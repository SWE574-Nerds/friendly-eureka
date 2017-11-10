import os
from redis import Redis


class RedisFactory(object):
    def getRedisConnection(self):
        redisPath = "";
        dbObject = {}
        if os.getenv('DOCKER_CONTAINER', 'false') == 'false':  # Running locally
            redisPath = "0.0.0.0"
        else:
            redisPath = "redis"

        redis = Redis(host=redisPath, port=6379)

        return redis