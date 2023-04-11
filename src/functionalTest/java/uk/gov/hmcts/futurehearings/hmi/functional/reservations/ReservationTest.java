package uk.gov.hmcts.futurehearings.hmi.functional.reservations;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.functional.common.test.FunctionalTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory.HeaderFactory.createStandardHmiHeader;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.LawOfDemeter", "PMD.UseDiamondOperator"})
class ReservationTest extends FunctionalTest {

    @Value("${reservationsApiRootContext}")
    protected String reservationsApiRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
    }

    @Test
    void testReservationsLookUp() {
        Map<String, String> queryParameters = new ConcurrentHashMap<>();

        headersAsMap = createStandardHmiHeader("SNL");
        headersAsMap.put("Content-Type", "application/json; charset=utf-8");
        Response response = callRestEndpointWithQueryParams(reservationsApiRootContext,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode(),
                "Status code do not match");
    }

}
