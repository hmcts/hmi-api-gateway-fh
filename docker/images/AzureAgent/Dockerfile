FROM ubuntu:18.04

# To make it easier for build and release pipelines to run apt-get,
# configure apt to not require confirmation (assume the -y argument by default)
ENV DEBIAN_FRONTEND=noninteractive

RUN echo "APT::Get::Assume-Yes \"true\";" > /etc/apt/apt.conf.d/90assumeyes

RUN apt-get update \
        && apt-get install -y --no-install-recommends \
        ca-certificates \
        curl \
        jq \
        git \
        iputils-ping \
        libcurl4 \
        libicu60 \
        libunwind8 \
        netcat \
        zip

WORKDIR /azp

# Install custom dependencies   
ENV DOCKER_CHANNEL stable
ENV DOCKER_VERSION 18.06.3-ce
ENV DOCKER_COMPOSE_VERSION 1.26.2
ENV TERRAFORM_VERSION 0.12.16
ENV HELM_VERSION v3.3.1
ENV DEFAULT_JDK_VERSION 11
ENV MAVEN_VERSION 3.6.3

COPY ./addons.sh .
COPY ./start.sh .

RUN chmod +x addons.sh start.sh

RUN ./addons.sh

CMD ["./start.sh"]