FROM openjdk:8
ARG JAR_FILE
ADD $JAR_FILE innometrics-config-service.jar
EXPOSE 9094
ENTRYPOINT ["java", "-jar", "innometrics-config-service.jar", "--spring.profiles.active=prod"]