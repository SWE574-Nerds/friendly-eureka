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
 #       try:
            return json.loads(self.redis.get(storeKey))
 #       except:
#            return None


    def getAnnotationBody(self, storeKey):
        annotation = Annotation.objects.get(storeKey__exact=storeKey)
        return annotation.message


    def createBasicAnnotationJSONLD(self, body, listoryId):

        hash = body.hash()

        anno = {
          "@context": "http://www.w3.org/ns/anno.jsonld",
          "id": hash,
          "type": "Annotation",
          "body": ANNO_GET_PATH.replace("{id}", hash),
          "target": VIEW_PATH.replace("{id}", listoryId)
        }

        return anno, hash

    def createAnnotation(self, form):
        anno, hash = self.createBasicAnnotationJSONLD(form.body, form.listory)

        self.redis.set(hash, json.dumps(anno))

        Annotation.objects.create( message=form.body.message,
                                   storeKey=hash,
                                   listory=ListoryService.get_listory_by_id(form.listory)
        )

        return anno, hash




