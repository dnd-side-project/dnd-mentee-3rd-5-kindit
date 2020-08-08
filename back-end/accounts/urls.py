from django.urls import include, path, re_path
from rest_framework import routers


app_name = "accounts"

urlpatterns = [
    path('auth/', include("rest_auth.urls")),
    path('auth/registration/', include('rest_auth.registration.urls')),
    path('accounts/', include('allauth.urls')),
]