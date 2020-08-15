package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestTemplate.shouldExecute;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderRemoveFields;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonPutDelegate {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/schedule/input";

    public static final void test_successfull_response_in_a_post(final String targetSubscriptionKey,
                                                                 final String targetURL) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        Map<String,String> standardHeaderMap = createPayloadHeader(targetSubscriptionKey);
        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/mock-demo-request.json");
        shouldExecute(standardHeaderMap,
                inputPayload,
                targetURL,
                HttpStatus.OK,
                HttpMethod.PUT);

        /*verifyResponse(shouldExecutePost(standardHeaderMap,
                inputPayload,
                targetURL, HttpStatus.OK));*/

    }


    public static final void test_content_type_removed_in_a_post(final String targetSubscriptionKey,
                                                   final String targetURL) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        Map<String,String> standardHeaderMap = createPayloadHeaderRemoveFields(targetSubscriptionKey,
                Arrays.asList("Source-System"));
        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/mock-demo-request.json");
        shouldExecute(standardHeaderMap,
                inputPayload,
                targetURL,
                HttpStatus.UNAUTHORIZED,
                HttpMethod.PUT);

       /* verifyResponse(shouldExecutePost(standardHeaderMap,
                inputPayload,
                targetURL, HttpStatus.UNAUTHORIZED));*/

    }

}
