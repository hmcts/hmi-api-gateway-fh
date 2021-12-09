package uk.gov.hmcts.futurehearings.hmi.functional.common.rest;

import static net.serenitybdd.rest.SerenityRest.expect;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class RestClientTemplate {

    public static Response callRestEndpointWithPayload(final String apiURL,
                                                       final Map<String, Object> headersAsMap,
                                                       final String authorizationToken,
                                                       final String payloadBody,
                                                       final HttpMethod httpMethod,
                                                       final HttpStatus httpStatus) {

        Response response;
        switch (httpMethod) {
            case POST:
                response = expect().that().statusCode(httpStatus.value())
                        .given().body(payloadBody)
                        .headers(headersAsMap)
                        .auth().oauth2(authorizationToken)
                        .basePath(apiURL)
                        .when().post().then().extract().response();
                break;
            case PUT:
                response = expect().that().statusCode(httpStatus.value())
                        .given().body(payloadBody)
                        .headers(headersAsMap)
                        .auth().oauth2(authorizationToken)
                        .basePath(apiURL)
                        .when().put().then().extract().response();
                break;
            case DELETE:
                response = expect().that().statusCode(httpStatus.value())
                        .given().body(payloadBody)
                        .headers(headersAsMap)
                        .auth().oauth2(authorizationToken)
                        .basePath(apiURL)
                        .when().delete().then().extract().response();
                break;
            case GET :
                response = expect().that().statusCode(httpStatus.value())
                        .given()
                        .headers(headersAsMap)
                        .auth().oauth2(authorizationToken)
                        .basePath(apiURL)
                        .when().get().then().extract().response();
                break;
            default:
                throw new UnsupportedOperationException("This REST method is not Supported....");
        }
        return response;
    }

    public static Response callRestEndpointWithQueryParams(final String apiURL,
                                                           final Map<String, Object> headersAsMap,
                                                           final String authorizationToken,
                                                           final Map<String, String> queryParams,
                                                           final HttpStatus httpStatus) {

        return expect().that().statusCode(httpStatus.value())
                .given()
                .queryParams(queryParams)
                .headers(headersAsMap)
                .auth().oauth2(authorizationToken)
                .basePath(apiURL)
                .when().get().then().extract().response();
    }

    public static Response callRestEndpointDelete(final String apiURL,
                                            final Map<String, Object> headersAsMap,
                                            final String authorizationToken,
                                            final HttpStatus httpStatus) {
        return expect().that().statusCode(httpStatus.value())
                .given()
                .headers(headersAsMap)
                .auth().oauth2(authorizationToken)
                .basePath(apiURL)
                .when().delete().then().extract().response();
    }
}
