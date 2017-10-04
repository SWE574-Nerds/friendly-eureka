import socket
import time
import os

port = int(os.environ["POSTGRES_PORT"])
host = os.environ["POSTGRES_HOST"]



while True:
    try:
        print("Waiting for DB")
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((host, port))
        s.close()
        print("DB Ready.")
        break
    except socket.error as ex:
        time.sleep(1)
        print("Sleeping 1")
