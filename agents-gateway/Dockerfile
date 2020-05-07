FROM openjdk:8
ADD InnoMetrics-Agents-gateway.jar InnoMetrics-Agents-gateway.jar
EXPOSE 9094
ENTRYPOINT ["java", "-jar", "InnoMetrics-Agents-gateway.jar", "--spring.profiles.active=prod"]
