from django.db import models
from django.contrib.auth.models import User
import uuid

# Create your models here.

class Tokens(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    token = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)