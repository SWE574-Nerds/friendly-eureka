from rest_framework import status
from django.http import JsonResponse

from api.models import Tokens

class AuthMiddleware(object):
    def process_request(self, request):
        request.test = 5
        token = request.META['HTTP_AUTHORIZATION']

        try:
            object = Tokens.objects.get(token=token)
            request.api_user = object.user
            request.valid_api_user = True
            return None
        except Tokens.DoesNotExist:
            return JsonResponse({'error': 'Unauthorized'}, status=401)
