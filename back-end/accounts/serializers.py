from rest_framework import serializers
from django.contrib.auth import get_user_model
from rest_framework_jwt.settings import api_settings
from django.contrib.auth.models import update_last_login
from django.contrib.auth import authenticate


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

    # set_password_form_class = SetPasswordForm
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
