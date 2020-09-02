from django.urls import include, path
from menu import views
from rest_framework.routers import DefaultRouter
from django.conf.urls.static import static
from django.conf import settings


# router = DefaultRouter()
# router.register('', views.BoardViewSet)
app_name = 'menu'

urlpatterns = [
    path('', views.MenuListView.as_view(), name='MenuListView'),
    path('<int:pk>/', views.MenuDetailView.as_view(), name='MenuDetailView')
    # path('', include(router.urls)),
]

urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)