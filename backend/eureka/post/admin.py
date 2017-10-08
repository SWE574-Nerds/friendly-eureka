from django.contrib import admin
from .models import Post
# from post.models import  post
# Register your models here.

class PostAdmin(admin.ModelAdmin):

    list_display = ['title', 'publishing_date']
    list_display_links = ['publishing_date']
    list_filter = ['publishing_date']
    search_fields = ['title', 'content']
    list_editable = ['title'] # editable olan link olarak verilmemeli

    class Meta:
        model = Post

admin.site.register(Post, PostAdmin)
