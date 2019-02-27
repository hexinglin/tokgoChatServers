FROM javabase

#指定镜像创建者信息
MAINTAINER hexinglin

ADD tokgochat.jar app.jar


ENTRYPOINT ["java","-jar","/app.jar"]