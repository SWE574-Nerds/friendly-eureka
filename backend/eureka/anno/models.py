from django.db import models
from post.models import Post

class Annotation(models.Model):
    message = models.TextField(verbose_name="Message content")
    storeKey = models.TextField(verbose_name="annotation_store_key")
    listory = models.ForeignKey(Post)
    author = models.ForeignKey('auth.User', verbose_name='Writer', related_name='annos')

    def __str__(self):
        return self.author.username + " annot(" + self.storeKey + "): " + self.message
