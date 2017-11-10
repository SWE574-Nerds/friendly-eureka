from django.db import models
from post.models import Post

class Annotation(models.Model):
    message = models.TextField(verbose_name="Message content")
    storeKey = models.TextField(verbose_name="annotation_store_key")
    listory = models.ForeignKey(Post)

    def __str__(self):
        return self.listory.user.username + " annot(" + self.storeKey + "): " + self.message