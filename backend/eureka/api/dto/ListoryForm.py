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

class Point(object):
    def __init__(self, point):
        self.lat = point.get('lat') or 0
        self.long = point.get('long') or 0

class Polyline(object):
    def __init__(self, polyline):
        self.color = polyline.get('color') or "#ff3a3a"
        self.name = polyline.get('name') or ""
        self.points = []
        points = polyline.get('points') or []
        for point in points:
            self.points.append(Point(point))

class ListoryForm(object):
    def __init__(self, post):
        self.name = post.get('name') or ""
        self.description = post.get('description') or ""
        self.image = post.get('image') or ""
        self.timeInfo = ListoryTimeInfoForm(post.get('timeInfo')) or ""
        self.tags = []
        tag_set = post.get('tags') or []
        for tag in tag_set:
            self.tags.append(tag)

        self.markers = post.get("markers") or []

        self.polylines = []
        polylines = post.get("polylines") or []
        for polyline in polylines:
            self.polylines.append(Polyline(polyline))
