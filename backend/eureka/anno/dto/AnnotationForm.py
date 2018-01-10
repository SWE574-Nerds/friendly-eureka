# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime
from typing import List, Dict
import hashlib

class AnnotationBodyForm(object):
    def __init__(self, body):
        if body is not None:
            self.message = body.get('message') or ""
            self.link = body.get('link') or ""

    def __str__(self):
        return "{message:" + self.message + ", link:" + self.link + "}"


    def hash(self):
        stringified = "" + self.__str__() + "__" + datetime.now().__str__()
        hashcode = hashlib.sha256(stringified.encode()).hexdigest()
        return hashcode

class TextSelector(object):
    def __init__(self, textSelector):
        self.startsWith = textSelector.get('startsWith') or ""
        self.endsWith = textSelector.get('endsWith') or ""
        self.selection = textSelector.get('selection') or ""

class ImageSelector(object):
     def __init__(self, imageSelector):
        self.imageLink = imageSelector.get('imgLink') or ""

class AnnotationForm(object):
    def __init__(self, post):
        self.listory = post.get('listory') or None
        self.body = AnnotationBodyForm(post.get('body')) or None
        self.selector = None

        providedSelector = post.get('selector') or { 'text': None } or { 'image': None }

        if (providedSelector is not None) and (providedSelector.get('text') is not None):
            self.selector = {'text' : TextSelector(providedSelector.get('text'))}
        elif(providedSelector is not None) and (providedSelector.get('image') is not None):
            self.selector = {'image': ImageSelector(providedSelector.get('image'))}



    def __str__(self):
        return "{listory:"+ self.listory + ",body:"+ self.body.__str__() +"}"

