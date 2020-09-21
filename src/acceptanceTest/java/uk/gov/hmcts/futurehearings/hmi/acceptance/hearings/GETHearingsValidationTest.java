package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper.SessionsParamsHelper.buildMultipleQueryParams;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper.SessionsParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.hearings.verify.GETHearingsByQueryValidationVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.hearings.verify.GETHearingsValidationVerifier;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(GETHearingsValidationTest.class)
@IncludeTags("GET")
public class GETHearingsValidationTest extends HearingValidationTest {

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    private CommonDelegate commonDelegate;

    @Value("${hearingsApiRootContext}")
    private String hearingsApiRootContext;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
        this.setRelativeURL(hearingsApiRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setInputPayloadFileName("hearing-request-standard.json");
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("hearings","hearing"));
        this.setHmiSuccessVerifier(new GETHearingsValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }

    @ParameterizedTest(name = "Hearing Date with and without value - Param : {0} --> {1}")
    @CsvSource({"hearingDate, date", "hearingDate,\" \"", "hearingDate,", "hearingDate, 2002-10-02T10:00:00-05:00"})
    public void test_hearing_date_queryparam_with_value(final String hearingDateKey,
                                                        final String hearingDateValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingDateKey, hearingDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK,  getInputFileDirectory(),
                null,
                null,
                getHmiSuccessVerifier(),
                "The request was received successfully.");
    }

    @ParameterizedTest(name = "Hearing Id CaseHQ with and without value - Param : {0} --> {1}")
    @CsvSource({"hearingIdCaseHQ, 234", "hearingIdCaseHQ,\" \"", "hearingIdCaseHQ,null"})
    public void test_hearing_id_casehq_queryparam_with_value(final String hearingIdCaseHQKey, final String hearingIdCaseHQValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingIdCaseHQKey, hearingIdCaseHQValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                null,
                null,
                new GETHearingsByQueryValidationVerifier(),
                "The request was received successfully.");
    }

    @ParameterizedTest(name = "Hearing Type with and without value - Param : {0} --> {1}")
    @CsvSource({"hearingType, Theft", "hearingType,\" \"", "hearingType,"})
    public void test_hearing_type_queryparam_with_value(final String hearingIdCaseHQKey, final String hearingIdCaseHQValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingIdCaseHQKey, hearingIdCaseHQValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                null,
                null,
                getHmiSuccessVerifier(),
                "The request was received successfully.");
    }

    @ParameterizedTest(name = "Multiple params (Hearing_Date & Hearing_Id_CaseHQ) with and without value - Param : {0} --> {1}")
    @CsvSource({"hearingDate,2002-10-02T10:00:00-05:00,hearingIdCaseHQ,123", "hearingDate,,hearingIdCaseHQ,null"})
    public void test_multiple_queryparam_with_value(final String hearingVenueIdKey,
                                                    final String hearingVenueIdValue,
                                                    final String hearingRoomIdKey,
                                                    final String hearingRoomIdValue) throws IOException {
        this.setUrlParams(buildMultipleQueryParams(hearingVenueIdKey, hearingVenueIdValue, hearingRoomIdKey, hearingRoomIdValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getInputFileDirectory(),
                null,
                null,
                new GETHearingsByQueryValidationVerifier(),
                "The request was received successfully.");
    }
}
