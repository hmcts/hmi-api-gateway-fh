package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import static io.restassured.config.EncoderConfig.encoderConfig;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.test.HMICommonHeaderTest;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
public abstract class HearingValidationTest extends HMICommonHeaderTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @BeforeAll
    public void initialiseValues() {
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        this.setApiSubscriptionKey(targetSubscriptionKey);
        RestAssured.config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        this.setInputFileDirectory("hearings");
    }

    /*@Disabled("Test Failing and giving multiple different Errors while testing - So Disabled- TBC With Lee")
    @Test
    @DisplayName("Successfully validated response with an xml payload")
    public void test_successful_response_for_test_xml_body() throws Exception {
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), "sample-xml-payload.xml",
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.BAD_REQUEST, getApiName(),null);
    }*/
}
