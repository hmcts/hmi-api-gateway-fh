package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.expect;

@SuppressWarnings({"PMD.UseObjectForClearerAPI"})
public final class TestUtilities {

    public static String readFileContents(final String path) throws IOException {
        final File file = ResourceUtils.getFile("classpath:" + path);
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    public static String getToken(String grantType, String clientID,
                                  String clientSecret, String tokenUrl, String scope) {
        final String body = String.format(
                "grant_type=%s&client_id=%s&client_secret=%s&scope=%s", grantType,
                clientID, clientSecret, scope);
        Response response = expect().that().statusCode(200)
                .given()
                .body(body)
                .contentType(ContentType.URLENC)
                .baseUri(tokenUrl)
                .when()
                .post()
                .then()
                .extract()
                .response();

        return response.jsonPath().getString("access_token");

    }

    private TestUtilities() {
    }

}
