from django.urls import include, path
from menu import views


app_name = 'menu'

urlpatterns = [
    path('hello/', views.hello, name='hello'),
]