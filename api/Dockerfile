# Build stage
FROM amazoncorretto:23-alpine-jdk AS builder
WORKDIR /build
COPY . .
RUN ./gradlew bootJar

# Run stage
FROM amazoncorretto:23-alpine-jdk
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=builder /build/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
