FROM maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app
COPY --from=builder /app/target/AnswerQ-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8081
ENV PORT=$PORT_APP

USER appuser

EXPOSE $PORT
ENTRYPOINT ["java","-jar","./AnswerQ-0.0.1-SNAPSHOT.jar"]