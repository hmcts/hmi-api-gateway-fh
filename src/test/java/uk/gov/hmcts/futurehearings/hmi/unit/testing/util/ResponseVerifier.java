package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import io.restassured.response.Response;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import com.aventstack.extentreports.ExtentTest;

public class ResponseVerifier {

    public static void checkResponseForError(Response response, String missingField, ExtentTest objStep) {

        try{
            Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
            assertEquals(2, responseMap.size());
            //objStep.pass("Response is of the expected size: 2");
            assertEquals("Status Code Validation: ",400, response.getStatusCode());
            objStep.pass("Got the expected response code: 400");
            assertEquals("Status Code Description: ","Malformed request. Missing/Invalid property: '" + missingField + "'", responseMap.get(("description")));
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

}
