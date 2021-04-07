package uk.gov.hmcts.futurehearings.hmi.smoke.common.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestClient {

    public static Response makeGetRequest(final Map<String, String> headersAsMap,
                                          final String authorizationToken,
                                          final String rootContext) {
        return given()
                .headers(headersAsMap)
                .auth().oauth2(authorizationToken)
                .basePath(rootContext)
                .when().get();
    }

    public static Response makeGetRequest(final Map<String, String> headersAsMap,
                                          final String authorizationToken,
                                          final Map<String, String> queryParams,
                                          final String rootContext) {
        return given()
                .queryParams(queryParams)
                .headers(headersAsMap)
                .auth().oauth2(authorizationToken)
                .basePath(rootContext)
                .when().get();
    }
}
