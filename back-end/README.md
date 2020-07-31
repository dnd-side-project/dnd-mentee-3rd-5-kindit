## Execution Test


### Install PIP

```
$ pip install -r requirements.txt
```


### Create Media Directory & Secrets File

```
$ mkdir media
$ type NUL > secrets.json // Window
$ touch secrets.json // Linux
```

### DB Migration

```
$ python manage.py makemigrations
$ python manage.py migrate
```

### Create Super User

```
$ python manage.py createsuperuser
```

### Runserver

```
$ python manage.py runserver
```