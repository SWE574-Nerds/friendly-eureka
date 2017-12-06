from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from .views import *

app_name = 'api'

urlpatterns = [
    url(r'^test/$', test, name='api_test'),

    url(r'^user/login/$', api_login, name='api_login'),
    url(r'^user/register/$', api_register, name='api_register'),
    url(r'^user/(?P<id>\w{0,50})/$', api_userinfo, name='api_userinfo'),

    url(r'^listory/create/$', api_create_listory, name='api_create_listory'),
    url(r'^listory/search/$', api_get_search, name='api_get_search'),
    url(r'^listory/search/base64/(?P<encoded>[\w=]{0,500})/$', api_get_search_base64, name='api_get_search_base64'),

    url(r'^listory/(?P<id>\w{0,50})/$', api_listory, name='api_listory'),
    url(r'^listory/(?P<id>\w{0,50})/annotations/$', api_get_annos_listory, name='api_get_annos_listory'),
    url(r'^listory/$', get_all_listories, name='get_all_listories'),

    url(r'^annotation/(?P<id>[0-9a-z]{0,256})/body/$', api_get_annotation_body, name='api_get_annotation_body'),

    url(r'^annotation/(?P<id>[0-9a-z]{0,256})/$', api_get_annotation, name='api_get_annotation'),
    url(r'^annotation/(?P<id>[0-9a-z]{0,256})/(?P<type>[0-9a-z]{0,256})/$', api_post_annotation, name='api_post_annotation'),

    url(r'^time/$', get_time_types, name='get_time_types'),
    url(r'^category/$', get_category_types, name='get_category_types'),
]



urlpatterns = format_suffix_patterns(urlpatterns)
