from django.db import models
from django.urls import reverse
from ckeditor.fields import RichTextField
# Create your models here.


class TimeInfo(models.Model):
    name = models.CharField(max_length=120, verbose_name='Time information')

    def __str__(self):
        return self.name


class Category(models.Model):
    name = models.CharField(max_length=120, verbose_name='Category Name')

    def __str__(self):
        return self.name


class Post(models.Model):
    user = models.ForeignKey('auth.User', verbose_name='Writer', related_name='posts')
    title = models.CharField(max_length=120, verbose_name='Title')
    content = RichTextField(verbose_name='Content')
    category = models.ForeignKey(Category, verbose_name='Category', related_name='posts', blank=True)
    timeInfo = models.ForeignKey(TimeInfo, verbose_name='TimeInfoValue', related_name='posts', blank=True)
    timeValue1 = models.CharField(max_length=120, verbose_name='', blank=True)
    timeValue2 = models.CharField(max_length=120, verbose_name='', blank=True)
    publishing_date = models.DateField(verbose_name='Date', auto_now_add=True)

    def __str__(self):
        return self.title

    def get_absolute_url(self):
        return reverse('post:detail', kwargs={'id': self.id})
        # return "/post/{}".format(self.id)pycharm

    def get_create_url(self):
        return reverse('post:create')

    def get_update_url(self):
        return reverse('post:update', kwargs={'id': self.id})

    def get_delete_url(self):
        return reverse('post:delete', kwargs={'id': self.id})

    class Meta:
        ordering = ['-publishing_date']


class Comment(models.Model):
    post = models.ForeignKey('post.Post', related_name='comments',on_delete=models.CASCADE)
    name = models.CharField(max_length=200,verbose_name='Username')
    content = models.TextField(verbose_name='Comment')
    created_Date = models.DateTimeField(auto_now_add=True)


