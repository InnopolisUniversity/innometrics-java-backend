FROM openjdk:8
ADD innometrics-auth-server.jar innometrics-auth-server.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "innometrics-auth-server.jar", "--spring.profiles.active=qa"]
