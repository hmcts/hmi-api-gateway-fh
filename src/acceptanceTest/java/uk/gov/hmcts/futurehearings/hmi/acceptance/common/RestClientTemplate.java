package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import java.util.Map;
import java.util.Objects;

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

        log.debug("The value of the baseURI : "  + RestAssured.baseURI);
        log.debug("The value of the path : "     + requestURL);
        log.debug("The value of the header : "   + headersAsMap);
        log.debug("The value of the HTTP Status : " + expectedHttpStatus.value());


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
           case DELETE:
               return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                       .given()
                       .headers(headersAsMap)
                       .basePath(requestURL)
                       .body(requestBodyPayload)
                       .when()
                       .delete().then().extract().response();
           case GET:
               if (Objects.isNull(params) || params.size() == 0) {
                   return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                           .given()
                           .headers(headersAsMap)
                           .basePath(requestURL)
                           .when()
                           .get().then().extract().response();
               } else {
                   log.debug("Query Params " + params);
                   Response response = RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                           .given()
                           .queryParams(params)
                           .headers(headersAsMap)
                           .basePath(requestURL)
                           .when()
                           .get().then().extract().response();

                   log.debug(response.getBody().asString());
                   return response;
               }
           case OPTIONS:
               return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                       .given()
                       .headers(headersAsMap)
                       .basePath(requestURL)
                       .when()
                       .options().then().extract().response();
           default :
               log.error("Http method not identified :" + httpMethod.name());
               throw new IllegalArgumentException("HTTP method not identified");

       }
    }
}
