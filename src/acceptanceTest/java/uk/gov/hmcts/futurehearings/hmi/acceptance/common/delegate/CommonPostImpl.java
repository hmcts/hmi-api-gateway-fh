package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestClientTemplate.shouldExecute;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderEmptyFields;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderNullFields;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.CommonResponseVerification.verifyResponse;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CommonDelegate")
public class CommonPostImpl implements CommonDelegate {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/schedule/input";

    public void test_expected_response_for_supplied_header(final String targetSubscriptionKey,
                                                                final String targetURL,
                                                                final String inputFile,
                                                                final Map<String,String> standardHeaderMap,
                                                                final HttpMethod httpMethod,
                                                                final HttpStatus status) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/" + inputFile);
        Response response = shouldExecute(standardHeaderMap,
                inputPayload,
                targetURL,
                status,
                httpMethod);

        verifyResponse(response);

    }

    public void test_source_system_nulled_in_a_post(final String targetSubscriptionKey,
                                                    final String targetURL,
                                                    final String inputFile) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        Map<String,String> standardHeaderMap = createPayloadHeaderNullFields(targetSubscriptionKey,
                Arrays.asList("Source-System"));
        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/" + inputFile);
        shouldExecute(standardHeaderMap,
                inputPayload,
                targetURL,
                HttpStatus.UNAUTHORIZED,
                HttpMethod.POST);

       /* verifyResponse(shouldExecutePost(standardHeaderMap,
                inputPayload,
                targetURL, HttpStatus.UNAUTHORIZED));*/

    }


    public static final void test_invalidURL(final String targetSubscriptionKey,
                                                                final String targetURL,
                                                                final String inputFile) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        Map<String,String> standardHeaderMap = createPayloadHeaderEmptyFields(targetSubscriptionKey,
                Arrays.asList("Source-System"));
        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/" + inputFile);
        shouldExecute(standardHeaderMap,
                inputPayload,
                targetURL,
                HttpStatus.NOT_FOUND,
                HttpMethod.POST);

       /* verifyResponse(shouldExecutePost(standardHeaderMap,
                inputPayload,
                targetURL, HttpStatus.UNAUTHORIZED));*/

    }

}
