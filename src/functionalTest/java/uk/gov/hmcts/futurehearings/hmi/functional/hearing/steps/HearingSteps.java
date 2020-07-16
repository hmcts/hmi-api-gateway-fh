package uk.gov.hmcts.futurehearings.hmi.functional.hearing.steps;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static uk.gov.hmcts.futurehearings.hmi.functional.hearing.steps.verify.HearingResponseVerification.verifySessionResponse;
import static uk.gov.hmcts.futurehearings.hmi.functional.hearing.steps.verify.HearingResponseVerification.verifyHearingResponse;
import static net.serenitybdd.rest.SerenityRest.expect;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

public class HearingSteps {

    private String actor;

    @Step("#actor routes to {0} in order to get invoke {1}")
    public void invokeEmployee (final String basePath,
                                final String api,
                                final Map<String,Object> headersAsMap) {

        System.out.println("The value of the base path " + basePath);
        Response response = expect().that().statusCode(200)
                .given().contentType("application/json")
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
        verifySessionResponse(lastResponse());
    }

    @Step("#actor routes to {0} in order to get invoke {1}")
    public void requestHearing (final String api,
                                final Map<String,Object> headersAsMap,
                                final String payloadBody) {

        //System.out.println("The value of the base path " + basePath);
        Response response = expect().that().statusCode(200)
                .given().contentType("application/json").body(payloadBody)
                .headers(headersAsMap)
                //.baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
        verifyHearingResponse(lastResponse());
    }
}
