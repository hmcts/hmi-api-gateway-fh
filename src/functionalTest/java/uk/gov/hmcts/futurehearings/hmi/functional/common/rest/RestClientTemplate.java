package uk.gov.hmcts.futurehearings.hmi.functional.common.rest;

import static net.serenitybdd.rest.SerenityRest.expect;

import java.util.Map;

import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class RestClientTemplate {

    public Response callRestEndpointWithPayload(final String apiURL,
                                                final Map<String, Object> headersAsMap,
                                                final String authorizationToken,
                                                final String payloadBody,
                                                final HttpMethod httpMethod,
                                                final HttpStatus httpStatus) {

        Response response = null;
        switch (httpMethod) {
            case POST:
                response = expect().that().statusCode(httpStatus.value())
                        .given().body(payloadBody)
                        .headers(headersAsMap)
                        .auth().oauth2(authorizationToken)
                        .basePath(apiURL)
                        .when().post().then().extract().response();
            case PUT:
                response = expect().that().statusCode(httpStatus.value())
                        .given().body(payloadBody)
                        .headers(headersAsMap)
                        .auth().oauth2(authorizationToken)
                        .basePath(apiURL)
                        .when().put().then().extract().response();
            case DELETE:
                response = expect().that().statusCode(httpStatus.value())
                        .given().body(payloadBody)
                        .headers(headersAsMap)
                        .auth().oauth2(authorizationToken)
                        .basePath(apiURL)
                        .when().put().then().extract().response();
        }
        return response;
    }

    public Response callRestEndpointWithQueryParams(final String apiURL,
                                                    final Map<String, Object> headersAsMap,
                                                    final String authorizationToken,
                                                    final Map<String, String> queryParams) {

        return expect().that().statusCode(200)
                .given()
                .queryParams(queryParams)
                .headers(headersAsMap)
                .auth().oauth2(authorizationToken)
                .basePath(apiURL)
                .when().get().then().extract().response();
    }
}
