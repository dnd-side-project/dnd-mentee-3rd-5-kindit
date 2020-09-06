from django.contrib import admin
from .models import Menu, BaseMenu, MenuIngredient, MenuComment


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


class BaseMenuAdmin(admin.ModelAdmin):
    list_display = (
        'brand', 
        'name',
        )
    search_fields = ('brand', 'name',)


class MenuIngredientAdmin(admin.ModelAdmin):
    list_display = (
        'brand', 
        'ingredient_type',
        'name',
        )
    search_fields = ('brand', 'name',)


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
admin.site.register(BaseMenu, BaseMenuAdmin)
admin.site.register(MenuIngredient, MenuIngredientAdmin)
admin.site.register(MenuComment, MenuCommentAdmin)