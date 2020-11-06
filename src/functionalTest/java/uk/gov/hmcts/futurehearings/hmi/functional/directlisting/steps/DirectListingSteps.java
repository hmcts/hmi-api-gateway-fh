package uk.gov.hmcts.futurehearings.hmi.functional.directlisting.steps;

import static net.serenitybdd.rest.SerenityRest.expect;

import java.util.Map;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

public class DirectListingSteps {

    private String actor;

    @Step("#actor routes to {0} in order to get invoke {1}")
    public void requestHearing (final String api,
                                final Map<String,Object> headersAsMap,
                                final String authorizationToken,
                                final String payloadBody) {

        //System.out.println("The value of the base path " + basePath);
        Response response = expect().that().statusCode(200)
                .given().body(payloadBody)
                .headers(headersAsMap)
                .auth().oauth2(authorizationToken)
                //.baseUri(basePath)
                .basePath(api)
                .when().post().then().extract().response();
        //verifyHearingResponse(lastResponse());
    }
}
