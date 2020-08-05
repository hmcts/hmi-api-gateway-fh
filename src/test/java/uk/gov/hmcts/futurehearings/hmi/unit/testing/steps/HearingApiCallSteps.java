package uk.gov.hmcts.futurehearings.hmi.unit.testing.steps;

import static io.restassured.RestAssured.expect;

import io.restassured.response.Response;

import java.util.Map;


public class HearingApiCallSteps {
    private String actor;

    public Response requestHearingWithMissingField(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {
        return expect().that().statusCode(400)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }
}
