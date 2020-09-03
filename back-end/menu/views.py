from django.shortcuts import render
from rest_framework.views import APIView, View
from rest_framework.response import Response
from .models import Menu
from .serializers import MenuSerializer, MenuPostSerializer, MenuDetailSerializer, TagListSerializer
from django.contrib.auth import get_user_model
from django.http import JsonResponse
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.permissions import IsAuthenticated, AllowAny # 로그인 여부
from rest_framework_jwt.authentication import JSONWebTokenAuthentication # JWT인증
from django.http import Http404
from rest_framework import viewsets
from rest_framework import permissions
from taggit.models import Tag
from django.db.models import Count
from accounts.models import CustomUser as User
import json


class MenuListView(APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def post(self, request, format=None):
        serializer = MenuPostSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(writer=self.request.user)
            return Response({'data':serializer.data, 'message':'성공적으로 등록되었습니다.'}, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def get(self, request, format=None):
        # queryset = Menu.objects.all()
        queryset = Menu.objects.exclude(deleted=True)
        serializer = MenuSerializer(queryset, many=True)
        return Response({'data':serializer.data})


class MenuDetailView(APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def get_object(self, pk):
        try:
            return Menu.objects.get(pk=pk)
        except Menu.DoesNotExist:
            raise Http404

    def get(self, request, pk):
        Menu = self.get_object(pk)
        Menu.hits += 1
        Menu.save()
        serializer = MenuDetailSerializer(Menu)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        writer = self.request.user
        try:
            menu = Menu.objects.get(pk=pk, writer=writer)
        except Menu.DoesNotExist:
            return Response({'data':None, 'message':'본인 게시글이 아닙니다.'}, status=status.HTTP_401_UNAUTHORIZED)

        serializer = MenuPostSerializer(menu, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'data':serializer.data, 'message':'성공적으로 수정되었습니다.'})
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        writer = self.request.user
        try:
            menu = Menu.objects.get(pk=pk, writer=writer)
        except Menu.DoesNotExist:
            return Response({'data':None, 'message':'본인 게시글이 아닙니다.'}, status=status.HTTP_401_UNAUTHORIZED)
        menu.deleted = True
        menu.save()
        # Menu.delete()
        return Response({'data':None, 'message':'성공적으로 삭제되었습니다.'}, status=status.HTTP_204_NO_CONTENT)


class TagListView(APIView):
    def get(self, request, format=None):
        queryset = Tag.objects.all()
        queryset_count = queryset.annotate(tag_count=Count('taggit_taggeditem_items')).order_by('-tag_count')[:30]
        # tag_dict = {}
        # for tag in queryset_count:
        #     tag_dict[tag.name] = tag.tag_count
        # print(tag_dict)
        serializer = TagListSerializer(queryset_count, many=True)
        return Response({'data':serializer.data})

    def post(self, request, format=None):
        user_email = self.request.user
        user = User.objects.get(email=user_email)
        user.preference_keward = json.loads(request.body)['preference_keward']
        user.save()
        return Response({'data':user.preference_keward,'message':'성공적으로 등록되었습니다.'})


class MenuLikeView(APIView):
    def post(self, request, pk, format=None):
        user = self.request.user
        try:
            menu = Menu.objects.get(pk=pk, writer=user)
        except Menu.DoesNotExist:
            menu = Menu.objects.get(pk=pk)
            if menu.likes.filter(id=user.id):
                menu.likes.remove(user.id)
                return Response({'data':None, 'message':'해당 게시글 찜을 취소했습니다.'}, status=status.HTTP_200_OK)
            else:
                menu.likes.add(user.id)
                return Response({'data':None, 'message':'해당 게시글을 찜했습니다.'}, status=status.HTTP_200_OK)

        return Response({'data':None, 'message':'본인 게시글은 찜할 수 없습니다.'}, status=status.HTTP_400_BAD_REQUEST)


class MenuRatingView(APIView):
    def post(self, request, pk, format=None):
        user = self.request.user
        try:
            menu = Menu.objects.get(pk=pk, writer=user)
        except Menu.DoesNotExist:
            menu = Menu.objects.get(pk=pk)
            if menu.rating_user.filter(id=user.id):
                return Response({'data':None, 'message':'이미 별점을 등록하셨습니다.'}, status=status.HTTP_400_BAD_REQUEST)
            else:  
                tmp_rating = menu.rating * menu.rating_user.count()
                menu.rating_user.add(user.id)
                rating = json.loads(request.body)['rating']
                menu.rating = (tmp_rating + rating) / menu.rating_user.count()
                menu.save()
                return Response({'data':None, 'message':'해당 게시글에 별점을 등록했습니다.'}, status=status.HTTP_200_OK)

        return Response({'data':None, 'message':'본인 게시글에는 별점을 등록할 수 없습니다.'}, status=status.HTTP_400_BAD_REQUEST)