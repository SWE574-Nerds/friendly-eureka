# coding: utf-8
from __future__ import absolute_import

class LoginForm(object):
    def __init__(self, name: str=None, password: str=None):
        self._name = name
        self._password = password


    def __init__(self, post):
        self._name = post.get('name') or None
        self._password = post.get('password') or None

    @property
    def name(self) -> str:
        return self._name

    @name.setter
    def name(self, name: str):
        self._name = name

    @property
    def password(self) -> str:
        return self._password

    @password.setter
    def password(self, password: str):
        self._password = password

