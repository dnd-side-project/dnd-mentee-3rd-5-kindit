import requests
import bcrypt
import jwt
import json
from django.shortcuts import render, get_object_or_404
from django.http import JsonResponse
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.permissions import IsAuthenticated, AllowAny # 로그인 여부
from rest_framework_jwt.authentication import JSONWebTokenAuthentication # JWT인증
from .models import CustomUser as User
from rest_framework.views import APIView, View
from rest_framework.response import Response
from rest_framework.generics import GenericAPIView, RetrieveUpdateAPIView
from .serializers import UserCreateSerializer, UserLoginSerializer, UserSerializer, UserDeleteSerializer, PasswordChangeSerializer, VerifyJSONWebTokenSerializer
from django.core.exceptions import ValidationError
from django.core.validators import validate_email
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

from .helper import send_mail, reset_password
from django.template.loader import render_to_string
from django.utils.http import urlsafe_base64_encode,urlsafe_base64_decode
from django.utils.encoding import force_bytes, force_text
from config.settings import get_secret
from django.http import HttpResponse
from pytz import timezone
from datetime import datetime, timedelta
from django.contrib.auth import get_user_model

from rest_framework_jwt.settings import api_settings
jwt_payload_handler = api_settings.JWT_PAYLOAD_HANDLER


@api_view(['GET'])
@permission_classes([AllowAny])
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


