FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /build/target/quarkus-app /app/

CMD ["java", "-jar", "/app/quarkus-run.jar"]
