package uk.gov.hmcts.futurehearings.hmi.acceptance.people;

import uk.gov.hmcts.futurehearings.hmi.Application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
class PUTPeopleValidationTest extends PeopleValidationTest {

    @Value("${people_idRootContext}")
    private String people_idRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        people_idRootContext = String.format(people_idRootContext,"12345");
        this.setRelativeURL(people_idRootContext);
        this.setInputPayloadFileName("PUT-people-payload.json");
        this.setHttpMethod(HttpMethod.PUT);
        this.setHttpSuccessStatus(HttpStatus.NO_CONTENT);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"ELINKS"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }
}
