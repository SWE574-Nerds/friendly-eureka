from django.contrib.auth.models import User
from django.contrib.auth import authenticate

from api.dto.RegisterForm import RegisterForm
from api.dto.ListoryForm import ListoryForm
from api.validators.RegisterFormValidator import RegisterFormValidator
from api.validators.LoginFormValidator import LoginFormValidator
from api.validators.ListoryFormValidator import ListoryFormValidator
from api.dto.LoginForm import LoginForm
from api.services.TokenService import TokenService
from api.middleware.AuthMiddleware import AuthMiddleware

from post.models import TimeInfo, Category

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from post.services import ListoryService

from django.utils.decorators import decorator_from_middleware
from django.db import IntegrityError

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
                "category" : listory.category.name,
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
                "category" : listory.category.name,
                'createdAt': None
            }, status=status.HTTP_200_OK)
        except:
            return Response("Bad request", status=status.HTTP_400_BAD_REQUEST)

def api_get_listory(request, id):
    listory = ListoryService.get_listory_by_id(id)
    if listory is not None:
        return Response({
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
            "category": listory.category.name,
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
    listories = ListoryService.get_listories(max_count=10)
    response = []

    for listory in listories:
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
            "category": listory.category.name,
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

