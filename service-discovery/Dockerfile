FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

RUN mvn package -DskipTests

FROM eclipse-temurin:17 as jre-builder

RUN $JAVA_HOME/bin/jlink \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /javaruntime

FROM debian:buster-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-builder /javaruntime $JAVA_HOME

RUN addgroup --system kioke && adduser --system --group kioke

USER kioke

WORKDIR /app

COPY --from=builder /app/target/service-discovery.jar ./

ENTRYPOINT [ "java", "-jar", "service-discovery.jar" ]

