package uk.gov.hmcts.futurehearings.hmi.smoke.people;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.smoke.common.test.SmokeTest;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("smoke")
@DisplayName("Smoke Test for the HMI People Context")
@SuppressWarnings("java:S2187")
class PeopleApiSmokeTest extends SmokeTest {

    @Value("${peopleApiRootContext}")
    private String peopleApiRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("updated_since", "2020-11-01");
        queryParams.put("per_page", "50");
        queryParams.put("page", "1");

        this.setParams(queryParams);
        setRootContext(peopleApiRootContext);
    }
}