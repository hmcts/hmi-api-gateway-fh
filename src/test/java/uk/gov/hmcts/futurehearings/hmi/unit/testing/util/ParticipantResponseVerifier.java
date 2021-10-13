package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

public class ParticipantResponseVerifier {

    public static void thenValidateResponseForAddParticipant(final Response response) {
        try{
            assertEquals(201, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 201");
        }
        catch (AssertionError e){
            getObjStep().fail("Exception in "+e.getMessage());
            throw e;
        }
        catch (Exception e){
            getObjStep().fail("Exception: "+e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForUpdateParticipant(final Response response) {
        try{
            assertEquals(204, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 204");
        }
        catch (AssertionError e){
            getObjStep().fail("Exception in "+e.getMessage());
            throw e;
        }
        catch (Exception e){
            getObjStep().fail("Exception: "+e.getClass());
            throw e;
        }
    }

    public static void  thenValidateResponseForAddParticipantWithInvalidHeader(Response response){
        try{
            assertEquals(400, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 400");
        }
        catch (AssertionError e){
            getObjStep().fail("Exception in "+e.getMessage());
            throw e;
        }
        catch (Exception e){
            getObjStep().fail("Exception: "+e.getClass());
            throw e;
        }
    }

    public static void thenValidateResponseForAddParticipantWithInvalidToken(final Response response) {
        try{
            assertEquals(401, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 401");
        }
        catch (AssertionError e){
            getObjStep().fail("Exception in "+e.getMessage());
            throw e;
        }
        catch (Exception e){
            getObjStep().fail("Exception: "+e.getClass());
            throw e;
        }
    }

}
