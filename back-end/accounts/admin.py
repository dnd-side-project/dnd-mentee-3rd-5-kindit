from django.contrib import admin
from django.contrib.auth import get_user_model
from django.contrib.auth.models import Group
from accounts.models import CustomUser
from django.contrib.auth.admin import UserAdmin


class CustomUserAdmin(admin.ModelAdmin):
    list_display = (
        'email',
        'nickname',
        'birthday'
    )

    search_fields = (
        'email',
        'nickname'
    )


admin.site.register(get_user_model(), CustomUserAdmin)
admin.site.unregister(Group)