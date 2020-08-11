FROM openjdk:11
LABEL maintainer="HMI-QA"
RUN apk add curl
WORKDIR /opt/app/wiremock
COPY wiremock/ /opt/app/wiremock/
RUN curl -s -O https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-jre8-standalone/2.27.1/wiremock-jre8-standalone-2.27.1.jar
RUN chmod a+x wiremock-jre8-standalone-2.27.1.jar
EXPOSE 8080:8080
# ENTRYPOINT ["java", "-jar", "wiremock-jre8-standalone-2.27.1.jar"]