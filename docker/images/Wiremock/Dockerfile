FROM openjdk:11
LABEL maintainer="HMI-QA"
ENV WIREMOCK_VERSION 2.27.1
WORKDIR /opt/app/wiremock
COPY wiremock/ /opt/app/wiremock/
RUN curl -s -O https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-jre8-standalone/$WIREMOCK_VERSION/wiremock-jre8-standalone-$WIREMOCK_VERSION.jar
RUN chmod a+x wiremock-jre8-standalone-$WIREMOCK_VERSION.jar
EXPOSE 8080
ENTRYPOINT java -jar wiremock-jre8-standalone-$WIREMOCK_VERSION.jar