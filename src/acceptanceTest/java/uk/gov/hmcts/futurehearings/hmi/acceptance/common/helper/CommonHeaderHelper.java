package uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterHeadersAdded;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterHeadersRemoved;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterTruncatingHeaderKey;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapWithAllHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapWithMandatoryHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToRestAssuredHeaderRequiredHeaders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.http.Headers;
import org.springframework.http.MediaType;

public class CommonHeaderHelper {

    private static final String MOCK_DESTINATION_SYSTEM = "MOCK";
    private static final String SNL_DESTINATION_SYSTEM = "SNL";
    private static final String RM_DESTINATION_SYSTEM = "RM";
    private static final String DESTINATION_SYSTEM = MOCK_DESTINATION_SYSTEM;

    public static final Map<String, String> createCompletePayloadHeader() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "no-cache",
                null,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static final Map<String, String> createStandardPayloadHeader() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static final Map<String, String> createPayloadWithCFTDestinationHeader() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "SNL",
                "CFT"
        );
    }

    public static final Headers createStandardPayloadHeaderWithDuplicateValues(Map<String, String> duplicateHeaderValues) {

        return buildHeaderWithDoubleValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "no-cache",
                null,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM,
                duplicateHeaderValues
        );
    }

    public static final Map<String, String> createHeaderWithAllValuesNull() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static final Map<String, String> createHeaderWithAllValuesEmpty() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                "",
                "",
                "",
                "",
                "",
                ""
        );
    }

    public static final Map<String, String> createHeaderWithCorruptedHeaderKey(final List<String> headersToBeTruncated) {

        return buildHeaderWithValuesWithKeysTruncated(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM,
                headersToBeTruncated
        );
    }

    public static final Map<String, String> createHeaderWithRemovedHeaderKey(final List<String> headersToBeRemoved) {

        return buildHeaderWithValuesWithKeysTruncated(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM,
                headersToBeRemoved
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

    public static Map<String, String> createHeaderWithDestinationSystemValue(final String destinationSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
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

    public static Map<String, String> createHeaderWithRequestCreatedAtSystemValue(final String requestCreatedAt) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                requestCreatedAt,
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createHeaderWithRequestProcessedAtSystemValue(final String requestProcessedAt) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createHeaderWithRequestTypeAtSystemValue(final String requestType) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createHeaderWithAcceptTypeAtSystemValue(final String acceptType) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                acceptType,
                "2012-03-19T07:22:00Z",
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static final Map<String, String> createHeaderWithDeprecatedHeaderValue(final String deprecatedHeaderKey,
                                                                                  final String deprecatedHeaderVal
    ) {
        //Set invalid value for specific header key
        final String acceptType = deprecatedHeaderKey.equalsIgnoreCase("X-Accept") ? MediaType.APPLICATION_PDF_VALUE : MediaType.APPLICATION_JSON_VALUE;
        final String sourceSystem = deprecatedHeaderKey.equalsIgnoreCase("X-Source-System") ? "CRIMES" : "CFT";
        final String destinationSystem = deprecatedHeaderKey.equalsIgnoreCase("X-Destination-System") ? "CRIMES" : DESTINATION_SYSTEM;
        final String requestCreatedAt = deprecatedHeaderKey.equalsIgnoreCase("X-Request-Created-At") ? "2002-10-02T15:00:00*05Z" : "2012-03-19T07:22:00Z";

        Map<String, String> headers = convertToMapWithMandatoryHeaders(buildStandardSytemHeaderPart(
                MediaType.APPLICATION_JSON_VALUE,
                acceptType,
                null,
                null,
                null),
                buildStandardBuinessHeaderPart(requestCreatedAt,
                        sourceSystem,
                        destinationSystem
                        ));
        headers.put(deprecatedHeaderKey, deprecatedHeaderVal);
        return Collections.unmodifiableMap(headers);
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

    private static Map<String, String> buildHeaderWithValuesWithKeysTruncated(final String contentType,
                                                                              final String acceptType,
                                                                              final String requestCreatedDate,
                                                                              final String sourceSystem,
                                                                              final String destinationSystem,
                                                                              List<String> headersToTruncate) {
        return Collections.unmodifiableMap(convertToMapAfterTruncatingHeaderKey(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                null),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                        ), headersToTruncate));

    }

    private static Map<String, String> buildHeaderWithValuesWithKeysRemoved(final String contentType,
                                                                            final String acceptType,
                                                                            final String requestCreatedDate,
                                                                            final String sourceSystem,
                                                                            final String destinationSystem,
                                                                            List<String> headersToBeRemoved) {
        return Collections.unmodifiableMap(convertToMapAfterHeadersRemoved(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                null),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                        ), headersToBeRemoved));

    }

}
