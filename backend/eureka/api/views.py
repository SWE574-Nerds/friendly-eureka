from django.contrib.auth import authenticate
from django.contrib.auth.models import User
from django.db import IntegrityError
from django.utils.decorators import decorator_from_middleware
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from anno.dto.AnnotationForm import AnnotationForm
from api.dto.ListoryForm import ListoryForm
from api.dto.LoginForm import LoginForm
from api.dto.RegisterForm import RegisterForm
from api.middleware.AuthMiddleware import AuthMiddleware
from api.services.TokenService import TokenService
from api.validators.AnnotationFormValidator import AnnotationFormValidator
from api.validators.ListoryFormValidator import ListoryFormValidator
from api.validators.LoginFormValidator import LoginFormValidator
from api.validators.RegisterFormValidator import RegisterFormValidator
from post.models import TimeInfo, Category
from post.services import ListoryService
from anno.services.AnnotationService import AnnotationService
from api.utilities.AnnotationResponse import AnnotationResponse


@api_view(['POST'])
def api_login(request):
    form = LoginForm(request.data)
    validator = LoginFormValidator()

    errors = validator.validate(form)

    if len(errors):
        return Response(errors, status=status.HTTP_406_NOT_ACCEPTABLE)
    else:
        user = authenticate(username=form.name, password=form.password)
        if user is not None:
            tokenizer = TokenService()
            token = tokenizer.assignToken(user)
            return Response({
                'name': user.username,
                'userId': user.pk,
                'token': token
            }, status=status.HTTP_200_OK)
        else:
            return Response("Unauthorized", status=status.HTTP_401_UNAUTHORIZED)

