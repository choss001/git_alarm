FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./build/libs/demo-0.0.1-SNAPSHOT.war app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
