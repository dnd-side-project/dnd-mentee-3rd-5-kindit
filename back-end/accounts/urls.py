from django.urls import include, path, re_path
from accounts import views
from rest_framework import routers
from rest_framework_jwt.views import obtain_jwt_token, refresh_jwt_token, verify_jwt_token


app_name = 'accounts'

urlpatterns = [
    # path('auth/', include("rest_auth.urls")),
    # path('auth/registration/', include('rest_auth.registration.urls')),
    # path('accounts/', include('allauth.urls')),
    path('jwt-auth/', obtain_jwt_token),          # JWT 토큰 획득
    path('jwt-auth/refresh/', refresh_jwt_token), # JWT 토큰 갱신
    path('jwt-auth/verify/', views.VerifyJSONWebToken.as_view()),   # JWT 토큰 확인

    path('check-nickname/', views.check_nickname, name='check_nickname'),
    path('registration/', views.SignUpView.as_view(), name='registration'),
    path('confirm-email/<str:token>/', views.confirm_email_view, name='activate'),
    path('login/', views.login_view, name='login'),
    path('user/', views.UserAPIView.as_view(), name='user'),
    path('password-reset/', views.PasswordResetView.as_view(), name='password-reset'),
    path('confirm-password-reset/<str:token>/', views.confirm_password_reset_view, name='password-reset-activate'),
    path('password-change/', views.PasswordChangeView.as_view(), name='password-change'),
]