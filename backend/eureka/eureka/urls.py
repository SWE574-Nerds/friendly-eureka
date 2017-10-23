from django.conf.urls import url, include
from django.contrib import admin
from home.views import home_view, terms_view
from django.conf.urls.static import static
from django.conf import settings

urlpatterns = [
    url(r'^admin/', admin.site.urls),

    url(r'^$', home_view, name='home'),

    url(r'^termsofservice', terms_view, name='termsofservice'),

    url(r'^post/', include('post.urls')),

    url(r'^accounts/', include('accounts.urls')),
 
]


urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
