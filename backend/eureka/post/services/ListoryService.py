from post.models import Post


def create_listory(name, description, owner):
    try:
        listory = Post.objects.create(user=owner, content=description, title=name)

        listory.save()
        return listory.pk
    except:
        return ""


def get_listory_by_id(listoryId):
    try:
        return Post.objects.get(pk__exact=listoryId)
    except:
        return None
