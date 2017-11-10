# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime
from typing import List, Dict

class AnnotationBodyForm(object):
    def __init__(self, body):
        if body is not None:
            self.message = body.get('message') or None

class AnnotationForm(object):
    def __init__(self, post):
        self.listory = post.get('listory') or None
        self.body = AnnotationBodyForm(post.get('body')) or None
