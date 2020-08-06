FROM openjdk:11
LABEL maintainer = HMI-QA
WORKDIR /opt/app/wiremock
COPY ./wiremock/ /opt/app/wiremock/
RUN chmod a+x wiremock-jre8-standalone-2.27.1.jar
RUN ls -al /opt/app/wiremock/
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "wiremock-jre8-standalone-2.27.1.jar"]
