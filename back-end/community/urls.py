from django.urls import include, path
from . import views
from rest_framework.routers import DefaultRouter


app_name = 'community'

urlpatterns = [
    path('', views.CommunityListView.as_view(), name='CommunityListView'),
    path('<int:pk>/', views.CommunityDetailView.as_view(), name='CommunityDetailView'),
    path('<int:pk>/like/', views.CommunityLikeView.as_view(), name='CommunityLikeView'),
]