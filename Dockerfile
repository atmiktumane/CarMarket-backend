# ============================
# Stage 1: Build JAR with Maven
# ============================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# 1. Copy pom.xml, mvnw, and the .mvn directory
#    This is crucial for Maven to find its configuration.
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn/

# 2. Download dependencies only (caching)
RUN ./mvnw dependency:go-offline -B

# 3. Copy source code
COPY src ./src

# 4. Build the Spring Boot JAR (skip tests)
RUN ./mvnw clean package -DskipTests

# ============================
# Stage 2: Run JAR
# ============================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose default port (Render will inject $PORT)
EXPOSE 8080

# Spring Boot environment variables (set in Render dashboard)
ENV SPRING_APPLICATION_NAME=${SPRING_APPLICATION_NAME}
ENV SPRING_DATA_MONGODB_URI=${SPRING_DATA_MONGODB_URI}
ENV SPRING_MAIL_HOST=${SPRING_MAIL_HOST}
ENV SPRING_MAIL_PORT=${SPRING_MAIL_PORT}
ENV SPRING_MAIL_USERNAME=${SPRING_MAIL_USERNAME}
ENV SPRING_MAIL_PASSWORD=${SPRING_MAIL_PASSWORD}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE}

# Run the app
CMD ["java", "-jar", "app.jar"]