FROM openjdk:8
ARG JAR_FILE
ADD $JAR_FILE InnoMetrics-RestAPI.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "InnoMetrics-RestAPI.jar", "--spring.profiles.active=dev"]
