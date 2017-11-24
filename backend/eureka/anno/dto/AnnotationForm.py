# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime
from typing import List, Dict
import hashlib

class AnnotationBodyForm(object):
    def __init__(self, body):
        if body is not None:
            self.message = body.get('message') or ""

    def __str__(self):
        return "{message:" + self.message + "}"


    def hash(self):
        stringified = "" + self.__str__() + "__" + datetime.now().__str__()
        hashcode = hashlib.sha256(stringified.encode()).hexdigest()
        return hashcode

class AnnotationForm(object):
    def __init__(self, post):
        self.listory = post.get('listory') or None
        self.body = AnnotationBodyForm(post.get('body')) or None

    def __str__(self):
        return "{listory:"+ self.listory + ",body:"+ self.body.__str__() +"}"
