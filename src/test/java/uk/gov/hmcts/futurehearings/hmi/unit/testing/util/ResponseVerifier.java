package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResponseVerifier {

    private static final String MISSING_SUB_KEY_ERROR = "Access denied due to missing subscription key. Make sure to include subscription key when making requests to an API.";
    private static final String INVALID_SUB_KEY_ERROR = "Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.";


    public static void  thenValidateHearingResponseForInvalidResource(Response response, ExtentTest objStep){
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

    public static void  thenASuccessfulResponseForHearingRequestIsReturned(Response response, ExtentTest objStep){
        try{

            assertEquals("Status Code Validation:",200, response.getStatusCode());
            objStep.pass("Got the expected response code: 200");

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

    public static void thenResponseForMissingHeaderOcpSubscriptionIsReturned(Response response, ExtentTest objStep){

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

    public static void  thenResponseForInvalidOcpSubscriptionIsReturned(Response response, ExtentTest objStep){

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

    public static void  thenValidateHearingResponseForMissingHeader(Response response, String missingField, ExtentTest objStep){

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

    public static void  thenValidateHearingResponseForMissingAcceptHeader(Response response, ExtentTest objStep){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",406, response.getStatusCode());
            objStep.pass("Got the expected response code: 406");
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


    public static void  thenValidateHearingResponseForMissingContentTypeHeader(Response response, ExtentTest objStep){

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


    public static void  thenValidateHearingsResponseForAdditionalParam(Response response, ExtentTest objStep){

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            //assertEquals(2, responseMap.size());
            assertEquals("Status Code Validation:",400, response.getStatusCode());
            objStep.pass("Got the expected response code: 400");
            assertEquals("Status Code Description Validation:","Invalid query parameter/s in the request URL.", responseMap.get(("message")));
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
