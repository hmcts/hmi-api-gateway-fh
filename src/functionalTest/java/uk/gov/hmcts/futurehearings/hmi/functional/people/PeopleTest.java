package uk.gov.hmcts.futurehearings.hmi.functional.people;

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
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;
import uk.gov.hmcts.futurehearings.hmi.functional.people.steps.PeopleSteps;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text = {"In order to test that the People Functionality is working properly",
        "As a tester",
        "I want to be able to execute the tests for People API"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"java:S2699", "PMD.UseDiamondOperator"})
public class PeopleTest extends FunctionalTest {

    @Value("${peopleRootContext}")
    protected String peopleRootContext;

    @Value("${people_idRootContext}")
    protected String peopleIdRootContext;

    @Steps
    PeopleSteps peopleSteps;

    @Before
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    public void testPeopleLookUp() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();
        queryParameters.put("updated_since", "2019-01-29");
        queryParameters.put("per_page", "52");
        queryParameters.put("page", "1");

        headersAsMap = createStandardHmiHeader("ELINKS");
        peopleSteps.shouldFetchListOfPeople(peopleRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters);
    }

    @Test
    public void testPersonLookUp() {
        peopleIdRootContext = String.format(peopleIdRootContext, new Random().nextInt(99999999));
        headersAsMap = createStandardHmiHeader("ELINKS");
        peopleSteps.shouldGetByPeopleId(peopleIdRootContext,
                headersAsMap,
                authorizationToken);
    }
}
