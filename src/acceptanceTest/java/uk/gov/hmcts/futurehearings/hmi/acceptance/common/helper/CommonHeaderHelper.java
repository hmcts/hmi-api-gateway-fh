package uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMap;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterHeadersRemoved;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMapAfterTruncatingHeaderKey;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;

public class CommonHeaderHelper {


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

    private static Map<String,String> buildHeaderWithValues(final String contentType,
                                                            final String acceptType,
                                                            final String subscriptionKey,
                                                            final String requestCreatedDate,
                                                            final String requestProcessedAt,
                                                            final String sourceSystem,
                                                            final String destinationSystem,
                                                            final String requestType) {
        return Collections.unmodifiableMap(convertToMap(buildStandardSytemHeaderPart(
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
        //buildHeaderWithValuesWithKeysTruncated
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
