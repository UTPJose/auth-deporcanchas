FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY target/auth-deporcanchas-0.0.1.jar target/auth-deporcanchas-0.0.1.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/auth-deporcanchas-0.0.1.jar"]