from django.urls import include, path
from menu import views
from rest_framework.routers import DefaultRouter


# router = DefaultRouter()
# router.register('', views.BoardViewSet)
app_name = 'menu'

urlpatterns = [
    path('', views.MenuListView.as_view(), name='MenuListView'),
    path('<int:pk>/', views.MenuDetailView.as_view(), name='MenuDetailView'),
    # path('', include(router.urls)),
    path('keward/', views.TagListView.as_view(), name='TagListView'),
    path('<int:pk>/like/', views.MenuLikeView.as_view(), name='MenuLikeView'),
]
