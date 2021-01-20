package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import java.util.Map;

import javax.net.ssl.SSLException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HmiHttpClient {
    private String accessToken;

    public HmiHttpClient(String accessToken, String baseUri) {
        this.accessToken = accessToken;
        RestAssured.baseURI = baseUri;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Response httpGet(final String basePath, final Map<String, Object> headersAsMap,
            final Map<String, String> paramsAsMap) {
        try {
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .basePath(basePath).when().get().then().extract().response();
        } catch (Exception exc) {
                log.error("SSL Exception occured. Trying again...", exc);
                return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                        .basePath(basePath).when().get().then().extract().response();
        }
    }

    public Response httpGetNoAuth(final String basePath, final Map<String, Object> headersAsMap,
            final Map<String, String> paramsAsMap) {
        try {
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap).basePath(basePath).when().get()
                    .then().extract().response();
        } catch (RuntimeException exc) {
            log.error("Exception occured. Trying again...", exc);
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap).basePath(basePath).when().get()
                    .then().extract().response();
        }
    }
}