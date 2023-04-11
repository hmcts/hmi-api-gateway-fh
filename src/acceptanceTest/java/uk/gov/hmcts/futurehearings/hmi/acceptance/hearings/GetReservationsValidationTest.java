package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.suite.api.IncludeTags;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.QueryParamsHelper.buildQueryParams;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeTags("GET")
public class GetReservationsValidationTest extends HearingValidationTest {

    private static final String NIL = "NIL";
    private static final String DISPLAY_NAME = "Testing valid and invalid values of the query parameter - ";

    @Value("${hearings_ReservationsApiRootContext}")
    private String hearingsReservationsApiRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeUrl(hearingsReservationsApiRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSuccessStatus(HttpStatus.OK);
        setCheckUnsupportedDestinations(true);
        String[] supportedDestinations = {"SNL"};
        this.extractUnsupportedDestinations(supportedDestinations);
    }

    private void testResponseForSuppliedParameter() throws Exception {
        commonDelegate.testExpectedResponseForSuppliedHeader(
                getAuthorizationToken(),
                getRelativeUrl(),
                getInputPayloadFileName(),
                createStandardPayloadHeader(),
                null,
                getUrlParams(),
                getHttpMethod(),
                HttpStatus.OK,
                getInputFileDirectory(),
                hmiSuccessVerifier,
                null,
                null);
    }

    @Test
    @DisplayName("Testing the Endpoint for GET Reservation with an Invalid Query Parameter")
    void testInvalidQueryParamWithValue() throws Exception {
        this.setUrlParams(buildQueryParams("non-existing-param",
                "HMI should not have validation"));
        testResponseForSuppliedParameter();
    }

    @ParameterizedTest(name = DISPLAY_NAME + "requestStartDate : {0} --> {1}")
    @CsvSource(value = {"requestStartDate,2018-01-29 20:36:01Z", "requestStartDate,AD12H",
        "requestStartDate,''", "requestStartDate,' '", "requestStartDate,NIL"}, nullValues = NIL)
    void testRequestStartDateQueryParam(final String requestStartDateKey,
                                             final String requestStartDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestStartDateKey, requestStartDateValue));
        testResponseForSuppliedParameter();
    }

    @ParameterizedTest(name = DISPLAY_NAME + "- requestEndDate : {0} --> {1}")
    @CsvSource(value = {"requestEndDate, 2018-01-29 20:36:01Z", "requestEndDate, AD12H",
        "requestEndDate, ''", "requestEndDate, ' '", "requestEndDate, NIL"}, nullValues = NIL)
    void testRequestEndDateQueryParam(final String requestEndDateKey,
                                           final String requestEndDateValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestEndDateKey, requestEndDateValue));
        testResponseForSuppliedParameter();
    }

    @ParameterizedTest(name = DISPLAY_NAME + "requestDuration : {0} --> {1}")
    @CsvSource(value = {"requestDuration, 360", "requestDuration, case01", "requestDuration, ''",
        "requestDuration, ' '", "requestDuration, NIL"}, nullValues = NIL)
    void testRequestDurationQueryParam(final String requestDurationKey,
                                           final String requestDurationValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestDurationKey, requestDurationValue));
        testResponseForSuppliedParameter();
    }

    @ParameterizedTest(name = DISPLAY_NAME
            + "requestLocationType, requestLocationId and requestLocationReferenceType - Param : {0} --> {1}")
    @CsvSource({"requestLocationType, Court, requestLocationId, 300, "
            + "requestLocationReferenceType, EXTERNAL", "requestLocationType, ADHOC, "
            + "requestLocationId, Unknown, requestLocationReferenceType, Undefined"})
    void testRequestLocationTypeWithMultipleQueryParams(final String paramKey1,
                                                                final String paramVal1,
                                                                final String paramKey2,
                                                                final String paramVal2,
                                                                final String paramKey3,
                                                                final String paramVal3) throws Exception {
        this.setUrlParams(buildQueryParams(paramKey1, paramVal1, paramKey2, paramVal2, paramKey3, paramVal3));
        testResponseForSuppliedParameter();
    }

    @ParameterizedTest(name = DISPLAY_NAME + "requestComments : {0} --> {1}")
    @CsvSource(value = {"requestComments, civil case", "requestComments, ADHOC", "requestComments, ''",
        "requestComments, ' '", "requestComments, NIL"}, nullValues = NIL)
    void testRequestCommentsQueryParam(final String requestCommentsKey,
                                           final String requestCommentsValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestCommentsKey, requestCommentsValue));
        testResponseForSuppliedParameter();
    }

    @ParameterizedTest(name = DISPLAY_NAME + "requestExternalId : {0} --> {1}")
    @CsvSource(value = {"requestExternalId, TEST_EXTERNAL_ID", "requestExternalId, ADHOC",
        "requestExternalId, ''", "requestExternalId, ' '", "requestExternalId, NIL"}, nullValues = NIL)
    void testRequestExternalIdQueryParam(final String requestExternalIdKey,
                                              final String requestExternalIdValue) throws Exception {
        this.setUrlParams(buildQueryParams(requestExternalIdKey, requestExternalIdValue));
        testResponseForSuppliedParameter();
    }
}
