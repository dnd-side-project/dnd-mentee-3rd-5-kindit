import os
from django.conf import settings
from django.db import models
from django.utils import timezone
from datetime import datetime, timedelta
from uuid import uuid4


class Community(models.Model):
    writer = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.SET_NULL, null=True, verbose_name='작성자')
    content = models.TextField(verbose_name='내용')
    likes = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='community_likes', verbose_name='좋아요', blank=True)
    hits = models.PositiveIntegerField(verbose_name='조회수', default=0)
    comments = models.PositiveIntegerField(verbose_name='댓글수', default=0)
    upload_image = models.ImageField(upload_to="community/%Y/%m/%d", null=True, blank=True, verbose_name='커뮤니티이미지')
    created_date = models.DateTimeField(auto_now_add=True, verbose_name='등록일')
    updated_date = models.DateTimeField(auto_now=True, verbose_name='수정일')
    deleted = models.BooleanField(default=False, verbose_name='삭제여부')

    def __str__(self):
        return '%s - %s' % (self.writer, self.content)

    @property
    def total_likes(self):
        return self.likes.count()

    @property
    def created_string(self):
        time = datetime.now(tz=timezone.utc) - self.created_date

        if time < timedelta(minutes=1):
            return '방금 전'
        elif time < timedelta(hours=1):
            return str(int(time.seconds / 60)) + '분 전'
        elif time < timedelta(days=1):
            return str(int(time.seconds / 3600)) + '시간 전'
        elif time < timedelta(days=7):
            time = datetime.now(tz=timezone.utc).date() - self.created_date.date()
            return str(time.days) + '일 전'
        else:
            return False
 
    def upload_image_delete(self, *args, **kargs):
        if self.upload_image:
            os.remove(os.path.join(settings.MEDIA_ROOT, self.upload_image.path))
        super(Community, self).delete(*args, **kargs)

    class Meta:
        db_table = '커뮤니티'
        verbose_name = '커뮤니티'
        verbose_name_plural = '커뮤니티'

    
class CommunityComment(models.Model):
    post = models.ForeignKey(Community, on_delete=models.CASCADE, verbose_name='게시글')
    writer = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.SET_NULL, null=True, verbose_name='댓글작성자')
    content = models.TextField(verbose_name='댓글내용')
    likes = models.ManyToManyField(settings.AUTH_USER_MODEL, related_name='community_comment_likes', verbose_name='댓글좋아요', blank=True)
    reply = models.IntegerField(verbose_name='답글위치', default=0)
    created_date = models.DateTimeField(auto_now_add=True, verbose_name='댓글등록일')
    updated_date = models.DateTimeField(auto_now=True, verbose_name='댓글수정일')
    deleted = models.BooleanField(default=False, verbose_name='댓글삭제여부')

    def __str__(self):
        return '%s - %s' % (self.post, self.content)

    @property
    def total_likes(self):
        return self.likes.count()

    @property
    def created_string(self):
        time = datetime.now(tz=timezone.utc) - self.created_date

        if time < timedelta(minutes=1):
            return '방금 전'
        elif time < timedelta(hours=1):
            return str(int(time.seconds / 60)) + '분 전'
        elif time < timedelta(days=1):
            return str(int(time.seconds / 3600)) + '시간 전'
        elif time < timedelta(days=7):
            time = datetime.now(tz=timezone.utc).date() - self.created_date.date()
            return str(time.days) + '일 전'
        else:
            return False 

    class Meta:
        db_table = '커뮤니티 댓글'
        verbose_name = '커뮤니티 댓글'
        verbose_name_plural = '커뮤니티 댓글'