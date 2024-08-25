FROM amazoncorretto:22-alpine-jdk
COPY target/service-0.0.1-SNAPSHOT-jar-with-dependencies.jar stats.jar
ENTRYPOINT ["java","-jar","/stats.jar"]