
#!/usr/bin/env bash

apt-get install --fix-missing -y --no-install-recommends \
        wget \
        zip \
        unzip \
        software-properties-common

# Install Docker
set -ex \
    && curl -fL "https://download.docker.com/linux/static/${DOCKER_CHANNEL}/`uname -m`/docker-${DOCKER_VERSION}.tgz" -o docker.tgz \
    && tar --extract --file docker.tgz --strip-components 1 --directory /usr/local/bin \
    && rm docker.tgz \
    && docker -v

# Install Docker-Compose
set -x \
    && curl -fSL "https://github.com/docker/compose/releases/download/$DOCKER_COMPOSE_VERSION/docker-compose-`uname -s`-`uname -m`" -o /usr/local/bin/docker-compose \
    && chmod +x /usr/local/bin/docker-compose \
    && docker-compose -v

# Install Terraform
echo "===> Installing Terraform ${TERRAFORM_VERSION}..." \
    && wget https://releases.hashicorp.com/terraform/${TERRAFORM_VERSION}/terraform_${TERRAFORM_VERSION}_linux_amd64.zip \
    &&	unzip terraform_${TERRAFORM_VERSION}_linux_amd64.zip \
    && mv terraform /usr/local/bin/terraform \
    && rm terraform_${TERRAFORM_VERSION}_linux_amd64.zip 

# Install az cli
curl -sL https://aka.ms/InstallAzureCLIDeb | bash

# Install PowerShell
echo "===> Installing PowerShell..." \
    && wget -q https://packages.microsoft.com/config/ubuntu/18.04/packages-microsoft-prod.deb \
    && dpkg -i packages-microsoft-prod.deb \
    && apt-get update \
    && add-apt-repository universe \
    && apt-get install -y powershell \
    && rm -rf packages-microsoft-prod.deb \
    && pwsh --version

# Install kubectl
echo "===> Installing kubectl..." \
    && apt-get update && apt-get install -y apt-transport-https \
    && curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add - \
    && echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | tee -a /etc/apt/sources.list.d/kubernetes.list \
    && apt-get update \
    && apt-get install -y kubectl

# Install helm  
echo "===> Installing helm..." \
    && wget https://get.helm.sh/helm-${HELM_VERSION}-linux-amd64.tar.gz \
    && tar -zxvf helm-${HELM_VERSION}-linux-amd64.tar.gz \
    && mv linux-amd64/helm /usr/local/bin/helm \
    && rm -rf linux-amd64 \
    && rm helm-${HELM_VERSION}-linux-amd64.tar.gz

# Install Java tools
echo "===> Installing Java..." \
    && wget -qO - "https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public" | apt-key add - \
    && add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/ \
    && apt-get update \
    && apt-get install adoptopenjdk-${DEFAULT_JDK_VERSION}-openj9 -y \
    && java -version

# Install Ant
echo "===> Installing Ant..." \
    && apt-get install -y --no-install-recommends ant ant-optional \
    && echo "ANT_HOME=/usr/share/ant" | tee -a /etc/environment

# Install Maven
echo "===> Installing Maven..." \
    && curl -sL https://www-eu.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip -o maven.zip \
    && unzip -d /usr/share maven.zip \
    && rm maven.zip \
    && ln -s /usr/share/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn \
    && echo "M2_HOME=/usr/share/apache-maven-${MAVEN_VERSION}" | tee -a /etc/environment

# Install Gradle
echo "===> Installing Gradle..." \
    && wget -O gradleReleases.html https://gradle.org/releases/ \
    && gradleUrl=$(grep -m 1 -o "https:\/\/services.gradle.org\/distributions\/gradle-.*-bin\.zip" gradleReleases.html | head -1) \
    && gradleVersion=$(echo $gradleUrl | sed -nre 's/^[^0-9]*(([0-9]+\.)*[0-9]+).*/\1/p') \
    && rm gradleReleases.html \
    && echo "gradleUrl=$gradleUrl" \
    && echo "gradleVersion=$gradleVersion" \
    && curl -sL $gradleUrl -o gradleLatest.zip \
    && unzip -d /usr/share gradleLatest.zip \
    && rm gradleLatest.zip \
    && ln -s /usr/share/gradle-"${gradleVersion}"/bin/gradle /usr/bin/gradle \
    && echo "GRADLE_HOME=/usr/share/gradle" | tee -a /etc/environment

# Run tests to determine that the software installed as expected
echo "Testing to make sure that script performed as expected, and basic scenarios work"
for cmd in gradle java javac mvn ant; do
    if ! command -v $cmd; then
        echo "$cmd was not installed or found on path"
        exit 1
    fi
done