from rest_framework import serializers
from menu.models import Menu


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


class MenuPostSerializer(serializers.ModelSerializer):
    class Meta:
        model = Menu
        fields = '__all__'


class MenuDetailSerializer(serializers.ModelSerializer):
    # comments = CommentSerializer(many=True)
    # creator = FeedUserSerializer()
    writer = serializers.CharField(source="writer.nickname", read_only=True)
    likes_count = serializers.IntegerField(source="total_likes", read_only=True)
    class Meta:
        model = Menu
        fields = '__all__'
        read_only_fields = (
            'writer',
            'likes_count'
        )