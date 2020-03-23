FROM openjdk:8
ADD InnoMetrics-Collector-Server.jar InnoMetrics-Collector-Server.jar
EXPOSE 9093
ENTRYPOINT ["java", "-jar", "InnoMetrics-Collector-Server.jar", "--spring.profiles.active=qa"]
