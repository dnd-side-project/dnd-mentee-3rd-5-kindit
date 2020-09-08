from django.shortcuts import render
from rest_framework.views import APIView, View
from rest_framework.response import Response
from .models import Community, CommunityComment
from .serializers import CommunitySerializer, CommunityPostSerializer, CommunityDetailSerializer
from django.contrib.auth import get_user_model
from django.http import JsonResponse
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.permissions import IsAuthenticated, AllowAny # 로그인 여부
from rest_framework_jwt.authentication import JSONWebTokenAuthentication # JWT인증
from django.http import Http404
from rest_framework import viewsets
from rest_framework import permissions
from django.db.models import Count
from accounts.models import CustomUser as User
import json
from django.db.models import Q
from rest_framework.parsers import MultiPartParser, JSONParser


class CommunityListView(APIView):
    def post(self, request, format=None):
        serializer = CommunityPostSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(writer=self.request.user)
            return Response({'data':serializer.data, 'message':'성공적으로 등록되었습니다.'}, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def get(self, request, format=None):
        queryset = Community.objects.exclude(deleted=True).order_by('-created_date')
        if queryset:
            serializer = CommunitySerializer(queryset, many=True)
            return Response({'data':serializer.data})
        else:
            return Response({'data':None, 'message':'등록된 게시글이 없습니다.'}, status=status.HTTP_400_BAD_REQUEST)


class CommunityDetailView(APIView):
    def get_object(self, pk):
        try:
            return Community.objects.get(pk=pk)
        except Community.DoesNotExist:
            raise Http404

    def get(self, request, pk):
        Community = self.get_object(pk)
        Community.hits += 1
        Community.save()
        serializer = CommunityDetailSerializer(Community)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        writer = self.request.user
        try:
            community = Community.objects.get(pk=pk, writer=writer)
        except Community.DoesNotExist:
            return Response({'data':None, 'message':'본인 게시글이 아닙니다.'}, status=status.HTTP_401_UNAUTHORIZED)

        serializer = CommunityPostSerializer(community, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'data':serializer.data, 'message':'성공적으로 수정되었습니다.'})
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        writer = self.request.user
        try:
            community = Community.objects.get(pk=pk, writer=writer)
        except Community.DoesNotExist:
            return Response({'data':None, 'message':'본인 게시글이 아닙니다.'}, status=status.HTTP_401_UNAUTHORIZED)
        community.deleted = True
        community.save()
        return Response({'data':None, 'message':'성공적으로 삭제되었습니다.'}, status=status.HTTP_204_NO_CONTENT)


class CommunityLikeView(APIView):
    def post(self, request, pk, format=None):
        user = self.request.user
        try:
            community = Community.objects.get(pk=pk, writer=user)
        except Community.DoesNotExist:
            community = Community.objects.get(pk=pk)
            if community.likes.filter(id=user.id):
                community.likes.remove(user.id)
                return Response({'data':None, 'message':'해당 게시글 좋아요를 취소했습니다.'}, status=status.HTTP_200_OK)
            else:
                community.likes.add(user.id)
                return Response({'data':None, 'message':'해당 게시글에 좋아요를 눌렀습니다.'}, status=status.HTTP_200_OK)

        return Response({'data':None, 'message':'본인 게시글은 좋아요 할 수 없습니다.'}, status=status.HTTP_400_BAD_REQUEST)


# class WriteMenuView(APIView):
#     def get(self, request, format=None):
#         queryset = Menu.objects.filter(writer=request.user.id).exclude(deleted=True)
#         if queryset:
#             serializer = MenuSerializer(queryset, many=True)
#             return Response({'data':serializer.data})
#         else:
#             return Response({'data':None, 'message':'작성한 메뉴가 없습니다.'}, status=status.HTTP_400_BAD_REQUEST)