from rest_framework import serializers
from .models import Community, CommunityComment


class CommunitySerializer(serializers.ModelSerializer):
    writer = serializers.CharField(source="writer.nickname", read_only=True)
    likes_count = serializers.IntegerField(source="total_likes", read_only=True)
    created_date = serializers.CharField(source="created_string")

    class Meta:
        model = Community
        fields = (
            'id',
            'writer',
            'content',
            'likes_count',
            'comments',
            'hits',
            'upload_image',
            'created_date',
        )
        read_only_fields = (
            'writer',
            'likes_count',
        )


class CommunityPostSerializer(serializers.ModelSerializer):
    writer = serializers.CharField(source="writer.nickname", read_only=True)
    likes_count = serializers.IntegerField(source="total_likes", read_only=True)

    class Meta:
        model = Community
        fields = '__all__'
        read_only_fields = (
                'writer',
                'likes_count',
            )


class CommunityDetailSerializer(serializers.ModelSerializer):
    writer = serializers.CharField(source="writer.nickname", read_only=True)
    likes_count = serializers.IntegerField(source="total_likes", read_only=True)
    created_date = serializers.CharField(source="created_string")

    class Meta:
        model = Community
        fields = (
            'id',
            'writer',
            'content',
            'likes_count',
            'comments',
            'hits',
            'upload_image',
            'created_date',
        )
        read_only_fields = (
            'writer',
            'likes_count',
        )
