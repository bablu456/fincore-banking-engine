
From openjdk:21-jdk-slim

LABEL maintainer="codewithbablu"

WORKDIR /app

COPY target/fincore-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]