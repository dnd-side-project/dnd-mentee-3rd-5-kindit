from django.db import models
from django.contrib.auth.models import AbstractUser
from django.utils.translation import ugettext_lazy as _
from accounts.managers import CustomUserManager


class CustomUser(AbstractUser):
    username = None
    email = models.EmailField(_('email address'), unique=True)

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []

    objects = CustomUserManager()

    nickname = models.CharField(blank=True, unique=True, max_length=20, verbose_name='닉네임')
    birthday = models.DateField(blank=True, null=True, verbose_name='생년월일')
    

    def __str__(self):
        return self.email