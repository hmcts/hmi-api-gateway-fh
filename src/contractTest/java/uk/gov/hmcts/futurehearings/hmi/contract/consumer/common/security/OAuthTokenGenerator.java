package uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.security;

import static io.restassured.RestAssured.expect;

import java.util.Map;
import java.util.Objects;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class OAuthTokenGenerator {

    public static final String generateOAuthTokenForSNL(final String token_apiURL,
                                                  final String userName,
                                                  final String password,
                                                  final HttpStatus httpStatus) throws Exception {

        String full_token_apiURL = token_apiURL;
        final String bodyForToken = String.format("username=%s&password=%s",
                userName, password);
        Response response = callTokenGeneratorEndpoint(null, bodyForToken, httpStatus, full_token_apiURL);
        return response.jsonPath().getString("id_token");
    }

    public static final String generateOAuthTokenForVH(final String token_apiURL,
                                                       final String token_apiTenantId,
                                                       final String grantType,
                                                       final String clientID,
                                                       final String clientSecret,
                                                       final String resource,
                                                       final HttpStatus httpStatus) throws Exception {

        String full_token_apiURL = String.format(token_apiURL, token_apiTenantId);
        final String bodyForToken = String.format("grant_type=%s&client_id=%s&client_secret=%s&resource=%s",
                grantType, clientID, clientSecret, resource);
        Map<String, String> headerMap = Map.of(
                "Content-Type", "application/x-www-form-urlencoded",
                "Cookie", "fpc=ArhVSY4Kf6BNr37UFaG4mvVPaRs5AgAAADampNcOAAAA");

        Response response = callTokenGeneratorEndpoint(headerMap, bodyForToken, httpStatus, full_token_apiURL);
        return response.jsonPath().getString("access_token");
    }

    public static final Response callTokenGeneratorEndpoint(final Map<String, String> headerMap, final String bodyForToken,
                                                            final HttpStatus badRequest,
                                                            final String full_token_apiURL) {

        log.debug("The value of the Body : " + bodyForToken);
        log.debug("The value of the Target URL : " + full_token_apiURL);
        Response response = null;
        if (Objects.isNull(headerMap) || headerMap.isEmpty()) {
            response = expect().that().statusCode(badRequest.value())
                    .given()
                    .body(bodyForToken)
                    .contentType(ContentType.URLENC)
                    .baseUri(full_token_apiURL)
                    .when()
                    .post()
                    .then()
                    .extract()
                    .response();
        } else {
            response = expect().that().statusCode(badRequest.value())
                    .given()
                    .headers(headerMap)
                    .body(bodyForToken)
                    .contentType(ContentType.URLENC)
                    .baseUri(full_token_apiURL)
                    .when()
                    .post()
                    .then()
                    .extract()
                    .response();
        }
        log.debug(response.prettyPrint());
        return response;
    }
}
