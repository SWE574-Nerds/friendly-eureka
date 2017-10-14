from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from .views import *

app_name = 'api'

urlpatterns = [
    url(r'^user/login/$', api_login, name='api_login'),
    url(r'^user/register/$', api_register, name='api_register'),
    url(r'^test/$', test, name='api_test'),
]



urlpatterns = format_suffix_patterns(urlpatterns)
