from django.db import models 
from django.contrib.auth.models import (
    AbstractBaseUser,
    BaseUserManager,
    PermissionsMixin
)


class UserManager(BaseUserManager):    
    use_in_migrations = True    
    
    def create_user(self, user_id, user_nickname):        
        if not user_id :            
            raise ValueError('must have user user_id')        
        user = self.model(            
            user_id = user_id, 
            user_nickname = user_nickname        
        )        
        # user.set_password(password)        
        user.save(using=self._db)        
        return user     

    def create_superuser(self, user_id, user_nickname, password):        
       
        user = self.create_user(            
            user_id = user_id,            
            user_nickname = user_nickname,            
            password = password        
        )        
        user.is_admin = True        
        user.is_superuser = True        
        user.is_staff = True        
        user.save(using=self._db)        
        return user 


class User(AbstractBaseUser,PermissionsMixin):    
    
    objects = UserManager()
    
    user_id = models.CharField(        
        max_length=20,        
        unique=True,    
    )    
    user_nickname = models.CharField(
        max_length=20,
        null=False,
        unique=True
    )     
    is_active = models.BooleanField(default=True)    
    is_admin = models.BooleanField(default=False)    
    is_superuser = models.BooleanField(default=False)    
    is_staff = models.BooleanField(default=False)     
    date_joined = models.DateTimeField(auto_now_add=True)     
    USERNAME_FIELD = 'user_nickname'
    REQUIRED_FIELDS = ['user_id']

    class Meta:
        db_table = "회원목록"
        verbose_name = "사용자"
        verbose_name_plural = "사용자"