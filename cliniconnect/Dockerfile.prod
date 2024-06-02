# Use OpenJDK 17 as the base image
FROM openjdk:17 AS builder
WORKDIR /app
COPY . .
COPY src ./src
RUN ./mvnw package -DskipTests
FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/cliniconnect-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]