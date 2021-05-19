package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

public class PubHubResponseVerifier {

    public static void  thenValidateResponseForDelete(Response response){
        try{
            assertEquals(204, response.getStatusCode(),"Response Code Validation:");
            getObjStep().pass("Got the expected response code: 204");
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

    public static void  thenValidateResponseForGet(Response response){
        try{
            assertEquals(200, response.getStatusCode(),"Response Code Validation:");
            getObjStep().pass("Got the expected response code: 200");
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

    public static void  thenValidateResponseForPost(Response response){
        try{
            assertEquals(201, response.getStatusCode(),"Response Code Validation:");
            getObjStep().pass("Got the expected response code: 201");
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
