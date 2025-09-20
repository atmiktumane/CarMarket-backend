# ============================
# Stage 1: Build JAR with Maven
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src
COPY mvnw .
COPY .mvn .mvn

# Build Spring Boot JAR
RUN ./mvnw clean package -DskipTests

# ============================
# Stage 2: Run JAR
# ============================
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render injects $PORT)
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]
