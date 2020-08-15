package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import java.io.IOException;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
@Slf4j
public class RestTemplate {

    public static Response shouldExecute(final Map<String,String> headersAsMap,
                                             final String requestBodyPayload,
                                             final String requestURL,
                                             final HttpStatus expectedHttpStatus,
                                             final HttpMethod httpMethod) {

        log.info("The value of the baseURI : " + RestAssured.baseURI);
        log.info("The value of the path : " + requestURL);
        log.info("The value of the header : " + headersAsMap);
        log.info("The value of the header : " + expectedHttpStatus.value());

       switch (httpMethod) {
           case POST:
               return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                       .given()
                       .headers(headersAsMap)
                       .contentType(headersAsMap.get("Content-Type"))
                       .basePath(requestURL)
                       .body(requestBodyPayload)
                       .when()
                       .post().then().extract().response();
           case PUT:
               return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                       .given()
                       .headers(headersAsMap)
                       .contentType(headersAsMap.get("Content-Type"))
                       .basePath(requestURL)
                       .body(requestBodyPayload)
                       .when()
                       .put().then().extract().response();

           default :
               throw new IllegalArgumentException("HTTP method not identified");

       }
    }
}
