from django.urls import include, path
from menu import views
from rest_framework.routers import DefaultRouter


app_name = 'menu'

urlpatterns = [
    path('', views.MenuListView.as_view(), name='MenuListView'),
    path('<int:pk>/', views.MenuDetailView.as_view(), name='MenuDetailView'),
    path('keyword/', views.TagListView.as_view(), name='TagListView'),
    path('<int:pk>/like/', views.MenuLikeView.as_view(), name='MenuLikeView'),
    path('<int:pk>/rating/', views.MenuRatingView.as_view(), name='MenuRatingView'),
    path('like-menu/', views.LikeMenuView.as_view(), name='LikeMenuView'),
    path('write-menu/', views.WriteMenuView.as_view(), name='WriteMenuView'),
    path('search/', views.MenuSearchView.as_view(), name='MenuSearchView'),
    path('write/', views.MenuWriteView.as_view(), name='MenuWriteView'),
    # path('<str:brand>/<str:sort>/', views.MenuListView.as_view(), name='MenuListView'),
]