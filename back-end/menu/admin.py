from django.contrib import admin
from .models import Menu, MenuComment, MenuIngredient


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


class MenuIngredientAdmin(admin.ModelAdmin):
    list_display = (
        'brand', 
        'ingredient_type',
        'name',
        )
    search_fields = ('brand', 'name',)


admin.site.register(Menu, MenuAdmin)
admin.site.register(MenuComment, MenuCommentAdmin)
admin.site.register(MenuIngredient, MenuIngredientAdmin)