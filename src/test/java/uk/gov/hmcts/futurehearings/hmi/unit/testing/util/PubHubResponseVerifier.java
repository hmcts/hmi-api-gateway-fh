package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

public final class PubHubResponseVerifier {

    public static void  thenValidateResponseForPost(Response response) {
        try {
            assertEquals(201, response.getStatusCode(), "Response Code Validation:");
            getObjStep().pass("Got the expected response code: 201");
        } catch (AssertionError e) {
            getObjStep().fail("Exception in " + e.getMessage());
            throw e;
        } catch (Exception e) {
            getObjStep().fail("Exception: " + e.getClass());
            throw e;
        }
    }

    private PubHubResponseVerifier() {
    }
}
