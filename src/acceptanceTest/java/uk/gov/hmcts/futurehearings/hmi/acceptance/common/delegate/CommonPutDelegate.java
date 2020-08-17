package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestClientTemplate.shouldExecute;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderEmptyFields;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.createPayloadHeaderNullFields;
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
@Component("PutDelegate")
public abstract class CommonPutDelegate implements CommonDelegate {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/schedule/input";

    public void test_expected_response_for_supplied_header_in_a_post(final String targetSubscriptionKey,
                                                                     final String targetURL,
                                                                     final String inputFile,
                                                                     final Map<String,String> standardHeaderMap,
                                                                     final HttpStatus status) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/" + inputFile);
        shouldExecute(standardHeaderMap,
                inputPayload,
                targetURL,
                status,
                HttpMethod.POST);

        /*verifyResponse(shouldExecutePost(standardHeaderMap,
                inputPayload,
                targetURL, HttpStatus.OK));*/

    }

    @Override
    public void test_successful_response_in_a_post_test(final String targetSubscriptionKey,
                                                        final String targetURL,
                                                        final String inputFile) throws IOException {

    }

    public void test_successful_response_in_a_post(final String targetSubscriptionKey,
                                                   final String targetURL,
                                                   final String inputFile) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        Map<String,String> standardHeaderMap = createPayloadHeader(targetSubscriptionKey);
        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/" + inputFile);
        shouldExecute(standardHeaderMap,
                inputPayload,
                targetURL,
                HttpStatus.OK,
                HttpMethod.POST);

        /*verifyResponse(shouldExecutePost(standardHeaderMap,
                inputPayload,
                targetURL, HttpStatus.OK));*/

    }


    public void test_source_system_removed_in_a_post(final String targetSubscriptionKey,
                                                     final String targetURL,
                                                     final String inputFile) throws IOException {

        log.debug("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        log.debug("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        Map<String,String> standardHeaderMap = createPayloadHeaderRemoveFields(targetSubscriptionKey,
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

    public void test_source_system_empty_in_a_post(final String targetSubscriptionKey,
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
