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

    def createImageAnnotationJSONLD(self, form: AnnotationForm, listoryId):

        hash = form.body.hash()

        anno = {
            "@context": "http://www.w3.org/ns/anno.jsonld",
            "id": hash,
            "type": "Annotation",
            "creator": "",  # Will be set later
            "body": [],
            "selector": [],
            "target": VIEW_PATH.replace("{id}", listoryId)
        }

        if form.body.message:
            anno["body"].append({
                "type": "TextualBody",
                "value": form.body.message,
                "format": "text/plain",
            })
        if form.body.link:
            anno["body"].append({
                "type": "Image",
                "value": form.body.link,
                "format": "image/png",
            })

        selector = form.selector
        if (selector is not None):
            textSelector = selector["text"]
            anno["selector"].append({
                "exact": textSelector.selection,
                "prefix": textSelector.startsWith,
                "suffix": textSelector.endsWith,
                "type": "TextQuote"

            });
        return anno, hash

    def createPlainTextAnnotationJSONLD(self, form: AnnotationForm, listoryId):

        hash = form.body.hash()

        anno = {
            "@context": "http://www.w3.org/ns/anno.jsonld",
            "id": hash,
            "type": "Annotation",
            "creator": "",  # Will be set later
            "body": {
                "type": "TextualBody",
                "value": form.body.message,
                "format": "text/plain",
                "shape": form.body.shape
            },
            "selector": [],
            "target": VIEW_PATH.replace("{id}", str(listoryId))
        }

        selector = form.selector
        if (selector is not None):
            textSelector = selector["text"]
            anno["selector"].append({
                "exact": textSelector.selection,
                "prefix": textSelector.startsWith,
                "suffix": textSelector.endsWith,
                "type": "TextQuote"

            });

        return anno, hash

    def createHighlightAnnotationJSONLD(self, body, listoryId):

        hash = body.hash()

        anno = {
            "@context": "http://www.w3.org/ns/anno.jsonld",
            "id": hash,
            "creator": "",  # Will be set later
            "type": "Annotation",
            "target": VIEW_PATH.replace("{id}", listoryId)
        }

        return anno, hash

    def createImageAnnotation(self, form, user):
        anno, hash = self.createImageAnnotationJSONLD(form, form.listory)

        anno['creator'] = "http://localhost:8000/api/user/" + str(user.id) + "/";

        self.redis.set(hash, json.dumps(anno))

        Annotation.objects.create(message=form.body.message,
                                  storeKey=hash,
                                  listory=ListoryService.get_listory_by_id(
                                      form.listory),
                                  author=user);

        return anno, hash

    def createTextAnnotation(self, form: AnnotationForm, user):
        anno, hash = self.createPlainTextAnnotationJSONLD(form, form.listory)

        anno['creator'] = "http://localhost:8000/api/user/" + str(user.id) + "/";

        self.redis.set(hash, json.dumps(anno))

        Annotation.objects.create(message=form.body.message,
                                  storeKey=hash,
                                  listory=ListoryService.get_listory_by_id(
                                      form.listory),
                                  author=user,
                                  x=form.body.x,
                                  y=form.body.y,
                                  width=form.body.width,
                                  height=form.body.height
                                  );

        return anno, hash

    def createHighlightAnnotation(self, form, user):
        anno, hash = self.createHighlightAnnotationJSONLD(form.body, form.listory)

        anno['creator'] = "http://localhost:8000/api/user/" + str(user.id) + "/";

        self.redis.set(hash, json.dumps(anno))

        Annotation.objects.create(message=form.body.message,
                                  storeKey=hash,
                                  listory=ListoryService.get_listory_by_id(
                                      form.listory),
                                  author=user);

        return anno, hash

    def getAnnotationsOfListory(self, listoryId):

        return Annotation.objects.filter(listory_id__exact=listoryId)
