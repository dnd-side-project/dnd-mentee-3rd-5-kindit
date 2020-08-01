from rest_framework import serializers
from django.contrib.auth.models import User
from accounts.models import User
from django.contrib.auth import authenticate


# 회원가입
class CreateUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ("id", "user_id", "user_nickname")
        extra_kwargs = {"user_nickname": {"write_only": True}}

    def create(self, validated_data):
        user = User.objects.create_user(
            validated_data["user_id"], None, validated_data["user_nickname"]
        )
        return user


# 접속 유지중인지 확인
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ("user_id", "user_nickname")


# 로그인
class LoginUserSerializer(serializers.Serializer):
    user_id = serializers.CharField()
    user_nickname = serializers.CharField()

    def validate(self, data):
        user = authenticate(**data)
        if user and user.is_active:
            return user
        raise serializers.ValidationError("Unable to log in with provided credentials.")