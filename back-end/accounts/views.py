import requests
import bcrypt
import jwt
import json
from django.shortcuts import render
from django.http import JsonResponse
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.permissions import IsAuthenticated, AllowAny # 로그인 여부
from rest_framework_jwt.authentication import JSONWebTokenAuthentication # JWT인증
from accounts.models import CustomUser as User
from rest_framework.views import APIView, View
from accounts.serializers import UserCreateSerializer
from django.core.exceptions import ValidationError
from django.core.validators import validate_email
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

from accounts.helper import send_mail
from django.template.loader import render_to_string
from django.utils.http import urlsafe_base64_encode,urlsafe_base64_decode
from django.utils.encoding import force_bytes, force_text
from config.settings import get_secret
from django.http import HttpResponse
from pytz import timezone
from datetime import datetime, timedelta


@api_view(['GET'])
@permission_classes((AllowAny,))
# @authentication_classes((JSONWebTokenAuthentication,))
def check_nickname(request):
    try:
        if User.objects.filter(nickname=request.GET['nickname']).exists():
            return JsonResponse({
                'result': 'fail',
                'message': '이미 존재하는 닉네임입니다.'
            }, status=409)
        else:
            return JsonResponse({
                'result':'success',
                'message': '사용 가능한 닉네임입니다.'
            }, status=200)
    except KeyError:
        return JsonResponse({'result':'INVALID_KEY'})


@method_decorator(csrf_exempt, name='dispatch')
class SignUpView(View):
    def post(self, request):
        email = request.POST.get('email','')
        nickname = request.POST.get('nickname','')
        password1 = request.POST.get('password1','')
        password2 = request.POST.get('password2','')
        try:
            validate_email(email)
            if User.objects.filter(email=email).exists():
                return JsonResponse({
                    'result': 'fail',
                    'message': '이미 등록된 이메일 계정입니다.'
                }, status=status.HTTP_400_BAD_REQUEST)
            
            if password1 != password2:
                return JsonResponse({
                    'result': 'fail',
                    'message': '비밀번호가 일치하지 않습니다.'
                }, status=status.HTTP_400_BAD_REQUEST)
            # user = User.objects.create(
            #     email     = data["email"],
            #     password  = bcrypt.hashpw(data["password"].encode("UTF-8"), bcrypt.gensalt()).decode("UTF-8"),
            #     is_active = False 
            # )
            token = jwt.encode({
                'email': email,
                'nickname': nickname,
                'password': password1,
                'exp': datetime.now(timezone('Asia/Seoul')) + timedelta(minutes=10),
            }, get_secret("SECRET_KEY"), algorithm = 'HS256').decode('utf-8')
            
            send_mail(
                '[카인딧:KINDIT] {} 님의 회원가입 인증메일 입니다.'.format(nickname),
                [email],
                html=render_to_string('accounts/confirm_email.html', {
                    'token': token,
                    'domain': self.request.META['HTTP_HOST'],
                }),
            )
            return JsonResponse({
                'result': 'success',
                'message': '회원가입 인증메일을 발송했습니다.'
            }, status=status.HTTP_200_OK)

        except KeyError:
            return JsonResponse({
                'result': 'fail',
                'message': 'INVALID_KEY'
            }, status=status.HTTP_400_BAD_REQUEST)
        except TypeError:
            return JsonResponse({
                'result': 'fail',
                'message': 'INVALID_TYPE'
            }, status=status.HTTP_400_BAD_REQUEST)
        except ValidationError:
            return JsonResponse({
                'result': 'fail',
                'message': '올바른 형식의 이메일을 입력해주세요.'
            }, status=status.HTTP_400_BAD_REQUEST)


def confirm_email_view(request, token):
    try:
        user_data = jwt.decode(token, get_secret("SECRET_KEY"), alghrithm='HS256')
        serializer = UserCreateSerializer(data=user_data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
        return render(request, 'accounts/register_success.html')

    except jwt.ExpiredSignatureError:
        return render(request, 'accounts/register_fail.html')

