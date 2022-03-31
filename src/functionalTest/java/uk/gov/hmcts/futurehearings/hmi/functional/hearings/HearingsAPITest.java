package uk.gov.hmcts.futurehearings.hmi.functional.hearings;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.hearings.steps.HearingsSteps;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Hearing Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for Hearings API methods works in a lifecycle mode of execution"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class HearingsAPITest extends FunctionalTest {

    @Value("${hearingsApiRootContext}")
    protected String hearingsApiRootContext;

    @Value("${hearings_idRootContext}")
    protected String hearings_idRootContext;

    @Value("${hearings_ReservationsApiRootContext}")
    private String hearings_ReservationsApiRootContext;

    @Steps
    HearingsSteps hearingsSteps;

    @Test
    public void testRequestHearingWithEmptyPayload() {
        hearingsSteps.shouldRequestHearingWithInvalidPayload(hearingsApiRootContext,
                headersAsMap,
                authorizationToken, HttpMethod.POST,
                "{}");
    }

    @Test
    public void testAmendHearingWithEmptyPayload() {
        int randomId = new Random().nextInt(99999999);

        hearingsSteps.shouldRequestHearingWithInvalidPayload(String.format(hearings_idRootContext,randomId),
                headersAsMap,
                authorizationToken, HttpMethod.PUT,
                "{}");
    }

    @Test
    public void testDeleteHearingWithEmptyPayload() {
        int randomId = new Random().nextInt(99999999);

        hearingsSteps.shouldRequestHearingWithInvalidPayload(String.format(hearings_idRootContext,randomId),
                headersAsMap,
                authorizationToken,HttpMethod.DELETE,
                "{}");
    }

    @Test
    public void testGetReservationsWithNoParams(){
        Map<String, String> queryParameters = new HashMap<>();
        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.shouldGETReservations(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }

    @Test
    public void testGetReservationsWithStartDate(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestStartDate", "2020-12-29T10:30:00Z");
        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.shouldGETReservations(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }

    @Test
    public void testGetReservationsWithEndDate(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestEndDate", "2020-11-30T14:30:00Z");
        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.shouldGETReservations(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }

    @Test
    public void testGetReservationsWithDuration(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestDuration", "30");
                headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.shouldGETReservations(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }

    @Test
    public void testGetReservationsWithRequestLocationTypeAndLocationReferenceAndLocationID(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestLocationType", "Cluster");
        queryParameters.put("requestLocationReferenceType", "CASEHQ”");
        queryParameters.put("requestLocationId ", "KNT");
        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.shouldGETReservations(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }


    @Test
    public void testGetReservationsWithRequestLocationType(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestLocationType", "Cluster");
        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.GETReservationsInvalidParams(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }

    @Test
    public void testGetReservationsWithRequestLocationReferenceType(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestLocationReferenceType", "CASEHQ”");
        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.GETReservationsInvalidParams(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }

    @Test
    public void testGetReservationsWithRequestLocationID(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestLocationId ", "KNT");
        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        hearingsSteps.GETReservationsInvalidParams(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }

    @Test
    public void testGetReservationsWithComments(){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("requestComments", "test");
        headersAsMap.put("Destination-System", "SNL");
        hearingsSteps.shouldGETReservations(hearings_ReservationsApiRootContext, headersAsMap, authorizationToken, queryParameters);
    }


}
