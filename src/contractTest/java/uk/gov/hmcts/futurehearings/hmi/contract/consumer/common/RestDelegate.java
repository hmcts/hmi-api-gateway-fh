package uk.gov.hmcts.futurehearings.hmi.contract.consumer.common;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;

import java.io.IOException;
import java.util.Map;

import au.com.dius.pact.consumer.MockServer;
import io.restassured.RestAssured;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RestDelegate {
    private RestDelegate () {}

    public static final void invokeSnLAPI (final Map<String, String> headersAsMap,
                                           final String requestPayloadPath,
                                           final HttpMethod httpMethod,
                                           final MockServer mockServer,
                                           final String apiURIPath,
                                           final HttpStatus httpStatus) throws IOException {

        switch (httpMethod) {
            case POST:
                RestAssured
                        .given()
                        .headers(headersAsMap)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(readFileContents(requestPayloadPath))
                        .when()
                        .post(mockServer.getUrl() + apiURIPath)
                        .then()
                        .statusCode(httpStatus.value());
                break;
            case PUT:
                RestAssured
                        .given()
                        .headers(headersAsMap)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(readFileContents(requestPayloadPath))
                        .when()
                        .put(mockServer.getUrl() + apiURIPath)
                        .then()
                        .statusCode(httpStatus.value());
                break;
            default:
                break;
        }

    }
}
