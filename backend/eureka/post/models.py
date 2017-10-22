from django.db import models
from django.urls import reverse
from ckeditor.fields import RichTextField

# Create your models here.


class Post(models.Model):
    user = models.ForeignKey('auth.User', verbose_name='Writer', related_name='posts')
    title = models.CharField(max_length=120, verbose_name='Title')
    content = RichTextField(verbose_name='Content')
    publishing_date = models.DateField(verbose_name='Date', auto_now_add=True)

    def __str__(self):
        return self.title

    class Meta:
        ordering = ['-publishing_date']
