package uk.gov.hmcts.futurehearings.hmi.acceptance.common;


import java.util.Map;
import java.util.Objects;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
public class RestClientTemplate {

    public static Response shouldExecute(final Headers headers,
                                         final String authorizationToken,
                                         final String requestBodyPayload,
                                         final String requestURL,
                                         final Map<String, String> params,
                                         final HttpStatus expectedHttpStatus,
                                         final HttpMethod httpMethod) {

        log.debug("The value of the baseURI : " + RestAssured.baseURI);
        log.debug("The value of the path : " + requestURL);
        if (Objects.nonNull(headers)) {
            log.debug("The value of the header : " + headers.size());
            headers.asList().forEach(header ->
                    log.debug("The Key of the header" + header.getName() + "The value of the Header" + header.getValue()));
        }

        log.debug("The value of the Authorization Token : " + authorizationToken);
        log.debug("The value of the header : " + headers.size());
        headers.asList().forEach(header ->
                log.debug("The Key of the header" + header.getName() + "The value of the Header" + header.getValue()));
        log.debug("The value of the HTTP Status : " + expectedHttpStatus.value());

        switch (httpMethod) {
            case POST:
                if (Objects.nonNull(authorizationToken)) {
                    return RestAssured
                            .expect().that().statusCode(expectedHttpStatus.value())
                            .given()
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestURL)
                            .body(requestBodyPayload)
                            .when()
                            .post().then().extract().response();
                } else {
                    return RestAssured
                            .expect().that().statusCode(expectedHttpStatus.value())
                            .given()
                            .headers(headers)
                            .basePath(requestURL)
                            .body(requestBodyPayload)
                            .when()
                            .post().then().extract().response();
                }
            case PUT:
                return RestAssured
                        .expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestURL)
                        .body(requestBodyPayload)
                        .when()
                        .put().then().extract().response();
            case DELETE:
                return RestAssured
                        .expect().that().statusCode(expectedHttpStatus.value())
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
                    return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                            .given()
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestURL)
                            .when()
                            .get().then().extract().response();
                } else {
                    log.debug("Query Params " + params);
                    Response response = null;
                    response = RestAssured.expect().that().statusCode(expectedHttpStatus.value())
                            .given()
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
                return RestAssured.expect().that().statusCode(expectedHttpStatus.value())
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