class SignUpView(View):
    def post(self, request):
        email = json.loads(request.body)['email']
        nickname = json.loads(request.body)['nickname']
        password1 = json.loads(request.body)['password1']
        password2 = json.loads(request.body)['password2']
        try:
            validate_email(email)
            if User.objects.filter(email=email).exists():
                return JsonResponse({
                    'result': 'fail',
                    'message': '이미 등록된 이메일 계정입니다.'
                }, status=status.HTTP_400_BAD_REQUEST)

            if not email or not nickname or not password1 or not password2:
                return JsonResponse({
                    'result': 'fail',
                    'message': '모든 항목을 입력해주세요.'
                }, status=status.HTTP_400_BAD_REQUEST)

            if password1 != password2:
                return JsonResponse({
                    'result': 'fail',
                    'message': '비밀번호가 일치하지 않습니다.'
                }, status=status.HTTP_400_BAD_REQUEST)

            token = jwt.encode({
                'email': email,
                'nickname': nickname,
                'password': password1,
                'exp': datetime.now(timezone('Asia/Seoul')) + timedelta(minutes=10),
            }, get_secret("SECRET_KEY"), algorithm = 'HS256').decode('utf-8')
            
            send_mail(
                '[카인딧:KINDIT] {}님의 회원가입 인증메일 입니다.'.format(nickname),
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
        return render(request, 'accounts/token_fail.html')


@api_view(['POST'])
@permission_classes([AllowAny])
def login_view(request):
    if request.method == 'POST':
        serializer = UserLoginSerializer(data=json.loads(request.body))

        if not serializer.is_valid(raise_exception=True):
            return JsonResponse({
                'result': 'fail',
                'message': 'Valid Error'
            }, status=status.HTTP_400_BAD_REQUEST)

        if serializer.validated_data['email'] == "None":
            return JsonResponse({
                'result': 'fail',
                'message': '입력하신 정보와 일치하는 계정이 없습니다.'
            }, status=status.HTTP_400_BAD_REQUEST)

        return JsonResponse({
            'result': 'success',
            'message': '로그인 성공.',
            'user': {
                'token': serializer.data['token'],
                'email': serializer.data['email'],
                'nickname': serializer.data['nickname']
            }
        }, status=status.HTTP_200_OK)


class UserAPIView(APIView):
    permission_classes = (IsAuthenticated,)

    def get_object(self):
        return self.request.user

    def get_queryset(self):
        return get_user_model().objects.none()

    def get(self, request, *args, **kwargs):
        snippet = self.get_object()
        serializer = UserSerializer(snippet)
        return JsonResponse({
            'result': 'success',
            'message': '회원님의 프로필은 다음과 같습니다.',
            'user': {
                'email': serializer.data['email'],
                'nickname': serializer.data['nickname'],
                'preference_keward': serializer.data['preference_keward'],
                'date_joined': serializer.data['date_joined']
            }
        }, status=status.HTTP_200_OK)

    def patch(self, request, *args, **kwargs):
        snippet = self.get_object()
        serializer = UserSerializer(snippet, data=json.loads(request.body))
        if serializer.is_valid():
            serializer.save()
            return JsonResponse({
                'result': 'success',
                'message': '프로필이 수정되었습니다.',
                'user': {
                    'email': serializer.data['email'],
                    'nickname': serializer.data['nickname'],
                    'preference_keward': serializer.data['preference_keward'],
                    'date_joined': serializer.data['date_joined']
                }
            }, status=status.HTTP_200_OK)
        else:
            return JsonResponse({
                    'result': 'fail',
                    'message': '이미 존재하는 닉네임입니다.',
                }, status=status.HTTP_409_CONFLICT)

    def delete(self, request, *args, **kwargs):
        snippet = self.get_object()
        data = {}
        password = json.loads(request.body)['password']
        data['email'] = snippet.email
        data['password'] = password
        serializer = UserDeleteSerializer(data=data)

        if not serializer.is_valid(raise_exception=True):
            return JsonResponse({
                'result': 'fail',
                'message': 'Valid Error'
            }, status=status.HTTP_400_BAD_REQUEST)

        if serializer.validated_data['email'] == "None":
            return JsonResponse({
                'result': 'fail',
                'message': '비밀번호가 일치하지 않습니다.'
            }, status=status.HTTP_400_BAD_REQUEST)

        snippet.delete()
        return JsonResponse({
            'result': 'success',
            'message': '회원탈퇴가 완료되었습니다.',
        }, status=status.HTTP_204_NO_CONTENT)


class PasswordResetView(APIView):
    permission_classes = (AllowAny,)

    def post(self, request):
        email = json.loads(request.body)['email']
        try:
            validate_email(email)
            if not User.objects.filter(email=email).exists():
                return JsonResponse({
                    'result': 'fail',
                    'message': '입력하신 정보와 일치하는 계정이 없습니다.'
                }, status=status.HTTP_400_BAD_REQUEST)

            nickname = User.objects.get(email=email).nickname
            token = jwt.encode({
                'email': email,
                'password': reset_password(),
                'exp': datetime.now(timezone('Asia/Seoul')) + timedelta(minutes=10),
            }, get_secret("SECRET_KEY"), algorithm = 'HS256').decode('utf-8')
            
            send_mail(
                '[카인딧:KINDIT] {}님의 비밀번호 초기화 인증 메일입니다.'.format(nickname),
                [email],
                html=render_to_string('accounts/password_reset.html', {
                    'token': token,
                    'domain': self.request.META['HTTP_HOST'],
                }),
            )
            return JsonResponse({
                'result': 'success',
                'message': '입력하신 메일로 비밀번호 초기화 메일을 발송했습니다.'
            }, status=status.HTTP_200_OK)

        except KeyError:
            return JsonResponse({
                'result': 'fail',
                'message': '이메일을 입력해주세요.'
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


def confirm_password_reset_view(request, token):
    try:
        data = jwt.decode(token, get_secret("SECRET_KEY"), alghrithm='HS256')
        user = User.objects.get(email=data['email'])
        user.set_password(data['password'])
        user.save()
        return render(request, 'accounts/password_reset_success.html', context={'data':data['password']})

    except jwt.ExpiredSignatureError:
        return render(request, 'accounts/token_fail.html')


class PasswordChangeView(GenericAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = PasswordChangeSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=json.loads(request.body))
        serializer.is_valid(raise_exception=True)
        
        if serializer.validated_data['error'] == 'old_password':
            return JsonResponse({
                'result': 'fail',
                'message': '이전 비밀번호가 일치하지 않습니다.'
            }, status=status.HTTP_400_BAD_REQUEST)

        if serializer.validated_data['error'] == 'new_password':
            return JsonResponse({
                'result': 'fail',
                'message': '입력하신 새 비밀번호가 일치하지 않습니다.'
            }, status=status.HTTP_400_BAD_REQUEST)
        serializer.save(data=json.loads(request.body))
        return JsonResponse({
            'result': 'success',
            'message': '비밀번호가 변경되었습니다. 변경하신 비밀번호로 다시 로그인 해주세요.',
        }, status=status.HTTP_200_OK)



class JSONWebTokenAPIView(APIView):
    permission_classes = ()
    authentication_classes = ()

    def get_serializer_context(self):
        return {
            'request': self.request,
            'view': self,
        }

    def get_serializer_class(self):
        assert self.serializer_class is not None, (
            "'%s' should either include a `serializer_class` attribute, "
            "or override the `get_serializer_class()` method."
            % self.__class__.__name__)
        return self.serializer_class

    def get_serializer(self, *args, **kwargs):
        serializer_class = self.get_serializer_class()
        kwargs['context'] = self.get_serializer_context()
        return serializer_class(*args, **kwargs)

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=json.loads(request.body))
        context = {'result':'success', 'message':'사용 가능'}

        if serializer.is_valid():
            user = serializer.object.get('user') or request.user
            token = serializer.object.get('token')
            response_data = api_settings.JWT_RESPONSE_PAYLOAD_HANDLER(token, user, request)
            response = Response(response_data)
            context['detail'] = response_data
            if api_settings.JWT_AUTH_COOKIE:
                expiration = (datetime.utcnow() + api_settings.JWT_EXPIRATION_DELTA)
                response.set_cookie(api_settings.JWT_AUTH_COOKIE, token, expires=expiration, httponly=True)

            return JsonResponse(context, status=status.HTTP_200_OK)

        return JsonResponse({
                'result': 'fail',
                'message': '사용 불가'
            }, status=status.HTTP_400_BAD_REQUEST)


class VerifyJSONWebToken(JSONWebTokenAPIView):
    serializer_class = VerifyJSONWebTokenSerializer