# -------- Stage 1: Build --------
FROM gradle:8.7-jdk21 AS builder
WORKDIR /app

# Copy everything (so Gradle can resolve module dependencies)
COPY --chown=gradle:gradle . .

# Build only the backend module (and its dependencies)
RUN gradle :expense-tracker-service:bootJar --no-daemon

# -------- Stage 2: Run --------
FROM eclipse-temurin:21-jdk-alpine

# Copy jar from build stage
COPY --from=builder /app/expense-tracker-service/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]