@api_view(['POST'])
def api_register(request):
    form = RegisterForm(request.data)
    validator = RegisterFormValidator()

    errors = validator.validate(form)

    if len(errors):
        return Response(errors, status=status.HTTP_406_NOT_ACCEPTABLE)
    else:
        try:
            user = User.objects.create_user(username=form.name,
                                        email=form.mail,
                                        password=form.password)
            user.save()
            return Response({
                'name': user.username,
                'userId': user.pk
            }, status=status.HTTP_200_OK)
        except IntegrityError as e:
            return Response("User exists", status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def api_userinfo(request, id):
    try:
        user = User.objects.get(pk=id)
        return Response({
                'name': user.username,
                'userId': user.pk
            }, status=status.HTTP_200_OK)
    except User.DoesNotExist:
        return Response("User not found", status=status.HTTP_404_NOT_FOUND)


@decorator_from_middleware(AuthMiddleware)
@api_view(['GET'])
def test(request):
    api_user = request.api_user
    valid_api_user = request.valid_api_user
    return Response("Hello World " + api_user.username , status.HTTP_200_OK)


@decorator_from_middleware(AuthMiddleware)
@api_view(['POST'])
def api_create_listory(request):

    form = ListoryForm(request.data)
    validator = ListoryFormValidator()
    
    errors = validator.validate(form)

    if len(errors):
        return Response(errors, status=status.HTTP_406_NOT_ACCEPTABLE)
    else:
 #       try:
            api_user = request.api_user
            listoryId = ListoryService.create_listory(form, api_user)
            listory = ListoryService.get_listory_by_id(listoryId)

            marker_set = listory.marker_set.all()
            markers = []
            for marker in marker_set:
                markers.append({
                   "lat": marker.lat,
                    "long" : marker.long,
                    "mag" : marker.mag,
                    "name" : marker.name,
                    "color" : marker.color
                });

            polyline_set = listory.polyline_set.all()
            polylines = []
            for polyline in polyline_set:
                points = []
                point_set = polyline.point_set.all()
                for point in point_set:
                    points.append({"lat": point.lat, "long" : point.long})
                polylines.append({
                    "name" : polyline.name,
                    "color" : polyline.color,
                    "points" : points
                })

            tags = []
            tag_set = listory.category.all()
            for tag in tag_set:
                tags.append({"name" : tag.name, "id" : tag.pk})

            return Response({
                'name' : listory.title,
                'description' : listory.content,
               'image': listory.image,
                'listoryId': listory.pk,
                'owner': {
                    'name': api_user.username,
                    'userId': api_user.pk
                },
                "polylines" : polylines,
                "markers" : markers,
                "time" : {
                    "name": listory.timeInfoGroup.timeInfo.name,
                    "units" : listory.timeInfoGroup.timeInfo.value_type,
                    "values" : [ listory.timeInfoGroup.timeValue1, listory.timeInfoGroup.timeValue2 ],
                    "count" : listory.timeInfoGroup.timeInfo.value_count
                },
                "tags" : tags,
                'createdAt': None
            }, status=status.HTTP_200_OK)
    #    except:
    #        return Response("Bad request", status=status.HTTP_400_BAD_REQUEST)



@api_view(['GET', 'POST', 'DELETE'])
def api_listory(request, id):
    if request.method == 'GET':
        return api_get_listory(request, id)
    if request.method == 'POST':
        return api_update_listory(request, id)
    if request.method == 'DELETE':
        return api_delete_listory(request, id)


@decorator_from_middleware(AuthMiddleware)
@api_view(['POST'])
def api_update_listory(request, id):

    form = ListoryForm(request.data)
    validator = ListoryFormValidator()

    errors = validator.validate(form)

    api_user = request.api_user
    listory = ListoryService.get_listory_by_id(id)

    if len(errors) and listory.user.pk is not api_user.pk:
        return Response(errors, status=status.HTTP_406_NOT_ACCEPTABLE)
    else:
        try:
            if form.name is not None:
                listory.title = form.name
            if form.description is not None:
                listory.content = form.description

            if form.image is not None:
                listory.image = form.image

            listory.save()


            tags = []
            tag_set = listory.category.all()
            for tag in tag_set:
                tags.append({"name" : tag.name, "id" : tag.pk})

            return Response({
                 'name' : listory.title,
                'description' : listory.content,
                'image': listory.image,
                'listoryId': listory.pk,
                'owner': {
                    'name': api_user.username,
                    'userId': api_user.pk
                },
                "time" : {
                    "name": listory.timeInfoGroup.timeInfo.name,
                    "units" : listory.timeInfoGroup.timeInfo.value_type,
                    "values" : [ listory.timeInfoGroup.timeValue1, listory.timeInfoGroup.timeValue2 ],
                    "count" : listory.timeInfoGroup.timeInfo.value_count
                },
                "tags": tags,
                'createdAt': None
            }, status=status.HTTP_200_OK)
        except:
            return Response("Bad request", status=status.HTTP_400_BAD_REQUEST)

def api_get_listory(request, id):
    listory = ListoryService.get_listory_by_id(id)

    if listory is not None:

        marker_set = listory.marker_set.all()

        markers = []
        for marker in marker_set:
            markers.append({
                "lat": marker.lat,
                "long": marker.long,
                "mag": marker.mag,
                "name": marker.name,
                "color": marker.color
            });


        polyline_set = listory.polyline_set.all()
        polylines = []
        for polyline in polyline_set:
            points = []
            point_set = polyline.point_set.all()
            for point in point_set:
                points.append({"lat": point.lat, "long" : point.long})
            polylines.append({
                "name" : polyline.name,
                "color" : polyline.color,
                "points" : points
            })

        tags = []
        tag_set = listory.category.all()
        for tag in tag_set:
            tags.append({"name" : tag.name, "id" : tag.pk})

        return Response({
            'name': listory.title,
            'description': listory.content,
            'image': listory.image,
            'listoryId': listory.pk,
            "polylines" : polylines,
            'markers' : markers,
            'owner': {
                'name': listory.user.username,
                'userId': listory.user.pk
            },
            "time": {
                "name": listory.timeInfoGroup.timeInfo.name,
                "units": listory.timeInfoGroup.timeInfo.value_type,
                "values": [listory.timeInfoGroup.timeValue1, listory.timeInfoGroup.timeValue2],
                "count": listory.timeInfoGroup.timeInfo.value_count
            },
            "tags": tags,
            'createdAt': None
        }, status=status.HTTP_200_OK)
    else:
        return Response("Listory not found", status=status.HTTP_404_NOT_FOUND)


@decorator_from_middleware(AuthMiddleware)
def api_delete_listory(request, id):
    try:
        listory = ListoryService.get_listory_by_id(id)
        api_user = request.api_user

        if listory.user.pk == api_user.pk:
            listory.delete()
            return Response("Ok", status=status.HTTP_204_NO_CONTENT)
        else:
            return Response("Not your listory", status=status.HTTP_401_UNAUTHORIZED)
    except:
        return Response("Listory not found", status=status.HTTP_404_NOT_FOUND)


@api_view(['GET'])
def get_all_listories(request):
    listories = ListoryService.get_listories(max_count=100)
    response = []

    for listory in listories:

        tags = []
        tag_set = listory.category.all()
        for tag in tag_set:
            tags.append({"name" : tag.name, "id" : tag.pk})


        response.append({
            'name': listory.title,
            'description': listory.content,
            'image': listory.image,
            'listoryId': listory.pk,
            'owner': {
                'name': listory.user.username,
                'userId': listory.user.pk
            },
            "time": {
                "name": listory.timeInfoGroup.timeInfo.name,
                "units": listory.timeInfoGroup.timeInfo.value_type,
                "values": [listory.timeInfoGroup.timeValue1, listory.timeInfoGroup.timeValue2],
                "count": listory.timeInfoGroup.timeInfo.value_count
            },
            "tags": tags,
            'createdAt': None
        })
    return Response(response, status=status.HTTP_200_OK)

@api_view(['GET'])
def get_time_types(request):
    times = TimeInfo.objects.all()

    response = []

    for time in times:
        response.append({
            "id": time.pk,
            "name": time.name,
            "value_count" : time.value_count,
            "value_type" : time.value_type
        })

    return Response(response, status=status.HTTP_200_OK)


@api_view(['GET'])
def get_category_types(request):
    categories = Category.objects.all()

    response = []

    for category in categories:
        response.append({'name': category.name, 'id': category.pk})

    return Response(response, status=status.HTTP_200_OK)




@api_view(['GET'])
def api_get_annotation(request, id):
    annoSevice = AnnotationService()
    something = annoSevice.getAnnotationJSONLD(id)
    if something is not None:
        return Response(something, status=status.HTTP_200_OK)
    else:
        return Response("Annotation not found", status=status.HTTP_404_NOT_FOUND)


@api_view(['GET'])
def api_get_annotation_body(request, id):
    annoSevice = AnnotationService()
    something = annoSevice.getAnnotation(id)
    if something is not None:
        return Response(AnnotationResponse(id).value, status=status.HTTP_200_OK)
    else:
        return Response("Annotation not found", status=status.HTTP_404_NOT_FOUND)



@api_view(['POST'])
@decorator_from_middleware(AuthMiddleware)
def api_post_annotation(request, id, type):

    form = AnnotationForm(request.data)
    validator = AnnotationFormValidator()

    errors = validator.validate(form)

    if len(errors) > 0:
        return Response(errors, status=status.HTTP_406_NOT_ACCEPTABLE)
    else:
        anno = None;
        hash = None;
        annoSevice = AnnotationService()
        if type == "text":
            anno, hash = annoSevice.createTextAnnotation(form, request.api_user)
        elif type == "highlight":
            anno, hash = annoSevice.createHighlightAnnotation(form, request.api_user)
        elif type == "image":
            anno, hash = annoSevice.createImageAnnotation(form, request.api_user)


    return Response(AnnotationResponse(hash).value, status=status.HTTP_200_OK)


@api_view(['GET'])
def api_get_annos_listory(request, id):

    annoSevice = AnnotationService()
    annotations = annoSevice.getAnnotationsOfListory(id)

    resp = []

    for annotation in annotations:
        resp.append(annoSevice.getAnnotationJSONLD(annotation.storeKey))

    return Response(resp, status=status.HTTP_200_OK)



