package uk.gov.hmcts.futurehearings.hmi.functional.people;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithPayload;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"java:S2699", "PMD.UseDiamondOperator"})
public class PeopleTest extends FunctionalTest {

    @Value("${peopleRootContext}")
    protected String peopleRootContext;

    @Value("${people_idRootContext}")
    protected String peopleIdRootContext;

    @BeforeAll
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
        Response response = callRestEndpointWithQueryParams(peopleRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void testPersonLookUp() {
        peopleIdRootContext = String.format(peopleIdRootContext, new Random().nextInt(99999999));
        headersAsMap = createStandardHmiHeader("ELINKS");
        Response response = callRestEndpointWithPayload(peopleIdRootContext,
                headersAsMap,
                authorizationToken,
                null, HttpMethod.GET, HttpStatus.NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
