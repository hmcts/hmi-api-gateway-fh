package uk.gov.hmcts.futurehearings.hmi.contract.consumer.common;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;

import java.io.IOException;
import java.util.Map;

import au.com.dius.pact.consumer.MockServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RestDelegate {
    private RestDelegate () {}

    public static final Response invokeAPI(final Map<String, String> headersAsMap,
                                           final String authorizationToken,
                                           final String requestPayloadPath,
                                           final HttpMethod httpMethod,
                                           final MockServer mockServer,
                                           final String apiURIPath,
                                           final HttpStatus httpStatus) throws IOException {

        switch (httpMethod) {
            case POST:
                return RestAssured
                        .given()
                        .headers(headersAsMap)
                        //.auth().oauth2(authorizationToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(readFileContents(requestPayloadPath))
                        .when()
                        .post(mockServer.getUrl() + apiURIPath)
                        .then()
                        .statusCode(httpStatus.value())
                        .extract().response();
            case PUT:
                return RestAssured
                        .given()
                        .headers(headersAsMap)
                        //.auth().oauth2(authorizationToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(readFileContents(requestPayloadPath))
                        .when()
                        .put(mockServer.getUrl() + apiURIPath)
                        .then()
                        .statusCode(httpStatus.value())
                        .extract().response();
            case GET:
                return RestAssured
                        .given()
                        .headers(headersAsMap)
                        //.auth().oauth2(authorizationToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .get(mockServer.getUrl() + apiURIPath)
                        .then()
                        .statusCode(httpStatus.value())
                        .extract().response();
            default:
                return null;

        }
    }
}
