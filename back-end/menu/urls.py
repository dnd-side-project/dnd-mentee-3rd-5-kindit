from django.urls import include, path
from menu import views
from rest_framework.routers import DefaultRouter


app_name = 'menu'

urlpatterns = [
    path('', views.MenuListView.as_view(), name='MenuListView'),
    path('<int:pk>/', views.MenuDetailView.as_view(), name='MenuDetailView'),
    path('keward/', views.TagListView.as_view(), name='TagListView'),
    path('<int:pk>/like/', views.MenuLikeView.as_view(), name='MenuLikeView'),
    path('<int:pk>/rating/', views.MenuRatingView.as_view(), name='MenuRatingView'),
    path('like-menu/', views.LikeMenuView.as_view(), name='LikeMenuView'),
    path('write-menu/', views.WriteMenuView.as_view(), name='WriteMenuView'),
]
