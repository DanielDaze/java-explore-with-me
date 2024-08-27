FROM amazoncorretto:21-alpine-jdk
COPY target/stats-service-0.0.1-SNAPSHOT-jar-with-dependencies.jar stats.jar
ENTRYPOINT ["nohup","java","-jar","/stats.jar"]