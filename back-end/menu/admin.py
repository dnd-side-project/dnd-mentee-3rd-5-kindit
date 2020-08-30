from django.contrib import admin
from .models import Menu, MenuComment


class MenuAdmin(admin.ModelAdmin):
    list_display = (
        'brand',
        'title', 
        'writer',
        'rating',
        'created_date',
        'updated_date',
        )
    search_fields = ('brand', 'title', 'writer__email',)


class MenuCommentAdmin(admin.ModelAdmin):
    list_display = (
        'post', 
        'content',
        'writer',
        'created_date',
        'updated_date',
        )
    search_fields = ('post__title', 'content', 'writer__email',)


admin.site.register(Menu, MenuAdmin)
admin.site.register(MenuComment, MenuCommentAdmin)