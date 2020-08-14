from django.db import models
from django.contrib.auth.models import AbstractUser
from django.utils.translation import ugettext_lazy as _
from accounts.managers import CustomUserManager


class CustomUser(AbstractUser):
    username = None
    email = models.EmailField(_('email address'), unique=True)
    nickname = models.CharField(unique=True, max_length=20, verbose_name='닉네임')
    registered_date = models.DateTimeField(auto_now_add=True, verbose_name='가입일')

    objects = CustomUserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []

    def __str__(self):
        return self.email

    class Meta:
        db_table = "회원목록"
        verbose_name = "사용자"
        verbose_name_plural = "사용자"