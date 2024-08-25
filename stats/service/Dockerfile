FROM amazoncorretto:22-alpine-jdk
COPY target/*.jar stats.jar
ENTRYPOINT ["java","-jar","/stats.jar"]