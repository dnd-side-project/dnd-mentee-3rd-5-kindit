from django.urls import include, path
from menu import views


app_name = 'menu'

urlpatterns = [
    path('', views.MenuListView.as_view(), name='MenuListView'),
    path('<int:pk>/', views.MenuDetailView.as_view(), name='MenuDetailView')
]