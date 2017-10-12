from django.contrib.auth.models import User
from django.contrib.auth import authenticate

from api.dto.RegisterForm import RegisterForm
from api.validators.RegisterFormValidator import RegisterFormValidator
from api.validators.LoginFormValidator import LoginFormValidator
from api.dto.LoginForm import LoginForm

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from django.db import IntegrityError


@api_view(['POST'])
def api_login(request):
    form = LoginForm(request.data)
    validator = LoginFormValidator()

    errors = validator.validate(form)

    if len(errors):
        return Response(errors, status=status.HTTP_406_NOT_ACCEPTABLE)
    else:
        user = authenticate(username=form.name, password=form.password)
        if user is not None:
            return Response("5", status=status.HTTP_200_OK)
        else:
            return Response("Unauthorized", status=status.HTTP_401_UNAUTHORIZED)

@api_view(['POST'])
def api_register(request):
    form = RegisterForm(request.data)
    validator = RegisterFormValidator()

    errors = validator.validate(form)

    if len(errors):
        return Response(errors, status=status.HTTP_406_NOT_ACCEPTABLE)
    else:
        try:
            user = User.objects.create_user(username=form.name,
                                        email=form.mail,
                                        password=form.password)
            user.save()
            return Response("OK", status=status.HTTP_200_OK)
        except IntegrityError as e:
            return Response("User exists", status.HTTP_400_BAD_REQUEST)
