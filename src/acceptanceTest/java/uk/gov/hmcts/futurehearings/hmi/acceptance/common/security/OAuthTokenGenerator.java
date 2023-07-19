package uk.gov.hmcts.futurehearings.hmi.acceptance.common.security;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.expect;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SuppressWarnings({"PMD.UseObjectForClearerAPI"})
public final class OAuthTokenGenerator {

    public static String generateOAuthToken(final String tokenApiUrl,
                                                  final String tokenApiTenantId,
                                                  final String grantType,
                                                  final String clientID,
                                                  final String clientSecret,
                                                  final String scope,
                                                  final HttpStatus httpStatus) throws Exception { //NOSONAR

        String fullTokenApiUrl = String.format(tokenApiUrl, tokenApiTenantId);
        final String bodyForToken = String.format("grant_type=%s&client_id=%s&client_secret=%s&scope=%s",
                grantType, clientID, clientSecret, scope);

        Response response = callTokenGeneratorEndpoint(bodyForToken, httpStatus, fullTokenApiUrl);
        assertEquals(httpStatus.value(), response.getStatusCode(),
                "Status code do not match");
        return response.jsonPath().getString("access_token");
    }

    public static Response callTokenGeneratorEndpoint(final String bodyForToken,
                                                            final HttpStatus badRequest,
                                                            final String fullTokenApiUrl) {
        return expect().that().statusCode(badRequest.value())
                .given()
                .body(bodyForToken)
                .contentType(ContentType.URLENC)
                .baseUri(fullTokenApiUrl)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    private OAuthTokenGenerator() {
    }
}
