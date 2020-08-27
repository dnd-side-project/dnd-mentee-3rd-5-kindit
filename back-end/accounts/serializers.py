from rest_framework import serializers
from django.contrib.auth import get_user_model
from rest_framework_jwt.settings import api_settings
from django.contrib.auth.models import update_last_login
from django.contrib.auth import authenticate

import jwt
from django.contrib.auth import authenticate, get_user_model
from django.utils.translation import ugettext as _
from rest_framework_jwt.compat import get_username_field, PasswordField

User = get_user_model()
jwt_payload_handler = api_settings.JWT_PAYLOAD_HANDLER
jwt_encode_handler = api_settings.JWT_ENCODE_HANDLER
jwt_decode_handler = api_settings.JWT_DECODE_HANDLER
jwt_get_username_from_payload = api_settings.JWT_PAYLOAD_GET_USERNAME_HANDLER


class Serializer(serializers.Serializer):
    @property
    def object(self):
        return self.validated_data
        

class VerificationBaseSerializer(Serializer):
    token = serializers.CharField()

    def validate(self, attrs):
        msg = 'Please define a validate method.'
        raise NotImplementedError(msg)

    def _check_payload(self, token):
        try:
            payload = jwt_decode_handler(token)
        except jwt.ExpiredSignature:
            msg = _('Signature has expired.')
            raise serializers.ValidationError(msg)
        except jwt.DecodeError:
            msg = _('Error decoding signature.')
            raise serializers.ValidationError(msg)

        return payload

    def _check_user(self, payload):
        username = jwt_get_username_from_payload(payload)
        if not username:
            msg = _('Invalid payload.')
            raise serializers.ValidationError(msg)
        try:
            user = User.objects.get_by_natural_key(username)
        except User.DoesNotExist:
            msg = _("User doesn't exist.")
            raise serializers.ValidationError(msg)

        if not user.is_active:
            msg = _('User account is disabled.')
            raise serializers.ValidationError(msg)

        return user


class VerifyJSONWebTokenSerializer(VerificationBaseSerializer):
    def validate(self, attrs):
        token = attrs['token']
        payload = self._check_payload(token=token)
        user = self._check_user(payload=payload)
        return {
            'token': token,
            'user': user
        }


User = get_user_model()

class UserCreateSerializer(serializers.Serializer):
    email = serializers.EmailField(required=True)
    nickname = serializers.CharField(required=True)
    password = serializers.CharField(required=True)

    def create(self, validated_data):
        user = User.objects.create(
            email=validated_data['email'],
            nickname=validated_data['nickname'],
        )
        user.set_password(validated_data['password'])

        user.save()
        return user



JWT_PAYLOAD_HANDLER = api_settings.JWT_PAYLOAD_HANDLER
JWT_ENCODE_HANDLER = api_settings.JWT_ENCODE_HANDLER

class UserLoginSerializer(serializers.Serializer):
    email = serializers.CharField(max_length=64)
    nickname = serializers.CharField(max_length=20, read_only=True)
    password = serializers.CharField(max_length=128, write_only=True)
    token = serializers.CharField(max_length=255, read_only=True)

    def validate(self, data):
        email = data.get("email", None)
        password = data.get("password", None)
        user = authenticate(email=email, password=password)

        if user is None:
            return {
                'email': 'None'
            }
        try:
            payload = JWT_PAYLOAD_HANDLER(user)
            jwt_token = JWT_ENCODE_HANDLER(payload)
            update_last_login(None, user)
        except User.DoesNotExist:
            raise serializers.ValidationError(
                'User with given email and password does not exists'
            )
        return {
            'email': user.email,
            'nickname': User.objects.get(email=user).nickname,
            'token': jwt_token
        }


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            "id",
            "email",
            "nickname",
            "date_joined",
        ]
        read_only_fields = ('email', )


class UserDeleteSerializer(serializers.Serializer):
    email = serializers.CharField(max_length=64)
    password = serializers.CharField(max_length=128)

    def validate(self, data):
        email = data.get("email", None)
        password = data.get("password", None)
        user = authenticate(email=email, password=password)

        if user is None:
            return {'email': 'None'}
        return {'email': user.email,}


from django.contrib.auth.forms import SetPasswordForm
from rest_framework.exceptions import ValidationError

class PasswordChangeSerializer(serializers.Serializer):
    old_password = serializers.CharField(max_length=128)
    new_password1 = serializers.CharField(max_length=128)
    new_password2 = serializers.CharField(max_length=128)

    def __init__(self, *args, **kwargs):
        super(PasswordChangeSerializer, self).__init__(*args, **kwargs)
        self.request = self.context.get('request')
        self.user = getattr(self.request, 'user', None)

    def validate(self, data):
        invalid_password_conditions = (
            self.user,
            not self.user.check_password(data['old_password'])
        )
        if all(invalid_password_conditions):
            return {'error': 'old_password'}
        elif data['new_password1'] != data['new_password2']:
            return {'error': 'new_password'}
        return {'error': 'success'}

    def save(self, data):
        self.user.set_password(data['new_password1'])
        self.user.save()