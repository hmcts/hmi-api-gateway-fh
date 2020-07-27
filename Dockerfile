#ARG APP_INSIGHTS_AGENT_VERSION=2.5.1

# Application image

#FROM hmctspublic.azurecr.io/base/java:openjdk-11-distroless-1.2

#COPY lib/AI-Agent.xml /opt/app/
#COPY build/libs/spring-boot-template.jar /opt/app/

#EXPOSE 4550
#CMD [ "spring-boot-template.jar" ]

FROM openjdk:11
MAINTAINER HMI-QA
WORKDIR /opt/app/wiremock
COPY ./wiremock/ /opt/app/wiremock/
RUN chmod 777 *
RUN ls -al /opt/app/wiremock/

EXPOSE 8080:8080
#RUN java -jar wiremock-jre8-standalone-2.27.0.jar
ENTRYPOINT ["java", "-jar", "wiremock-jre8-standalone-2.27.0.jar"]
