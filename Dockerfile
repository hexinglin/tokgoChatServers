FROM javabase

#指定镜像创建者信息
MAINTAINER hexinglin

ADD tokgochat.jar app.jar
ADD resoures resoures
ENV LANG C.UTF-8

ENTRYPOINT ["java","-jar","/app.jar"]