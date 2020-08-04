from django.urls import include, path
from rest_framework import routers
# from accounts import views
# from rest_framework.authtoken import views as auth_views


app_name = "accounts"

"""
urlpatterns = [
    path("user/", views.UserListView.as_view()),
    path("user/<int:pk>/", views.UserDetailView.as_view()),
    # path("staff/", views.StaffListCreateView.as_view()),
    # path("admin/", views.AdminListCreateView.as_view()),
    path("get_token/", views.CustomObtainAuthToken.as_view())
]
"""

urlpatterns = [
    # path('api-auth/', include("rest_framework.urls")),
    path('rest-auth/', include("rest_auth.urls")),
    path('rest-auth/registration/', include('rest_auth.registration.urls')),
]