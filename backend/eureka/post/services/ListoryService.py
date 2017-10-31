from post.models import Post
from post.models import TimeInfoGroup, TimeInfo, Category
from api.dto.ListoryForm import *

def create_listory(listoryForm, owner):
   # try:
        info = TimeInfo.objects.get(pk=listoryForm.timeInfo.id)
        category = Category.objects.get(pk=listoryForm.category_id)
        timeInfoGroup = TimeInfoGroup.objects.create(
            timeInfo=info,
            timeValue1=listoryForm.timeInfo.value1 or 0,
            timeValue2=listoryForm.timeInfo.value2 or 0
        )
        listory = Post.objects.create(user=owner, content=listoryForm.description, title=listoryForm.name, timeInfoGroup=timeInfoGroup, category=category)

        listory.save()
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
