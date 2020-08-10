from django.shortcuts import render
import requests, json
from django.http import JsonResponse
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.permissions import IsAuthenticated, AllowAny # 로그인 여부
from rest_framework_jwt.authentication import JSONWebTokenAuthentication # JWT인증
from accounts.models import CustomUser as User


@api_view(['GET'])
@permission_classes((IsAuthenticated,))
@authentication_classes((JSONWebTokenAuthentication,))
def check_nickname(request):
    """
    Params--nickname
    Returns
        중복 닉네임 없을 시
        message : success
        status : 200
        중복 닉네임 있을 시
        message : nickname_exist
        status : 409
    """
    try:
        if User.objects.filter(nickname=request.GET['nickname']).exists():
            return JsonResponse({'message':'nickname_exist'}, status=409)
        else:
            return JsonResponse({'message':'success'}, status=200)
    except KeyError:
        return JsonResponse({'message':'INVALID_KEY'})