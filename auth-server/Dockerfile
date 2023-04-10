FROM openjdk:8
ARG JAR_FILE
ADD $JAR_FILE innometrics-auth-server.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "innometrics-auth-server.jar", "--spring.profiles.active=dev"]
