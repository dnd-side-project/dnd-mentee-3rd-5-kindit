# from django.contrib import admin
# from django.contrib.auth import get_user_model
# from django.contrib.auth.models import Group


# class UserAdmin(admin.ModelAdmin):
#     list_display = (
#         'user_id',
#         'user_nickname'
#         )

#     search_fields = (
#         'user_id',
#         'user_nickname'
#         )


# admin.site.register(User, UserAdmin)
# admin.site.unregister(Group)


from django.contrib import admin
from django.contrib.auth import get_user_model
from django.contrib.auth.admin import UserAdmin



class CustomUserAdmin(UserAdmin):
    UserAdmin.fieldsets[1][1]["fields"]


admin.site.register(get_user_model(), CustomUserAdmin)