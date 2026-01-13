# build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /src
COPY src/Main.java /src/Main.java
RUN javac Main.java && jar --create --file app.jar Main.class

# run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /src/app.jar /app/app.jar
EXPOSE 8080
ENV PORT=8080
CMD ["java","-jar","/app/app.jar"]

