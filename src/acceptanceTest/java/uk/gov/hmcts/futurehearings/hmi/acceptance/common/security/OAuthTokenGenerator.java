package uk.gov.hmcts.futurehearings.hmi.acceptance.common.security;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.expect;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class OAuthTokenGenerator {

    public static final String generateOAuthToken(final String tokenApiUrl,
                                                  final String tokenApiTenantId,
                                                  final String grantType,
                                                  final String clientID,
                                                  final String clientSecret,
                                                  final String scope,
                                                  final HttpStatus httpStatus) throws Exception {

        String fullTokenApiUrl = String.format(tokenApiUrl, tokenApiTenantId);
        final String bodyForToken = String.format("grant_type=%s&client_id=%s&client_secret=%s&scope=%s",
                grantType, clientID, clientSecret, scope);

        Response response = callTokenGeneratorEndpoint(bodyForToken, httpStatus, fullTokenApiUrl);
        assertEquals(httpStatus.value(), response.getStatusCode());
        return response.jsonPath().getString("access_token");
    }

    public static final Response callTokenGeneratorEndpoint(final String bodyForToken,
                                                            final HttpStatus badRequest,
                                                            final String full_token_apiURL) {
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
        return response;
    }
}
