FROM maven:3.9.9-eclipse-temurin-21-alpine

WORKDIR /app

COPY pom.xml .

RUN \
    mvn dependency:go-offline -DskipTests

COPY src src

RUN \
    mvn clean install -DskipTests

ENTRYPOINT ["java", "-jar"]
CMD ["./target/pet_app-0.0.1-SNAPSHOT.jar"]
