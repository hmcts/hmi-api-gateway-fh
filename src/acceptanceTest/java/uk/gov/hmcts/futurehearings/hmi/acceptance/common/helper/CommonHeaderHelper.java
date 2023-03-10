package uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper;

import com.microsoft.applicationinsights.boot.dependencies.apachecommons.lang3.ArrayUtils;
import io.restassured.http.Headers;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Map;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDtoFactory.buildStandardBusinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDtoFactory.buildStandardSystemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDtoFactory.convertToMapWithAllHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDtoFactory.convertToMapWithMandatoryHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDtoFactory.convertToRestAssuredHeaderRequiredHeaders;

public class CommonHeaderHelper {

    private static final String MOCK_DESTINATION_SYSTEM = "MOCK";
    private static final String REQUEST_CREATED_DATE = "2012-03-19T07:22:00Z";
    private static final String DESTINATION_SYSTEM = MOCK_DESTINATION_SYSTEM;

    public static Map<String, String> createCompletePayloadHeader(final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "no-cache",
                null,
                REQUEST_CREATED_DATE,
                sourceSystem,
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createStandardPayloadHeader() {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                REQUEST_CREATED_DATE,
                "CFT",
                DESTINATION_SYSTEM
        );
    }

    public static Headers createStandardPayloadHeaderWithDuplicateValues(
            final Map<String, String> duplicateHeaderValues, final String sourceSystem) {

        return buildHeaderWithDoubleValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                "no-cache",
                null,
                REQUEST_CREATED_DATE,
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
                REQUEST_CREATED_DATE,
                sourceSystem,
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createHeaderWithSourceAndDestinationSystemValues(final String sourceSystem,
        final String destinationSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                REQUEST_CREATED_DATE,
                sourceSystem,
                destinationSystem
        );
    }

    public static Map<String, String> createHeaderWithRequestCreatedAtSystemValue(final String requestCreatedAt,
        final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                requestCreatedAt,
                sourceSystem,
                DESTINATION_SYSTEM
        );
    }

    public static Map<String, String> createHeaderWithAcceptTypeAtSystemValue(final String acceptType,
        final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                acceptType,
                REQUEST_CREATED_DATE,
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
        return Collections.unmodifiableMap(convertToMapWithMandatoryHeaders(buildStandardSystemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                null),
                buildStandardBusinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                       )));
    }

    private static Map<String, String> buildHeaderWithValues(final String contentType,
                                                             final String acceptType,
                                                             final String cacheControl,
                                                             final String contentEncoding,
                                                             final String requestCreatedDate,
                                                             final String sourceSystem,
                                                             final String destinationSystem
                                                             ) {
        return Collections.unmodifiableMap(convertToMapWithAllHeaders(buildStandardSystemHeaderPart(
                contentType,
                acceptType,
                null,
                contentEncoding,
                cacheControl),
                buildStandardBusinessHeaderPart(requestCreatedDate,
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
        return convertToRestAssuredHeaderRequiredHeaders(buildStandardSystemHeaderPart(
                contentType,
                acceptType,
                null,
                contentEncoding,
                cacheControl),
                buildStandardBusinessHeaderPart(requestCreatedDate,
                        sourceSystem,
                        destinationSystem
                        ), extraHeaderValue);
    }

    public static String[] removeItemsFromArray(String[] arrayToRemoveItemsFrom, String... itemsToBeRemoved) {
        var updatedArray = arrayToRemoveItemsFrom;
        if (!(arrayToRemoveItemsFrom == null) && arrayToRemoveItemsFrom.length > 0) {
            for (String s : itemsToBeRemoved) {
                updatedArray = ArrayUtils.removeElement(updatedArray, s);
            }
        }
        return updatedArray;
    }

    private CommonHeaderHelper() {
    }
}
