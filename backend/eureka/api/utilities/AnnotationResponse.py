from anno.services.AnnotationService import AnnotationService


class AnnotationResponse(object):
    def __init__(self, storeKey):
        service = AnnotationService()
        anno = service.getAnnotation(storeKey)

        self.value = {
            "json" :service.getAnnotationJSONLD(storeKey),
            "author": {
                "name" : anno.author.username,
                "userId" : anno.author.pk
            },
            "createdAt": None,
            "body": service.getAnnotationBody(storeKey)
        }
