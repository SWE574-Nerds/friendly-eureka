import os
from redis import StrictRedis


class RedisFactory(object):
    def getRedisConnection(self):
        redisPath = "";
        dbObject = {}
        if os.getenv('DOCKER_CONTAINER', 'false') == 'false':  # Running locally
            redisPath = "0.0.0.0"
        else:
            redisPath = "redis"

        redis = StrictRedis(host=redisPath, port=6379, password='1A2S3D4F5G')

        return redis
