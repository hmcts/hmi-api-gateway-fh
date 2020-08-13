package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import java.io.IOException;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
@Slf4j
public class RestTemplate {

    public static Response shouldExecutePost(final Map<String,String> headersAsMap,
                                             final String requestBodyPayload,
                                             final String requestURL,
                                             final HttpStatus expectedHttpStatus) {

       log.debug("The value of the baseURI"+RestAssured.baseURI);

        return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                .given()
                .headers(headersAsMap)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .basePath(requestURL)
                .body(requestBodyPayload)
                .when()
                .post().then().extract().response();
    }
}
