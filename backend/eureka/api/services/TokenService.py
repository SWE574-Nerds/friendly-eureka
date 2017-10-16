from api.models import Tokens

class TokenService(object):

    def assignToken(self, user):
        if user.is_authenticated():

            token = Tokens.objects.create(user=user)
            token.save()

            if token.pk is not None:
                return token.token
            else:
                return None

    def deferToken(self, token):
        object = Tokens.objects.get(token__exact=token)

        if object is not None:
            return object.user
        else:
            return None
