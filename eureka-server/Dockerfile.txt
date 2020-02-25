FROM openjdk:8
ADD innometrics-eureka-server.jar innometrics-eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "innometrics-eureka-server.jar"]
