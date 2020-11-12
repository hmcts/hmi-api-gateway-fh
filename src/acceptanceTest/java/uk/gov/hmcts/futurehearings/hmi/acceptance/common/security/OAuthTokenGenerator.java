package uk.gov.hmcts.futurehearings.hmi.acceptance.common.security;

import static io.restassured.RestAssured.expect;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class OAuthTokenGenerator {

    public static final String generateOAuthToken(final String token_apiURL,
                                                  final String token_apiTenantId,
                                                  final String grantType,
                                                  final String clientID,
                                                  final String clientSecret,
                                                  final String scope,
                                                  final HttpStatus httpStatus) throws Exception {

        String full_token_apiURL = String.format(token_apiURL, token_apiTenantId);
        final String bodyForToken = String.format("grant_type=%s&client_id=%s&client_secret=%s&scope=%s",
                grantType, clientID, clientSecret, scope);

        Response response = callTokenGeneratorEndpoint(bodyForToken, httpStatus, full_token_apiURL);
        assertEquals(httpStatus.value(), response.getStatusCode());
        return response.jsonPath().getString("access_token");
    }

    public static final Response callTokenGeneratorEndpoint(final String bodyForToken,
                                                            final HttpStatus badRequest,
                                                            final String full_token_apiURL) {

        log.debug("The value of the Body : " + bodyForToken);
        log.debug("The value of the Target URL : " + full_token_apiURL);

        Response response = expect().that().statusCode(badRequest.value())
                .given()
                .body(bodyForToken)
                .contentType(ContentType.URLENC)
                .baseUri(full_token_apiURL)
                .when()
                .post()
                .then()
                .extract()
                .response();
        log.debug(response.prettyPrint());
        return response;
    }
}
