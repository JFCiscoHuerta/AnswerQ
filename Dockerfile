FROM openjdk:21-jdk AS builder
WORKDIR /app
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/target/AnswerQ-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8081
ENV PORT=$PORT_APP
EXPOSE $PORT


ENTRYPOINT ["java","-jar","./AnswerQ-0.0.1-SNAPSHOT.jar"]