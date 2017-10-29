from rest_framework import status
from django.http import JsonResponse

from api.models import Tokens

class AuthMiddleware(object):
    def process_request(self, request):

        try:
            token = request.META['HTTP_AUTHORIZATION']
            object = Tokens.objects.get(token=token)
            request.api_user = object.user
            request.valid_api_user = True
            return None
        except:
            return JsonResponse({'error': 'Unauthorized'}, status=401)
