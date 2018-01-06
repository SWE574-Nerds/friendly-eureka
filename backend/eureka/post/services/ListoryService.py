from post.models import Post, Marker, Point, Polyline
from post.models import TimeInfoGroup, TimeInfo, Category
from api.dto.ListoryForm import ListoryForm
from django.db.models import Q
from functools import reduce

def searchForKeywords(keywords: []):
    posts = Post.objects.filter(reduce(
        lambda x, y: x | y, [
            Q(content__contains=word) for word in keywords
        ])
    ) | Post.objects.filter(reduce(
        lambda x, y: x | y, [
            Q(title__contains=word) for word in keywords
        ])
    ) | Post.objects.filter(reduce(
        lambda x, y: x | y, [
            Q(category__name__contains=word) for word in keywords
        ])
    ) | Post.objects.filter(reduce(
        lambda x, y: x | y, [
            Q(annotation__message__contains=word) for word in keywords
        ])

    ) | Post.objects.filter(Q(word.isdigit())&Q(timeInfoGroup_timeInfo_value_count__gt = word)&Q(timeInfoGroup_timeValue1__lt=word)&Q(timeInfoGroup_timeValue2__gt=word) for word in keywords)


    temp = list(set(posts))
    return temp

def create_listory(listoryForm, owner):
   # try:
        info = TimeInfo.objects.get(pk=listoryForm.timeInfo.id)
        timeInfoGroup = TimeInfoGroup.objects.create(
            timeInfo=info,
            timeValue1=listoryForm.timeInfo.value1 or 0,
            timeValue2=listoryForm.timeInfo.value2 or 0
        )
        listory = Post.objects.create(image=listoryForm.image, user=owner, content=listoryForm.description, title=listoryForm.name, timeInfoGroup=timeInfoGroup)

        listory.save()


        for marker in listoryForm.markers:
            obj = Marker.objects.create(lat=marker.get("lat"),
                                    long=marker.get("long"),
                                    mag=marker.get("mag") or 200,
                                    name=marker.get("name") or "",
                                    color=marker.get("color") or "#ff0aaa",
                                    listory=listory
                                    );
            obj.save();

        for tag in listoryForm.tags:
            obj = Category.objects.get_or_create(name=tag)[0]
            obj.posts.add(listory)
            obj.save();


        for polyline in listoryForm.polylines:
            line = Polyline.objects.create(color=polyline.color, name=polyline.name, listory=listory)
            line.save();

            for point in polyline.points:
                Point.objects.create(lat=point.lat, long=point.long, line=line).save()


        return listory.pk
   # except :
    #     return ""


def get_listory_by_id(listoryId):
    try:
        return Post.objects.get(pk__exact=listoryId)
    except:
        return None


def get_listories(max_count):
    try:
        return Post.objects.all()[:max_count]
    except:
        return []
