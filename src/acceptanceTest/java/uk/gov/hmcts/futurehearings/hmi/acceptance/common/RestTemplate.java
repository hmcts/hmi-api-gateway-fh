package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import java.io.IOException;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RestTemplate {

    public static Response shouldExecutePost(final Map<String,String> headersAsMap,
                                             final String requestBodyPayload,
                                             final String requestURL,
                                             final HttpStatus expectedHttpStatus) {

        return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBodyPayload)
                .when()
                .post(requestURL).then().extract().response();
    }
}
