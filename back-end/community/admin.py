from django.contrib import admin
from .models import Community, CommunityComment


class CommunityAdmin(admin.ModelAdmin):
    list_display = (
        'writer',
        'content',
        'created_date',
        'updated_date',
        )
    search_fields = ('writer__email', 'content',)


class CommunityCommentAdmin(admin.ModelAdmin):
    list_display = (
        'post', 
        'writer',
        'content',
        'created_date',
        'updated_date',
        )
    search_fields = ('post__title', 'content', 'writer__email',)



admin.site.register(Community, CommunityAdmin)
admin.site.register(CommunityComment, CommunityCommentAdmin)