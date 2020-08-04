# from config.utils import response_error_handler
# from rest_framework import viewsets
# from rest_framework.response import Response
# from rest_framework.request import Request
# from rest_framework.permissions import (
#     IsAdminUser,
#     AllowAny,
#     DjangoModelPermissionsOrAnonReadOnly,
#     IsAuthenticatedOrReadOnly,
#     IsAuthenticated,
# )
# from rest_framework import status
# from accounts.serializers import (
#     UserListSerializer,
#     UserCreateSerializer,
#     UserDetailSerializer,
# )
# from django.contrib.auth.models import Group
# from django.contrib.auth import get_user_model
# from rest_framework.authtoken.views import ObtainAuthToken
# from rest_framework.authtoken.models import Token


# class UserListView(viewsets.generics.ListCreateAPIView):
#     """A function, able to get list of user and Create normal user
    
#     Arguments:
#         viewsets {[ListCreateAPIView]} -- [GET, POST handler]
#     Raises:
#         ValidationError: [POST-HTTP_400_BAD_REQUEST]
#     Returns:
#         [GET-status] -- [GET-201-HTTP_201_CREATED]
#         [POST-status] -- [GET-200-HTTP_200_OK]
#     """

#     queryset = get_user_model().objects.filter(is_staff=False)
#     permission_classes = (AllowAny,)

#     def get_serializer_class(self):
#         if self.request.method == "POST":
#             return UserCreateSerializer
#         else:
#             return UserListSerializer

#     @response_error_handler
#     def post(self, request, *args, **kwargs):
#         return super().post(request, *args, **kwargs)


# class UserDetailView(viewsets.generics.RetrieveUpdateAPIView):
#     """A function, able to get specific user data and update
    
#     Arguments:
#         viewsets {[RetirieveUpdateAPIView]} -- [GET, PUT handler]
#     Raises:
#         PermissionError: [PUT-HTTP_401_UNAUTHORIZED]
#         ValidationError: [PUT-HTTP_400_BAD_REQUEST]
#         ValueError: [GET-HTTP_404_NOT_FOUND]
#     Returns:
#         [status] -- [GET-200-HTTP_200_OK]
#         [status] -- [PUT-204-HTTP_204_NO_CONTENT]
#     """

#     permission_classes = (IsAuthenticated,)

#     def get_serializer_class(self):
#         serializer_class = UserDetailSerializer
#         if self.request.method == "POST":
#             serializer_class = UserCreateSerializer
#         return serializer_class

#     def get_queryset(self):
#         pk = self.kwargs.get("pk", None)
#         queryset = get_user_model().objects.filter(id=pk, is_staff=False)
#         return queryset

#     @response_error_handler
#     def put(self, request, *args, **kwargs):
#         self.validate(request)
#         response = super().put(request, *args, **kwargs)
#         response.status_code = 204
#         return response
    
#     @response_error_handler
#     def get(self, request, *args, **kwargs):
#         self.validate(request)
#         return super().get(request, *args, **kwargs)

#     def validate(self, request):
#         condition_1 = request.user == self.get_queryset()[0]
#         condition_2 = request.user.is_staff or request.user.is_superuser
#         if condition_1 or condition_2:
#             return True
#         else:
#             raise PermissionError("you are not user or staff", "do not do that")


# class CustomObtainAuthToken(ObtainAuthToken):
#     def post(self, request, *args, **kwargs):
#         serializer = self.serializer_class(
#             data=request.data,
#             context={'request': request}
#         )
#         serializer.is_valid(raise_exception=True)
#         user = serializer.validated_data['user']
#         token, created = Token.objects.get_or_create(user=user)
#         return Response({'token': token.key, 'user': token.user.id})