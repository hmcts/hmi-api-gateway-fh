package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Objects;

@Slf4j
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.CyclomaticComplexity"})
public final class RestClientTemplate {

    public static Response shouldExecute(final Headers headers,
                                         final String authorizationToken,
                                         final String requestBodyPayload,
                                         final String requestUrl,
                                         final Map<String, String> params,
                                         final HttpStatus expectedHttpStatus,
                                         final HttpMethod httpMethod) {

        switch (httpMethod) {
            case POST:
                return RestAssured
                        .expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestUrl)
                        .body(requestBodyPayload)
                        .when()
                        .post().then().extract().response();
            case PUT:
                return RestAssured
                        .expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestUrl)
                        .body(requestBodyPayload)
                        .when()
                        .put().then().extract().response();
            case DELETE:
                if (requestBodyPayload == null) {
                    return RestAssured
                            .expect().that().statusCode(expectedHttpStatus.value())
                            .given()
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestUrl)
                            .when()
                            .delete().then().extract().response();
                }
                return RestAssured
                        .expect().that().statusCode(expectedHttpStatus.value())
                        .given()
                        .headers(headers)
                        .auth()
                        .oauth2(authorizationToken)
                        .basePath(requestUrl)
                        .body(requestBodyPayload)
                        .when()
                        .delete().then().extract().response();
            case GET:
                if (Objects.isNull(params) || params.isEmpty()) {
                    return RestAssured
                            .expect().that().statusCode(expectedHttpStatus.value())
                            .given()
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestUrl)
                            .when()
                            .get().then().extract().response();
                } else {
                    log.debug("Query Params " + params);
                    Response response = RestAssured
                            .expect().that().statusCode(expectedHttpStatus.value())
                            .given()
                            .queryParams(params)
                            .headers(headers)
                            .auth()
                            .oauth2(authorizationToken)
                            .basePath(requestUrl)
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
                        .basePath(requestUrl)
                        .when()
                        .options().then().extract().response();
            default:
                log.error("Http method not identified :" + httpMethod.name());
                throw new IllegalArgumentException("HTTP method not identified");
        }
    }

    private RestClientTemplate() {
    }
}
