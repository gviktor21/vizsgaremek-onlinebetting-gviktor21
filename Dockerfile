FROM maven:3.8.6-openjdk-11-slim as builder
WORKDIR /app
COPY ./pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean package -DskipTest

FROM openjdk:11-alpine
WORKDIR /app
COPY --from=builder /app/target/onlinebet.jar ./app.jar
CMD java -jar ./app.jar