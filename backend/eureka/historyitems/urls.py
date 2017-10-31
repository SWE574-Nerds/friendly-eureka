from django.conf.urls import url
from historyitems import views

urlpatterns = [
    url(r'^historyitem', views.historyitems, name='historyitems'),
]
