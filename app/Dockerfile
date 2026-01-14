# build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/sentiment-api-1.0.0.jar /app/app.jar
EXPOSE 8080
CMD ["java","-jar","/app/app.jar"]

