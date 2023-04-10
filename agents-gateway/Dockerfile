FROM openjdk:8
ARG JAR_FILE
ADD $JAR_FILE InnoMetrics-Agents-gateway.jar
EXPOSE 9094
ENTRYPOINT ["java", "-jar", "InnoMetrics-Agents-gateway.jar", "--spring.profiles.active=prod"]
