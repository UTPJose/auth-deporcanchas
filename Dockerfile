FROM eclipse-temurin:25-jdk
ARG JAR_FILE=target/auth-deporcanchas-0.0.1.jar
COPY ${JAR_FILE} auth-deporcanchas.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "auth-deporcanchas.jar"]
