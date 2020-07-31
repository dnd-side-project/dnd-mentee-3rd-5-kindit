from accounts.urls import urlpatterns as accounts_router
from django.urls import include, path


urlpatterns = [
    path("accounts/", include(accounts_router), name="accounts"),
]