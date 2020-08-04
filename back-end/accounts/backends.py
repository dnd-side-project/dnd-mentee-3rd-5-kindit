# from django.contrib.auth.backends import ModelBackend
# from django.contrib.auth import get_user_model
# from rest_framework.authtoken.models import Token
# from rest_framework.authentication import TokenAuthentication, get_authorization_header
# from rest_framework import exceptions
# from config.utils import response_error_handler


# class UserBackend(ModelBackend):
#     def authenticate(self, request, username=None, password=None, **kwargs):
#         user = super().authenticate(request, username, password, **kwargs)
#         if user:
#             return user
#         UserModel = get_user_model()

#         if username is None:
#             username = kwargs.get(UserModel.USERNAME_FIELD, kwargs.get("email"))
#         try:
#             user = UserModel._default_manager.get(email=username)
#         except get_user_model().DoesNotExist:
#             UserModel().set_password(password)
#         else:
#             if user.check_password(password) and self.user_can_authenticate(user):
#                 return user

#     def get_token(self, user):
#         token = Token.objects.get(user=user)
#         return token


# class TokenAuthBackend(TokenAuthentication):
#     def authenticate(self, request):
#         auth = get_authorization_header(request).split()

#         if not auth or auth[0].lower() != self.keyword.lower().encode():
#             return None

#         if len(auth) == 1 or not auth[1]:
#             return None  # This part changed
#         elif len(auth) > 2:
#             msg = _("Invalid token header. Token string should not contain spaces.")
#             raise exceptions.AuthenticationFailed(msg)

#         try:
#             token = auth[1].decode()
#         except UnicodeError:
#             msg = _(
#                 "Invalid token header. Token string should not contain invalid characters."
#             )
#             raise exceptions.AuthenticationFailed(msg)

#         return self.authenticate_credentials(token)