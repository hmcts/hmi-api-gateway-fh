package uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterHeadersAdded;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapWithAllHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapWithMandatoryHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToRestAssuredHeaderRequiredHeaders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.restassured.http.Headers;
import org.springframework.http.MediaType;

public class CommonHeaderHelper {

    private static final String MOCK_DESTINATION_SYSTEM = "MOCK";
    private static final String DESTINATION_SYSTEM = MOCK_DESTINATION_SYSTEM;

    public static Map<String, String> createCompletePayloadHeader(final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "no-cache",
                null,
                "2012-03-19T07:22:00Z",
                sourceSystem,
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createStandardPayloadHeader() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static Headers createStandardPayloadHeaderWithDuplicateValues(final Map<String, String> duplicateHeaderValues, final String sourceSystem) {

        return buildHeaderWithDoubleValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "no-cache",
                null,
                "2012-03-19T07:22:00Z",
                sourceSystem,
                DESTINATION_SYSTEM,
                duplicateHeaderValues
        );
    }

    public static Map<String, String> createHeaderWithAllValuesEmpty() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                "",
                "",
                "",
                "",
                "",
                ""
        );
    }

    public static Map<String, String> createHeaderWithSourceSystemValue(final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                sourceSystem,
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createHeaderWithSourceAndDestinationSystemValues( final String sourceSystem, final String destinationSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                sourceSystem,
                destinationSystem
        );
    }

    public static Map<String, String> createHeaderWithEmulatorValues(final String destinationSystem,
                                                                     final String returnHttpCode,
                                                                     final String returnErrorCode,
                                                                     final String returnDescription) {

        Map<String,String> emulatorHeaderValues = new HashMap<String,String>();
        emulatorHeaderValues.put("returnHttpCode",returnHttpCode);
        emulatorHeaderValues.put("returnErrorCode",returnErrorCode);
        emulatorHeaderValues.put("returnDescription",returnDescription);

        return buildHeaderWithEmulatorValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
                destinationSystem,
                emulatorHeaderValues
        );
    }

    public static Map<String, String> createHeaderWithRequestCreatedAtSystemValue(final String requestCreatedAt, final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                requestCreatedAt,
                sourceSystem,
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createHeaderWithAcceptTypeAtSystemValue(final String acceptType, final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                acceptType,
                "2012-03-19T07:22:00Z",
                sourceSystem,
                DESTINATION_SYSTEM
        );
    }

    private static Map<String, String> buildHeaderWithValues(final String contentType,
                                                             final String acceptType,
                                                             final String requestCreatedDate,
                                                             final String sourceSystem,
                                                             final String destinationSystem
                                                             ) {
        return Collections.unmodifiableMap(convertToMapWithMandatoryHeaders(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                null),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                       )));
    }

    private static Map<String, String> buildHeaderWithEmulatorValues(final String contentType,
                                                                     final String acceptType,
                                                                     final String requestCreatedDate,
                                                                     final String sourceSystem,
                                                                     final String destinationSystem,
                                                                     final Map<String, String> emulatorHeaders) {
        return Collections.unmodifiableMap(convertToMapAfterHeadersAdded(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                null),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                        ),emulatorHeaders));
    }

    private static Map<String, String> buildHeaderWithValues(final String contentType,
                                                             final String acceptType,
                                                             final String cacheControl,
                                                             final String contentEncoding,
                                                             final String requestCreatedDate,
                                                             final String sourceSystem,
                                                             final String destinationSystem
                                                             ) {
        return Collections.unmodifiableMap(convertToMapWithAllHeaders(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                contentEncoding,
                cacheControl),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                        )));
    }

    private static Headers buildHeaderWithDoubleValues(final String contentType,
                                                       final String acceptType,
                                                       final String cacheControl,
                                                       final String contentEncoding,
                                                       final String requestCreatedDate,
                                                       final String sourceSystem,
                                                       final String destinationSystem,
                                                       final Map<String, String> extraHeaderValue) {
        return convertToRestAssuredHeaderRequiredHeaders(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                contentEncoding,
                cacheControl),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                        ), extraHeaderValue);
    }
}
