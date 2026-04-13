FROM eclipse-temurin:25-jdk
ARG JAR_FILE=target/auth-app-backend-0.0.1.jar
COPY ${JAR_FILE} auth-app-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "auth-app-backend.jar"]
