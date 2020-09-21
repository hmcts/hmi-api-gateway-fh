package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RetrieveResourcesResponseVerifier {
    private static final String MISSING_SUB_KEY_ERROR = "Access denied due to missing subscription key. Make sure to include subscription key when making requests to an API.";
    private static final String INVALID_SUB_KEY_ERROR = "Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.";


    public static void  thenValidateResponseForInvalidResource(Response response, ExtentTest objStep){
        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",404, response.getStatusCode());
            objStep.pass("Got the expected response code: 404");
            assertEquals("Status Code Description Validation:","Resource not found", responseMap.get(("message")));
            objStep.pass("Got the expected description: " + responseMap.get(("message")));
        }
        catch (AssertionError e){
            objStep.fail("Exception in "+e.getMessage());
            objStep.info(e);
            throw e;
        }
        catch (Exception e){
            objStep.fail("Exception: "+e.getClass());
            objStep.info(e);
            throw e;
        }
    }

    public static void  thenValidateResponseForACorrectRequest(Response response, ExtentTest objStep){
        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals("Status Code Validation:",200, response.getStatusCode());
            objStep.pass("Got the expected response code: 200");
            assertEquals("Status Code Description Validation:","The request was received successfully.", responseMap.get(("description")));
            objStep.pass("Got the expected description: " + responseMap.get(("description")));

        }
        catch (AssertionError e){
            objStep.fail("Exception in "+e.getMessage());
            objStep.info(e);
            throw e;
        }
        catch (Exception e){
            objStep.fail("Exception: "+e.getClass());
            objStep.info(e);
            throw e;
        }
    }

    public static void thenValidateResponseForMissingOcpSubscriptionHeader(Response response, ExtentTest objStep){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",401, response.getStatusCode());
            objStep.pass("Got the expected response code: 401");
            assertEquals("Status Code Description Validation:",MISSING_SUB_KEY_ERROR, responseMap.get(("message")));
            objStep.pass("Got the expected description: " + responseMap.get(("message")));
        }
        catch (AssertionError e){
            objStep.fail("Exception in "+e.getMessage());
            objStep.info(e);
            throw e;
        }
        catch (Exception e){
            objStep.fail("Exception: "+e.getClass());
            objStep.info(e);
            throw e;
        }
    }

    public static void  thenValidateResponseForInvalidOcpSubscription(Response response, ExtentTest objStep){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",401, response.getStatusCode());
            objStep.pass("Got the expected response code: 401");
            assertEquals("Status Code Description Validation:",INVALID_SUB_KEY_ERROR, responseMap.get(("message")));
            objStep.pass("Got the expected description: " + responseMap.get(("message")));
        }
        catch (AssertionError e){
            objStep.fail("Exception in "+e.getMessage());
            objStep.info(e);
            throw e;
        }
        catch (Exception e){
            objStep.fail("Exception: "+e.getClass());
            objStep.info(e);
            throw e;
        }
    }

    public static void  thenValidateResponseForMissingHeader(Response response, String missingField, ExtentTest objStep){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",400, response.getStatusCode());
            objStep.pass("Got the expected response code: 400");
            assertEquals("Status Code Description Validation:","Missing/Invalid Header "+missingField, responseMap.get(("message")));
            objStep.pass("Got the expected description: " + responseMap.get(("message")));
        }
        catch (AssertionError e){
            objStep.fail("Exception in "+e.getMessage());
            objStep.info(e);
            throw e;
        }
        catch (Exception e){
            objStep.fail("Exception: "+e.getClass());
            objStep.info(e);
            throw e;
        }

    }

    public static void  thenValidateResponseForMissingAcceptHeader(Response response, ExtentTest objStep){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",406, response.getStatusCode());
            objStep.pass("Got the expected response code: 400");
            assertEquals("Status Code Description Validation:","Missing/Invalid Media Type", responseMap.get(("message")));
            objStep.pass("Got the expected description: " + responseMap.get(("message")));
        }
        catch (AssertionError e){
            objStep.fail("Exception in "+e.getMessage());
            objStep.info(e);
            throw e;
        }
        catch (Exception e){
            objStep.fail("Exception: "+e.getClass());
            objStep.info(e);
            throw e;
        }
    }


    public static void  thenValidateResponseForMissingContentTypeHeader(Response response, ExtentTest objStep){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",400, response.getStatusCode());
            objStep.pass("Got the expected response code: 400");
            assertEquals("Status Code Description Validation:","Missing/Invalid Media Type", responseMap.get(("message")));
            objStep.pass("Got the expected description: " + responseMap.get(("message")));
        }
        catch (AssertionError e){
            objStep.fail("Exception in "+e.getMessage());
            objStep.info(e);
            throw e;
        }
        catch (Exception e){
            objStep.fail("Exception: "+e.getClass());
            objStep.info(e);
            throw e;
        }
    }
}
