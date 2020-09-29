package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

import java.util.Map;

import io.restassured.response.Response;

public class HearingsResponseVerifier {

    private static final String MISSING_SUB_KEY_ERROR = "Access denied due to missing subscription key. Make sure to include subscription key when making requests to an API.";
    private static final String INVALID_SUB_KEY_ERROR = "Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.";


    public static final void thenValidateResponseForInvalidResource(Response response){
        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(404, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 404");
            assertEquals("Resource not found", responseMap.get(("message")),"Status Code Message Validation:");
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
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

    public static void  thenValidateResponseForRequestOrDelete(Response response){
        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(200, response.getStatusCode(),"Response Code Validation:");
            getObjStep().pass("Got the expected response code: 200");
            assertEquals("Response Code Description Validation:","The request was received successfully.", responseMap.get(("description")));
            getObjStep().pass("Got the expected description: " + responseMap.get(("description")));

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

    public static void thenValidateResponseForUpdate(Response response) {

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(201, response.getStatusCode(),"Response Code Validation:");
            getObjStep().pass("Got the expected response code: 201");
            assertEquals("Response Code Description Validation:","The request was received successfully.", responseMap.get(("description")));
            getObjStep().pass("Got the expected description: " + responseMap.get(("description")));
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
    public static void  thenValidateResponseforRetrieve(Response response){
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

    public static void thenValidateResponseForMissingSubscriptionKeyHeader(Response response){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(401, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 401");
            assertEquals("Status Code Message Validation:",MISSING_SUB_KEY_ERROR, responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
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

    public static void  thenValidateResponseForInvalidSubscriptionKeyHeader(Response response){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(401, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 401");
            assertEquals("Status Code Message Validation:",INVALID_SUB_KEY_ERROR, responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
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

    public static void  thenValidateResponseForMissingOrInvalidHeader(Response response, String missingField){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(400, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Status Code Message Validation:","Missing/Invalid Header "+missingField, responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
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

    public static void  thenValidateResponseForMissingOrInvalidAcceptHeader(Response response){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(406, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 406");
            assertEquals("Status Code Message Validation:","Missing/Invalid Media Type", responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
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

    public static void  thenValidateResponseForMissingOrInvalidContentTypeHeader(Response response){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(400, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Status Code Message Validation:","Missing/Invalid Media Type", responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
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

    public static void  thenValidateResponseForAdditionalParam(Response response){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals(400, response.getStatusCode(),"Status Code Validation:");
            getObjStep().pass("Got the expected status code: 400");
            assertEquals("Status Code Message Validation:","Invalid query parameter/s in the request URL.", responseMap.get(("message")));
            getObjStep().pass("Got the expected message: " + responseMap.get(("message")));
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
