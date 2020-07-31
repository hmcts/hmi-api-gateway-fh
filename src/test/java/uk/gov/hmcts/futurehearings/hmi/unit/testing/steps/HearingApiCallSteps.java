package uk.gov.hmcts.futurehearings.hmi.unit.testing.steps;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.expect;

public class HearingApiCallSteps {
    private String actor;

    @Step("#actor routes to {0} in order to get invoke {1}")
    public Response requestHearingWithMissingCaseTitle(final String api, final Map<String, Object> headersAsMap,
                                                       final String basePath, final String payloadBody) {
        return expect().that().statusCode(400)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
    }
}
