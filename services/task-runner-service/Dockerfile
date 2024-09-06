FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} task-runner-service.jar
ENTRYPOINT ["java","-jar","/task-runner-service.jar"]