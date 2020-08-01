from django.contrib import admin
from accounts.models import User
from django.contrib.auth.models import Group


class UserAdmin(admin.ModelAdmin):
    list_display = (
        'user_id',
        'user_nickname'
        )
        
    search_fields = (
        'user_id',
        'user_nickname'
        )


admin.site.register(User, UserAdmin)
admin.site.unregister(Group)