from rest_framework import serializers
from django.contrib.auth import get_user_model


# class UserSerializer(serializers.ModelSerializer):
#     class Meta:
#         model = CustomUser
#         fields = ['id', 'email', 'nickname']


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