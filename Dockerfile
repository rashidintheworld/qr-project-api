FROM openjdk:17-jdk-alpine
ADD build/libs/*.jar qrcodeapp.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","qrcodeapp.jar"]