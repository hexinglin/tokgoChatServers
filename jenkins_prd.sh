#!/bin/bash
mvn clean package
echo "mvn build ok"

#当前版本号，需要和pom文件相同
version=2.0.1
docker_image=registry.cn-hangzhou.aliyuncs.com/xinglinhe/tokgo-servers:${version}
container_name='tokgo-chat-prd'

echo "正在停止所有docker里面的容器ing..."
docker stop ${container_name}
echo "停止成功,正在删除容器ing..."
docker rm ${container_name}
docker rmi ${docker_image}

echo "正在构建新的容器ing..."
mkdir docker
mkdir docker/resoures
cp Dockerfile docker/Dockerfile
cp resoures/config-prd.json docker/resoures/config.json
cp target/tokgoChatServers-${version}-SNAPSHOT-jar-with-dependencies.jar docker/tokgochat.jar
cp target/tokgoChatServers-2.0.10-SNAPSHOT-jar-with-dependencies.jar docker/tokgochat.jar

cd docker
docker build -t ${docker_image} .
docker build -t registry.cn-hangzhou.aliyuncs.com/xinglinhe/tokgo-servers:2.0.10 .

echo "构建成功,运行容器ing..."
docker run --name ${container_name} -p 1315:1315 -v /etc/localtime:/etc/localtime:ro  -v /etc/timezone:/etc/timezone:ro  -d ${docker_image}

echo `docker ps`
docker ps