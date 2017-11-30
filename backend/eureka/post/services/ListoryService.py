from post.models import Post
from post.models import Marker
from post.models import TimeInfoGroup, TimeInfo, Category
from api.dto.ListoryForm import *

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
