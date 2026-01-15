FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar
COPY catalogo catalogo

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]