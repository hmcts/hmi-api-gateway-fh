package uk.gov.hmcts.futurehearings.hmi.acceptance.delegate;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestTemplate.shouldExecutePost;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMap;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.schedule.verify.ScheduleResponseVerification.verifyScheduleResponse;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.integration.common.TestingUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@Component
public class Schedule {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/schedule/input";

    public void test_successfull_post(final String targetSubscriptionKey,
                                      final String targetURL) throws IOException {

        System.out.println("The value of TEST SUBSCRIPTION KEY " +System.getProperty("TEST_SUBSCRIPTION_KEY"));
        System.out.println("The value of the targetSubscriptionKey " +targetSubscriptionKey);

        Map<String,String> standardPayloadMap = createPayloadHeader(targetSubscriptionKey);
        String inputPayload =
                TestingUtils.readFileContents( INPUT_FILE_PATH + "/mock-demo-request.json");

        verifyScheduleResponse(shouldExecutePost(standardPayloadMap,
                inputPayload,
                targetURL, HttpStatus.OK));

    }

    private static final Map<String,String> createPayloadHeader (final String targetSubscriptionKey) {

        return Collections.unmodifiableMap(convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                "true",
                "hmi-apim-svc-sbox.azure-api.net",
                targetSubscriptionKey),

                buildStandardBuinessHeaderPart("HMCTS",
                        "dateTime",
                        "SnL",
                        "CFT",
                        "TypeOfCase")));
    }
}
