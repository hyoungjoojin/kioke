# Stage 1
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

RUN mvn package -DskipTests

# Stage 2
FROM eclipse-temurin:17

RUN addgroup --system kioke && adduser --system --group kioke

USER kioke

WORKDIR /app

COPY --from=builder /app/target/auth-service.jar ./

ENTRYPOINT [ "java", "-jar", "auth-service.jar" ]

