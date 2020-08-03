# HMI API Gateway

[![Build Status](https://dev.azure.com/hmcts/Shared%20Services/_apis/build/status/hmcts.hmi-api-gateway-fh?branchName=HMIS-152_SANDBOX_CI%2FCD_Pipeline)](https://dev.azure.com/hmcts/Shared%20Services/_build/latest?definitionId=356&branchName=HMIS-152_SANDBOX_CI%2FCD_Pipeline)

## Purpose

The purpose of this template is to speed up the creation of tests for the Functional,
Integration, Contract and Smoke Tests.

## What's inside

The template is a working application with a minimal setup. It contains:
 * application skeleton
 * setup script to prepare project
 * common plugins and libraries
 * docker setup
 * swagger configuration for api documentation ([see how to publish your api documentation to shared repository](https://github.com/hmcts/reform-api-docs#publish-swagger-docs))
 * code quality tools already set up
 * integration with Travis CI
 * Hystrix circuit breaker enabled
 * MIT license and contribution information
 * Helm chart using chart-java.

The application exposes health endpoint (http://localhost:4550/health) and metrics endpoint
(http://localhost:4550/metrics).

## Plugins

The template contains the following plugins:

  * checkstyle

    https://docs.gradle.org/current/userguide/checkstyle_plugin.html

    Performs code style checks on Java source files using Checkstyle and generates reports from these checks.
    The checks are included in gradle's *check* task (you can run them by executing `./gradlew check` command).

  * pmd

    https://docs.gradle.org/current/userguide/pmd_plugin.html

    Performs static code analysis to finds common programming flaws. Included in gradle `check` task.


  * jacoco

    https://docs.gradle.org/current/userguide/jacoco_plugin.html

    Provides code coverage metrics for Java code via integration with JaCoCo.
    You can create the report by running the following command:

    ```bash
      ./gradlew jacocoTestReport
    ```

    The report will be created in build/reports subdirectory in your project directory.

  * io.spring.dependency-management

    https://github.com/spring-gradle-plugins/dependency-management-plugin

    Provides Maven-like dependency management. Allows you to declare dependency management
    using `dependency 'groupId:artifactId:version'`
    or `dependency group:'group', name:'name', version:version'`.

  * org.springframework.boot

    http://projects.spring.io/spring-boot/

    Reduces the amount of work needed to create a Spring application

  * org.owasp.dependencycheck

    https://jeremylong.github.io/DependencyCheck/dependency-check-gradle/index.html

    Provides monitoring of the project's dependent libraries and creating a report
    of known vulnerable components that are included in the build. To run it
    execute `gradle dependencyCheck` command.

  * com.github.ben-manes.versions

    https://github.com/ben-manes/gradle-versions-plugin

    Provides a task to determine which dependencies have updates. Usage:

    ```bash
      ./gradlew dependencyUpdates -Drevision=release
    ```

## Setup

Located in `./bin/init.sh`. Simply run and follow the explanation how to execute it.

## Notes

Since Spring Boot 2.1 bean overriding is disabled. If you want to enable it you will need to set `spring.main.allow-bean-definition-overriding` to `true`.

JUnit 5 is now enabled by default in the project. Please refrain from using JUnit4
(except for libraries not able to support Junit5 like Serenity) 
and use the next generation

## Building and deploying the application

### Building the application (for purpose of running the test)

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```bash
  ./gradlew build
```

### Running the Functional Tests

Change Directory to the root folder of the Project:

Run the following command

```bash
  gradle clean functional -DTEST_URL='{url}' -DTEST_HOST='{hostname}' -DTEST_SUBSCRIPTION_KEY={subscriptonKey}
```

### Running the Smoke Tests

Change Directory to the root folder of the Project:

Run the following command

```bash
  gradle clean smoke -DTEST_URL='{url}' -DTEST_HOST='{hostname}' -DTEST_SUBSCRIPTION_KEY={subscriptonKey}
```

### Running the Integration Tests

Change Directory to the root folder of the Project:

Run the following command

```bash
  gradle clean integration -DTEST_URL='{url}' -DTEST_HOST='{hostname}' -DTEST_SUBSCRIPTION_KEY={subscriptonKey}
```

### Running the Contract Tests

Change Directory to the root folder of the Project:

Start the broker first running the pact docker-compose: docker-compose -f docker-pactbroker-compose.yml up
You can run contract or pact tests as follows:

Run the following command

```bash
  gradle clean contract -DTEST_HOST='{hostname}' -DTEST_SUBSCRIPTION_KEY={subscriptonKey}
```

PUBLISHING YOUR PACT    
You can publish your pact tests locally by using it to publish your tests:

```bash
./gradlew pactPublish
```

VERIFY YOUR PACT

Make sure that your end point is up and running...

The following command should perform all the verifications tests

```
./gradlew clean pactVerify -Ppact.verifier.publishResults=true
```

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

