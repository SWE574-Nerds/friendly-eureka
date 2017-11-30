# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime
from typing import List, Dict

class ListoryTimeInfoForm(object):
    def __init__(self, timeInfo):
        if timeInfo is not None:
            self.id = timeInfo.get('id') or None
            self.value1 = timeInfo.get('value_1') or None
            self.value2 = timeInfo.get('value_2') or None

class ListoryForm(object):
    def __init__(self, post):
        self.name = post.get('name') or None
        self.description = post.get('description') or None
        self.image = post.get('image') or None
        self.timeInfo = ListoryTimeInfoForm(post.get('timeInfo')) or None
        self.tags = []
        self.markers = post.get("markers") or []

        tag_set = post.get('tags') or []
        for tag in tag_set:
            self.tags.append(tag)