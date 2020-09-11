package uk.gov.hmcts.futurehearings.hmi.acceptance.schedules;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper.SessionsParamsHelper.buildAllQueryParams;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper.SessionsParamsHelper.buildMultipleQueryParams;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper.SessionsParamsHelper.buildQueryParams;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class GETSchedulesValidationTest extends SchedulesValidationTest {

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${schedulesApiRootContext}")
    private String schedulesApiRootContext;

    @BeforeAll
    public void initialiseValues() {
        super.initialiseValues();
        this.setRelativeURL(schedulesApiRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("schedules","schedule"));
        //this.setUrlParams(buildValidRetrieveScheduleParams());
    }


    /*@ParameterizedTest(name = "Hearing Date with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_date, date", "hearing_date,\" \"", "hearing_date,", "hearing_date, 2002-10-02T10:00:00-05:00"})
    public void test_hearing_date_queryparam_with_value(final String hearingDateKey, final String hearingDateValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingDateKey, hearingDateValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }

    @ParameterizedTest(name = "Hearing Venue Id with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_venue_id, 234", "hearing_venue_id,\" \"", "hearing_venue_id,"})
    public void test_hearing_venue_id_queryparam_with_value(final String hearingVenueIdKey, final String hearingVenueIdValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingVenueIdKey, hearingVenueIdValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }

    @ParameterizedTest(name = "Hearing Room Id with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_room_id, 234", "hearing_room_id,\" \"", "hearing_room_id,"})
    public void test_hearing_room_id_queryparam_with_value(final String hearingRoomIdKey, final String hearingRoomIdValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingRoomIdKey, hearingRoomIdValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }

    @ParameterizedTest(name = "Hearing Session Id CaseHQ with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_session_id_casehq, 234", "hearing_session_id_casehq,\" \"", "hearing_session_id_casehq,"})
    public void test_hearing_session_id_casehq_queryparam_with_value(final String hearingSessionIdCaseHQKey, final String hearingSessionIdCaseHQValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingSessionIdCaseHQKey, hearingSessionIdCaseHQValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }

    @ParameterizedTest(name = "Hearing Case Id HMCTS with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_case_id_hmcts, 234", "hearing_case_id_hmcts,\" \"", "hearing_case_id_hmcts,"})
    public void test_hearing_case_id_hmcts_queryparam_with_value(final String hearingCaseIdHmctsKey, final String hearingCaseIdHmctsValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingCaseIdHmctsKey, hearingCaseIdHmctsValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }

    @ParameterizedTest(name = "Hearing Id CaseHQ with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_id_casehq, 234", "hearing_id_casehq,\" \"", "hearing_id_casehq,"})
    public void test_hearing_id_casehq_queryparam_with_value(final String hearingIdCaseHQKey, final String hearingIdCaseHQValue) throws IOException {
        this.setUrlParams(buildQueryParams(hearingIdCaseHQKey, hearingIdCaseHQValue));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }

    @ParameterizedTest(name = "Multiple params (Hearing_Venue_Id & Hearing_Room_Id) with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_venue_id,234,hearing_room_id,123", "hearing_venue_id,,hearing_room_id,"})
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
                HttpStatus.OK, getApiName(),null);
    }

    @ParameterizedTest(name = "Multiple params (Hearing_Session_Id_CaseHQ, Hearing_Case_Id_Hmcts & Hearing_Id_Casehq) with and without value - Param : {0} --> {1}")
    @CsvSource({"hearing_session_id_casehq,234,hearing_case_id_hmcts,123,hearing_id_casehq,case1", "hearing_session_id_casehq,,hearing_case_id_hmcts,,hearing_id_casehq,"})
    public void test_multiple_queryparams_with_value(final String paramKey1,
                                                    final String paramVal1,
                                                    final String paramKey2,
                                                    final String paramVal2,
                                                    final String paramKey3,
                                                    final String paramVal3) throws IOException {
        this.setUrlParams(buildMultipleQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }
    @Test
    public void test_all_params_with_value() throws IOException {
        this.setUrlParams(buildAllQueryParams("hearing_date", "09/09/1964",
                                                    "hearing_venue_id", "1",
                                                    "hearing_room_id", null,
                                                    "hearing_session_id_casehq","",
                                                    "hearing_case_id_hmcts"," ",
                                                    "hearing_id_casehq",""));
        commonDelegate.test_expected_response_for_supplied_header(getApiSubscriptionKey(),
                getRelativeURL(), getInputPayloadFileName(),
                createStandardPayloadHeader(getApiSubscriptionKey()),
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK, getApiName(),null);
    }*/
}
