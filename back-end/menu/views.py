from django.shortcuts import render
from rest_framework.views import APIView, View
from rest_framework.response import Response
from .models import Menu
from .serializers import MenuSerializer
from django.contrib.auth import get_user_model
from django.http import JsonResponse
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes, authentication_classes
from rest_framework.permissions import IsAuthenticated, AllowAny # 로그인 여부
from rest_framework_jwt.authentication import JSONWebTokenAuthentication # JWT인증
from django.http import Http404
from rest_framework import viewsets
from rest_framework import permissions


class MenuListView(APIView):
    permission_classes = (IsAuthenticated,)
    
    def post(self, request, format=None):
        serializer = MenuSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def get(self, request, format=None):
        queryset = Menu.objects.all()
        serializer = MenuSerializer(queryset, many=True)
        return Response(serializer.data)


class MenuDetailView(APIView):
    def get_object(self, pk):
        try:
            return Menu.objects.get(pk=pk)
        except Menu.DoesNotExist:
            raise Http404

    """
    특정 게시물 조회
    /Menu/{pk}/
    """
    def get(self, request, pk):
        Menu = self.get_object(pk)
        serializer = MenuSerializer(Menu)
        return Response(serializer.data)

    """
    특정 게시물 수정
    /Menu/{pk}/
    """
    def put(self, request, pk, format=None):
        Menu = self.get_object(pk)
        serializer = MenuSerializer(Menu, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    """
    특정 게시물 삭제
    /Menu/{pk}/
    """
    def delete(self, request, pk, format=None):
        Menu = self.get_object(pk)
        Menu.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)


class BoardViewSet(viewsets.ModelViewSet):
    queryset = Menu.objects.all()
    serializer_class = MenuSerializer
    # permission_classes = [permissions.IsAuthenticatedOrReadOnly]
    permission_classes = [permissions.AllowAny]

    def perform_create(self, serializer):
        serializer.save(writer=self.request.user)