sudo `which docker-compose` build
docker rm -f friendlyeureka_ngapp_1
docker rm -f friendlyeureka_pyapp_1
sudo `which docker-compose` up -d
