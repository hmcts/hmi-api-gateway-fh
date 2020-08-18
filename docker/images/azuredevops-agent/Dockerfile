FROM ubuntu:16.04

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        apt-utils \
        apt-transport-https \
        ca-certificates \
        curl \
        jq \
        git \
        gnupg \
        iputils-ping \
        libcurl3 \
        libicu55 \
        lsb-release \
        openssh-server \
        software-properties-common \
        unzip \
        wget \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /azp

COPY ./start.sh .
RUN chmod +x start.sh

CMD ["./start.sh"]