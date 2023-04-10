FROM openjdk:8
ARG JAR_FILE
ADD $JAR_FILE InnoMetrics-Collector-Server.jar
EXPOSE 9093
ENTRYPOINT ["java", "-jar", "InnoMetrics-Collector-Server.jar", "--spring.profiles.active=prod"]
