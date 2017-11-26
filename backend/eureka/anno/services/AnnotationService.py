from anno.models import Annotation
from anno.dto import AnnotationForm
from post.services import ListoryService
import json

VIEW_PATH = "http://localhost:3000/listory/{id}"
ANNO_GET_PATH = "http://localhost:8000/api/annotation/{id}/body"

from anno.RedisFactory import RedisFactory

class AnnotationService(object):
    def __init__(self):
        redisFactory = RedisFactory();
        self.redis = redisFactory.getRedisConnection();


    def getAnnotationJSONLD(self, storeKey):
        try:
            return json.loads(self.redis.get(storeKey))
        except:
            return None


    def getAnnotationBody(self, storeKey):
        annotation = self.getAnnotation(storeKey)
        return annotation.message



    def getAnnotation(self, storeKey):
        try:
            return Annotation.objects.get(storeKey__exact=storeKey)
        except:
            return None


    def createImageAnnotationJSONLD(self, body, listoryId):

        hash = body.hash()

        anno = {
            "@context": "http://www.w3.org/ns/anno.jsonld",
            "id": hash,
            "type": "Annotation",
            "creator": "",  # Will be set later
            "body": [],
            "target": VIEW_PATH.replace("{id}", listoryId)
        }

        if body.message:
            anno["body"].append({
                "type": "TextualBody",
                "value": body.message,
                "format": "text/plain",
            })
        if body.link:
            anno["body"].append({
                "type": "Image",
                "value": body.link,
                "format": "image/png",
            })

        return anno, hash


    def createPlainTextAnnotationJSONLD(self, body, listoryId):

        hash = body.hash()

        anno = {
            "@context": "http://www.w3.org/ns/anno.jsonld",
            "id": hash,
            "type": "Annotation",
            "creator": "", # Will be set later
            "body": {
                "type": "TextualBody",
                "value": body.message,
                "format": "text/plain",
            },
            "target": VIEW_PATH.replace("{id}", listoryId)
        }

        return anno, hash

    def createHighlightAnnotationJSONLD(self, body, listoryId):

        hash = body.hash()

        anno = {
            "@context": "http://www.w3.org/ns/anno.jsonld",
            "id": hash,
            "creator": "", # Will be set later
            "type": "Annotation",
            "target": VIEW_PATH.replace("{id}", listoryId)
        }

        return anno, hash


    def createImageAnnotation(self, form, user):
        anno, hash = self.createImageAnnotationJSONLD(form.body, form.listory)

        anno['creator'] = "http://localhost:8000/api/user/" + str(user.id) + "/";

        self.redis.set(hash, json.dumps(anno))

        Annotation.objects.create(message=form.body.message,
        storeKey = hash,
        listory = ListoryService.get_listory_by_id(
        form.listory),
        author = user);

        return anno, hash



    def createTextAnnotation(self, form, user):
        anno, hash = self.createPlainTextAnnotationJSONLD(form.body, form.listory)

        anno['creator'] = "http://localhost:8000/api/user/" + str(user.id) + "/";

        self.redis.set(hash, json.dumps(anno))

        Annotation.objects.create(message=form.body.message,
        storeKey = hash,
        listory = ListoryService.get_listory_by_id(
        form.listory),
        author = user);

        return anno, hash


    def createHighlightAnnotation(self, form, user):
        anno, hash = self.createHighlightAnnotationJSONLD(form.body, form.listory)

        anno['creator'] = "http://localhost:8000/api/user/" + str(user.id) + "/";

        self.redis.set(hash, json.dumps(anno))

        Annotation.objects.create(message=form.body.message,
        storeKey = hash,
        listory = ListoryService.get_listory_by_id(
        form.listory),
        author = user);

        return anno, hash


    def getAnnotationsOfListory(self, listoryId):

        return Annotation.objects.filter(listory_id__exact=listoryId)
