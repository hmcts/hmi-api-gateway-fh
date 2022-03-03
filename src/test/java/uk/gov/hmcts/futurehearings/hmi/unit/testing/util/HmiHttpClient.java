package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

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
            final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().get().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().get().then().extract().response();
        }
    }

    public Response httpDelete(final String basePath, final Map<String, Object> headersAsMap,
            final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().delete().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().delete().then().extract().response();
        }
    }

    public Response httpPost(final String basePath, final Map<String, Object> headersAsMap,
            final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        }
    }

    public Response httpPost(final String basePath, final Map<String, Object> headersAsMap,
                             final Map<String, String> paramsAsMap, InputStream payloadBody) {
        try {
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        }
    }

    public Response httpPostMultiPart(final String basePath, final Map<String, Object> headersAsMap,
                             final Map<String, String> paramsAsMap, File payloadBody) {
        try {
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .multiPart("file", payloadBody, "application/pdf").basePath(basePath).when().post().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        }
    }

    public Response httpPut(final String basePath, final Map<String, Object> headersAsMap,
                             final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().put().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().auth().oauth2(accessToken).queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        }
    }

    public Response httpGetNoAuth(final String basePath, final Map<String, Object> headersAsMap,
            final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap).basePath(basePath)
                    .body(payloadBody).when().get().then().extract().response();
        } catch (RuntimeException exc) {
            log.error("Exception occured. Trying again...", exc);
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap).basePath(basePath)
                    .body(payloadBody).when().get().then().extract().response();
        }
    }

    public Response httpDeleteNoAuth(final String basePath, final Map<String, Object> headersAsMap,
            final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap).basePath(basePath)
                    .body(payloadBody).when().delete().then().extract().response();
        } catch (RuntimeException exc) {
            log.error("Exception occured. Trying again...", exc);
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap).basePath(basePath)
                    .body(payloadBody).when().delete().then().extract().response();
        }
    }

    public Response httpPostNoAuth(final String basePath, final Map<String, Object> headersAsMap,
            final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        }
    }

    public Response httpPutNoAuth(final String basePath, final Map<String, Object> headersAsMap,
                                   final Map<String, String> paramsAsMap, String payloadBody) {
        try {
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().put().then().extract().response();
        } catch (Exception exc) {
            log.error("SSL Exception occured. Trying again...", exc);
            return RestAssured.given().queryParams(paramsAsMap).headers(headersAsMap)
                    .body(payloadBody).basePath(basePath).when().post().then().extract().response();
        }
    }
}