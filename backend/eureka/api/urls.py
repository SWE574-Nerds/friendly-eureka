from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from .views import *

app_name = 'api'

urlpatterns = [
    url(r'^user/login/$', api_login, name='api_login'),
    url(r'^user/register/$', api_register, name='api_register'),
    url(r'^user/(?P<id>\w{0,50})/$', api_userinfo, name='api_userinfo'),
    url(r'^test/$', test, name='api_test'),
    url(r'^listory/create/$', api_create_listory, name='api_create_listory'),
    url(r'^listory/(?P<id>\w{0,50})/$', api_listory, name='api_listory'),
]



urlpatterns = format_suffix_patterns(urlpatterns)
