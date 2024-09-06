FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-service.jar
ENTRYPOINT ["java","-jar","/api-service.jar"]