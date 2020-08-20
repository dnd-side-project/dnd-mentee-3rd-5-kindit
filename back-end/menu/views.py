from django.shortcuts import render
from rest_framework.views import APIView, View
from rest_framework.response import Response
from .models import Menu
from .serializers import MenuSerializer
from django.contrib.auth import get_user_model
from django.http import JsonResponse
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.permissions import IsAuthenticated, AllowAny # 로그인 여부
from rest_framework_jwt.authentication import JSONWebTokenAuthentication # JWT인증


def hello(request):
    pass