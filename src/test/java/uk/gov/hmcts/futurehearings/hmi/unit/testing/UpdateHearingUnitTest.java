package uk.gov.hmcts.futurehearings.hmi.unit.testing;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
public class UpdateHearingUnitTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetHost}")
    private String targetHost;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${hearingApiRootContext}")
    private String hearingApiRootContext;

    private final Map<String, Object> headersAsMap = new HashMap<>();

    @BeforeEach
    public void initialiseValues() {
        headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Source", "SnL");
        headersAsMap.put("Destination", "CFT");
        headersAsMap.put("DateTime", "datetimestring");
        headersAsMap.put("RequestType", "TypeOfCase");
    }

    @Test
    public void testUpdateHearingRequestWithCorrectRequest() throws IOException {

    }

    private Response requestHearingWithProperRequest(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(201)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestHearingWithMissingHeaderOcpSubscriptionKey(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(401)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestHearingWithMissingHeaderSource(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(401)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestHearingWithMissingHeaderDestination(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(401)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestHearingWithMissingHeaderDateTime(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(401)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

    private Response requestHearingWithMissingHeaderRequestType(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(401)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }

}
