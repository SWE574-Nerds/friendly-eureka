# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime
from typing import List, Dict


class ListoryForm(object):
    def __init__(self, name: str=None, description: str=None, image: str=None):
        self._name = name
        self._description = description
        self._image = image

    def __init__(self, post):
        self._name = post.get('name') or None
        self.description = post.get('description') or None
        self.image = post.get('image') or None

    @property
    def name(self) -> str:
        return self._name

    @name.setter
    def name(self, name: str):
        self._name = name

    @property
    def description(self) -> str:
        return self._description

    @description.setter
    def description(self, description: str):
        self._description = description

    @property
    def image(self) -> str:
        return self._image

    @image.setter
    def image(self, image: str):
        self._image = image

