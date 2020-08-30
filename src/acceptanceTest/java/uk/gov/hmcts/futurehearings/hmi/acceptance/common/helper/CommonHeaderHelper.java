package uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapWithAllHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapWithMandatoryHeaders;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterHeadersRemoved;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterTruncatingHeaderKey;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;

public class CommonHeaderHelper {

    public static final Map<String,String> createCompletePayloadHeader (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "no-cache",
                null,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }
    public static final Map<String,String> createStandardPayloadHeader (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static final Map<String,String> createHeaderWithAllValuesNull () {

         return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                 null,
                null,
                 null,
                 null,
                 null,
                 null,
                 null
        );
    }

    public static final Map<String,String> createHeaderWithAllValuesEmpty () {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );
    }

    public static final Map<String,String> createHeaderWithCorruptedHeaderKey (final String subscriptionKey,
                                                                                     final List<String> headersToBeTruncated) {

        return buildHeaderWithValuesWithKeysTruncated(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault",
                headersToBeTruncated
        );
    }

    public static final Map<String,String> createHeaderWithRemovedHeaderKey (final String subscriptionKey,
                                                                               final List<String> headersToBeRemoved) {

        return buildHeaderWithValuesWithKeysTruncated(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault",
                headersToBeRemoved
        );
    }

    public static Map<String,String> createHeaderWithSourceSystemValue (final String subscriptionKey,
                                                                        final String sourceSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                sourceSystem,
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithDestinationSystemValue (final String subscriptionKey,
                                                                        final String destinationSystem) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                destinationSystem,
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithRequestCreatedAtSystemValue (final String subscriptionKey,
                                                                             final String requestCreatedAt) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                requestCreatedAt,
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithRequestProcessedAtSystemValue (final String subscriptionKey,
                                                                                  final String requestProcessedAt) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                requestProcessedAt,
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithRequestTypeAtSystemValue (final String subscriptionKey,
                                                                                    final String requestType) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                requestType
        );
    }

    public static Map<String,String> createHeaderWithContentTypeAtSystemValue (final String subscriptionKey,
                                                                               final String contentType) {

        return buildHeaderWithValues(contentType,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithAcceptTypeAtSystemValue (final String subscriptionKey,
                                                                               final String acceptType) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                acceptType,
                subscriptionKey,
                "2012-03-19T07:22:00Z",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static final Map<String,String> createHeaderWithDeprecatedHeaderValue(final String subscriptionKey,
                                                                                 final String deprecatedHeaderKey,
                                                                                 final String deprecatedHeaderVal
    ) {
        //Set invalid value for specific header key
        final String acceptType = deprecatedHeaderKey.equalsIgnoreCase("X-Accept")?MediaType.APPLICATION_PDF_VALUE:MediaType.APPLICATION_JSON_VALUE;
        final String sourceSystem = deprecatedHeaderKey.equalsIgnoreCase("X-Source-System")?"S&L":"CFT";
        final String destinationSystem = deprecatedHeaderKey.equalsIgnoreCase("X-Destination-System")?"CFT":"S&L";
        final String requestType = deprecatedHeaderKey.equalsIgnoreCase("X-Request-Type")?"Robbery":"Assault";
        final String requestCreatedAt = deprecatedHeaderKey.equalsIgnoreCase("X-Request-Created-At")?"2002-10-02T15:00:00*05Z":"2012-03-19T07:22:00Z";
        final String requestProcessedAt = deprecatedHeaderKey.equalsIgnoreCase("X-Request-Processed-At")?"2002-10-02T15:00:00*05Z":"2012-03-19T07:22:00Z";

        Map<String,String> headers = convertToMapWithMandatoryHeaders(buildStandardSytemHeaderPart(
                MediaType.APPLICATION_JSON_VALUE,
                acceptType,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart(requestCreatedAt,
                        requestProcessedAt,
                        sourceSystem,
                        destinationSystem,
                        requestType));
        headers.put(deprecatedHeaderKey, deprecatedHeaderVal);
        return Collections.unmodifiableMap(headers);
    }

    private static Map<String,String> buildHeaderWithValues(final String contentType,
                                                            final String acceptType,
                                                            final String subscriptionKey,
                                                            final String requestCreatedDate,
                                                            final String requestProcessedAt,
                                                            final String sourceSystem,
                                                            final String destinationSystem,
                                                            final String requestType) {
        return Collections.unmodifiableMap(convertToMapWithMandatoryHeaders(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        requestProcessedAt,
                        sourceSystem,
                        destinationSystem,
                        requestType)));
    }

    private static Map<String,String> buildHeaderWithValues(final String contentType,
                                                            final String acceptType,
                                                            final String subscriptionKey,
                                                            final String cacheControl,
                                                            final String contentEncoding,
                                                            final String requestCreatedDate,
                                                            final String requestProcessedAt,
                                                            final String sourceSystem,
                                                            final String destinationSystem,
                                                            final String requestType) {
        return Collections.unmodifiableMap(convertToMapWithAllHeaders(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                contentEncoding,
                subscriptionKey,
                cacheControl),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        requestProcessedAt,
                        sourceSystem,
                        destinationSystem,
                        requestType)));
    }

    private static Map<String,String> buildHeaderWithValuesWithKeysTruncated(final String contentType,
                                                                             final String acceptType,
                                                                             final String subscriptionKey,
                                                                             final String requestCreatedDate,
                                                                             final String requestProcessedAt,
                                                                             final String sourceSystem,
                                                                             final String destinationSystem,
                                                                             final String requestType,
                                                                             List<String> headersToTruncate) {
        return Collections.unmodifiableMap(convertToMapAfterTruncatingHeaderKey(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        requestProcessedAt,
                        sourceSystem,
                        destinationSystem,
                        requestType),headersToTruncate));

    }

    private static Map<String,String> buildHeaderWithValuesWithKeysRemoved(final String contentType,
                                                                             final String acceptType,
                                                                             final String subscriptionKey,
                                                                             final String requestCreatedDate,
                                                                             final String requestProcessedAt,
                                                                             final String sourceSystem,
                                                                             final String destinationSystem,
                                                                             final String requestType,
                                                                             List<String> headersToBeRemoved) {
        return Collections.unmodifiableMap(convertToMapAfterHeadersRemoved(buildStandardSytemHeaderPart(
                contentType,
                acceptType,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart(requestCreatedDate,
                        requestProcessedAt,
                        sourceSystem,
                        destinationSystem,
                        requestType),headersToBeRemoved));

    }

}
