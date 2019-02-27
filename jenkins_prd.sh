#!/bin/bash
mvn clean package
echo "mvn build ok"

#当前版本号，需要和pom文件相同
version=1.2.4
docker_image=tokgo-chat-prd-${version}
container_name='tokgo-chat-prd'

echo "正在停止所有docker里面的容器ing..."
docker stop ${container_name}
echo "停止成功,正在删除容器ing..."
docker rm ${container_name}
docker rmi ${docker_image}

echo "正在构建新的容器ing..."
mkdir docker
cp -p Dockerfile docker/Dockerfile
cp -p target/tokgoChatServers-${version}-SNAPSHOT-jar-with-dependencies.jar docker/tokgochat.jar
cd docker
docker build -t ${docker_image}:latest .


echo "构建成功,运行容器ing..."
docker run --name ${container_name} -p 1315:1315 -v /etc/localtime:/etc/localtime:ro  -d ${docker_image}

echo `docker ps`
docker ps