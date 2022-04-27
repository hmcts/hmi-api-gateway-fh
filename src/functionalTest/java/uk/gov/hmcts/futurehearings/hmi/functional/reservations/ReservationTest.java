package uk.gov.hmcts.futurehearings.hmi.functional.reservations;

import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Ignore;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.reservations.steps.ReservationSteps;
import uk.gov.hmcts.futurehearings.hmi.functional.videohearing.steps.VideoHearingSteps;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the Hearings Reservation Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for Reservation API"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class ReservationTest extends FunctionalTest {

    @Value("${reservationsApiRootContext}")
    protected String reservationsApiRootContext;

    @Steps
    ReservationSteps reservationSteps;

    @Test
    public void testReservationsLookUp() {
        Map<String, String> queryParameters = new HashMap<String, String>();

        headersAsMap = createStandardHMIHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        reservationSteps.shouldFetchListOfReservations(reservationsApiRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

}
