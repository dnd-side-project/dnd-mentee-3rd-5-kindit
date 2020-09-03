from rest_framework import serializers
from menu.models import Menu
from taggit_serializer.serializers import TagListSerializerField, TaggitSerializer


class MenuSerializer(serializers.ModelSerializer):
    writer = serializers.CharField(source="writer.nickname", read_only=True)
    class Meta:
        model = Menu
        fields = (
            'id',
            'title',
            'writer',
            'hits',
            'rating',
            'upload_image',
        )
        read_only_fields = (
            'writer',
        )


class MenuPostSerializer(TaggitSerializer, serializers.ModelSerializer):
    tags = TagListSerializerField()
    base_menu = serializers.ListField(child=serializers.CharField(max_length=255))
    ingredient = serializers.ListField(child=serializers.CharField(max_length=255))
    writer = serializers.CharField(source="writer.nickname", read_only=True)
    likes_count = serializers.IntegerField(source="total_likes", read_only=True)

    class Meta:
        model = Menu
        fields = '__all__'
        read_only_fields = (
                'writer',
                'likes_count'
            )


class MenuDetailSerializer(serializers.ModelSerializer):
    # comments = CommentSerializer(many=True)
    # creator = FeedUserSerializer()
    tags = TagListSerializerField()
    writer = serializers.CharField(source="writer.nickname", read_only=True)
    likes_count = serializers.IntegerField(source="total_likes", read_only=True)
    class Meta:
        model = Menu
        fields = '__all__'
        read_only_fields = (
            'writer',
            'likes_count'
        )