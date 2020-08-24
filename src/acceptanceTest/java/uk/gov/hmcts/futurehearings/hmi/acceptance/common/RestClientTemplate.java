package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
@Slf4j
public class RestClientTemplate {

    public static Response shouldExecute(final Map<String,String> headersAsMap,
                                             final String requestBodyPayload,
                                             final String requestURL,
                                             final Map<String, String> params,
                                             final HttpStatus expectedHttpStatus,
                                             final HttpMethod httpMethod) {

        log.info("The value of the baseURI : "  + RestAssured.baseURI);
        log.info("The value of the path : "     + requestURL);
        log.info("The value of the header : "   + headersAsMap);
        log.info("The value of the HTTP Status : " + expectedHttpStatus.value());

       switch (httpMethod) {
           case POST:
               return RestAssured
                       .expect().that().statusCode(expectedHttpStatus.value())
                       .given()
                       .headers(headersAsMap)
                       .basePath(requestURL)
                       .body(requestBodyPayload)
                       .when()
                       .post().then().extract().response();
           case PUT:
               return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                       .given()
                       .headers(headersAsMap)
                       .basePath(requestURL)
                       .body(requestBodyPayload)
                       .when()
                       .put().then().extract().response();
           case GET:
               return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                       .given()
                       .headers(headersAsMap)
                       .basePath(requestURL)
                       .params(params)
                       //.body(requestBodyPayload)
                       .when()
                       .get().then().extract().response();
           default :
               throw new IllegalArgumentException("HTTP method not identified");

       }
    }
}
