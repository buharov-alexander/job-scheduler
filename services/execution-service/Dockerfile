FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} execution-service.jar
ENTRYPOINT ["java","-jar","/execution-service.jar"]