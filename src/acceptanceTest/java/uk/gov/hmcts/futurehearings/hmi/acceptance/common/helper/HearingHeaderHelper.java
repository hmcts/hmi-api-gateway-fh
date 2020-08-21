package uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMap;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.MediaType;

public class HearingHeaderHelper {


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

    public static final Map<String,String> createHeaderWithAllValuesNull (final String subscriptionKey) {

         return buildHeaderWithValues(null,
                 null,
                subscriptionKey,
                 null,
                 null,
                 null,
                 null,
                 null
        );
    }

    public static final Map<String,String> createHeaderWithAllValuesEmpty (final String subscriptionKey) {

        return buildHeaderWithValues("",
                "",
                subscriptionKey,
                "",
                "",
                "",
                "",
                ""
        );
    }

    public static Map<String,String> createHeaderWithSourceSystemValueAsCFT (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithNullRequestCreatedAt (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                null,
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithEmptyRequestCreatedAt (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithSpacedRequestCreatedAt (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                " ",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithSingleCharRequestCreatedAt (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "1",
                "2012-03-19T07:22:00Z",
                "CFT",
                "S&L",
                "Assault"
        );
    }

    public static Map<String,String> createHeaderWithLongRequestCreatedAt (final String subscriptionKey) {

        return buildHeaderWithValues(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                subscriptionKey,
                "QwertysampledateQwertysampledateQwertysampledateQwerty",
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
        return Collections.unmodifiableMap(convertToMap(buildStandardSytemHeaderPart(contentType,
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
}
