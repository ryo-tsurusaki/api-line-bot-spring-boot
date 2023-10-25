FROM openjdk:11-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/build/libs/lineBotApi-0.1-SNAPSHOT.jar ./api.jar
ENTRYPOINT ["java", "-jar", "api.jar"]
