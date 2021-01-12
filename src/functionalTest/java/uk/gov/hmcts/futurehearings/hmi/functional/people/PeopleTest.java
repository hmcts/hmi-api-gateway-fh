package uk.gov.hmcts.futurehearings.hmi.functional.people;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHMIHeader;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.people.steps.PeopleSteps;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the People Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for People API"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings("java:S2699")
public class PeopleTest extends FunctionalTest {

    @Value("${peopleRootContext}")
    protected String peopleRootContext;

    @Value("${people_idRootContext}")
    protected String people_idRootContext;

    @Steps
    PeopleSteps peopleSteps;

    @Before
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }


    @Test
    public void testPeopleLookUp() throws Exception {

        log.debug("In the testSuccessfulGetPeople() method");

        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("updated_since", "2020-10-12");
        queryParameters.put("per_page", "50");
        queryParameters.put("page", "1");

        headersAsMap = createStandardHMIHeader("MOCK");
        String peopleId = peopleSteps.getPeopleIdForELinks(peopleRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
        log.debug("The value of the peopleId : "+peopleId);

        people_idRootContext = String.format(people_idRootContext, peopleId);
        peopleSteps.performGetByPeopleId(people_idRootContext, peopleId,
                headersAsMap,
                authorizationToken,
                null);
    }
}
