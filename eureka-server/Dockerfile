FROM openjdk:8
ARG JAR_FILE
ADD $JAR_FILE innometrics-eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "innometrics-eureka-server.jar"]