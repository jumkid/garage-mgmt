# Docker for Content Vault microserivce 
FROM openjdk:20
ARG env
# local file storage path
RUN mkdir -p /opt/garage-mgmt/logs

COPY src/main/resources/application.${env}.properties /opt/garage-mgmt/application.properties
COPY src/main/resources/redission.${env}.yaml /opt/garage-mgmt/redission.yaml
COPY target/garage-mgmt-*.jar /opt/garage-mgmt/garage-mgmt.jar

RUN ln -sf /dev/stdout /opt/garage-mgmt/logs/garage-mgmt.sys.log
WORKDIR /opt/garage-mgmt

CMD ["java", "-jar", "garage-mgmt.jar", "--spring.config.additional-location=application.properties"]

EXPOSE 8087
