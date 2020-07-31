from django.urls import path, include
from django.conf.urls import url
from drf_yasg.views import get_schema_view
from drf_yasg import openapi
from config.api_urls import urlpatterns
from rest_framework.permissions import (
    AllowAny,
    IsAuthenticated,
    BasePermission
)


schema_url_patterns = [
    path('api/', include(urlpatterns)),
]

schema_view = get_schema_view(
    openapi.Info(
        title="API 문서",
        default_version='v1',
        description = "DRF BackEnd API 문서입니다.",
        terms_of_service="https://www.google.com/policies/terms/",
        contact=openapi.Contact(email="park19996@gmail.com"),
        license=openapi.License(name="ParkHyeonChae"),
    ),
    validators=['flex'],
    public=True,
    permission_classes=(
        AllowAny,
    ),
    patterns=schema_url_patterns,
)


app_name = "api"

urlpatterns = [
    # path('swagger<str:format>', schema_view.without_ui(cache_timeout=0), name='schema-json'),
    path('swagger/', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
    path('docs/', schema_view.with_ui('redoc', cache_timeout=0), name='schema-redoc'),
]