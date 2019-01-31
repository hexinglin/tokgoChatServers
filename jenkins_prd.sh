#!/bin/bash
mvn clean package
echo "mvn build ok"

#当前版本号，需要和pom文件相同
version=1.0.2
filename=tokgoChatServers-${version}-SNAPSHOT-jar-with-dependencies.jar

#复制文件到prd路径下
mkdir /home/tokgo/prd/${version} -p
cp target/${filename} /home/tokgo/prd/${version}/${filename} -f