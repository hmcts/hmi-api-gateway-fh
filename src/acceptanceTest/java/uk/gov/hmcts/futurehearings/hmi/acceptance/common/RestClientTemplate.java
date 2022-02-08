package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import java.util.Map;
import java.util.Objects;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;

@Slf4j
public class RestClientTemplate {

    public static Response shouldExecute(final Headers headers,
                                         final String authorizationToken,
                                         final String requestBodyPayload,
                                         final String requestURL,
                                         final Map<String, String> params,
                                         final HttpStatus expectedHttpStatus,
                                         final HttpMethod httpMethod) {

        switch (httpMethod) {
            case POST: {
                log.info(baseURI);
                log.info(requestURL);

                return expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestURL)
                        .body(requestBodyPayload)
                        .when()
                        .post().then().extract().response();
            }
            case PUT:
                return expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestURL)
                        .body(requestBodyPayload)
                        .when()
                        .put().then().extract().response();
            case DELETE:
                if(requestBodyPayload == null) {
                    return expect().that().statusCode(expectedHttpStatus.value())
                            .given()
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestURL)
                            .when()
                            .delete().then().extract().response();
                }
                return expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestURL)
                        .body(requestBodyPayload)
                        .when()
                        .delete().then().extract().response();
            case GET:
                if (Objects.isNull(params) || params.size() == 0) {
                    return //.expect().that().statusCode(expectedHttpStatus.value())
                            given()
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestURL)
                            .when()
                            .get().then().extract().response();
                } else {
                    log.debug("Query Params " + params);
                    Response response = null;
                    response = //.expect().that().statusCode(expectedHttpStatus.value())
                            given()
                            .queryParams(params)
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestURL)
                            .when()
                            .get().then().extract().response();
                    log.debug(response.getBody().asString());
                    return response;
                }
            case OPTIONS:
                return expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestURL)
                        .when()
                        .options().then().extract().response();
            default:
                log.error("Http method not identified :" + httpMethod.name());
                throw new IllegalArgumentException("HTTP method not identified");
        }
    }
}
