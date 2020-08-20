package uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardBuinessHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.buildStandardSytemHeaderPart;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory.PayloadHeaderDTOFactory.convertToMap;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.MediaType;

public class HearingHeaderHelper {


    public static final Map<String,String> createStandardPayloadHeader (final String targetSubscriptionKey) {

        return Collections.unmodifiableMap(
                convertToMap(
                        buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                                MediaType.APPLICATION_JSON_VALUE,
                                null,
                                null,
                                targetSubscriptionKey,
                                null),

                        buildStandardBuinessHeaderPart("dateTime",
                                "dateTime",
                                "CFT",
                                "S&L",
                                "THEFT")));
    }

    public static final Map<String,String> createHeaderWithAllValuesNull (final String targetSubscriptionKey) {

        return Collections.unmodifiableMap(
                convertToMap(
                        buildStandardSytemHeaderPart(null,
                                null,
                                null,
                                null,
                                null,
                                null),

                        buildStandardBuinessHeaderPart(null,
                                null,
                                null,
                                null,
                                null)));
    }

    public static final Map<String,String> createHeaderWithAllValuesEmpty (final String targetSubscriptionKey) {

        return Collections.unmodifiableMap(
                convertToMap(
                        buildStandardSytemHeaderPart("",
                                "",
                                "",
                                "",
                                "",
                                ""),

                        buildStandardBuinessHeaderPart("",
                                "",
                                "",
                                "",
                                "")));
    }

    public static Map<String,String> createHeaderWithSourceSystemValueAsCFT (final String subscriptionKey) {

        return convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart("Qwerty",
                        "sampledate",
                        "CFT",
                        "S&L",
                        "Assault"));
    }

    public static Map<String,String> createHeaderWithNullRequestCreatedAt (final String subscriptionKey) {

        return convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart(null,
                        "sampledate",
                        "SnL",
                        "CFT",
                        "SomeString"));
    }

    public static Map<String,String> createHeaderWithEmptyRequestCreatedAt (final String subscriptionKey) {

        return convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart("",
                        "sampledate",
                        "SnL",
                        "CFT",
                        "SomeString"));
    }

    public static Map<String,String> createHeaderWithSpacedRequestCreatedAt (final String subscriptionKey) {

        return convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart(" ",
                        "sampledate",
                        "SnL",
                        "CFT",
                        "SomeString"));
    }

    public static Map<String,String> createHeaderWithSingleCharRequestCreatedAt (final String subscriptionKey) {

        return convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                subscriptionKey,
                null),
                buildStandardBuinessHeaderPart("1",
                        "sampledate",
                        "SnL",
                        "CFT",
                        "SomeString"));
    }

    public static Map<String,String> createHeaderWithLongRequestCreatedAt (final String subscriptionKey) {

        return convertToMap(buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                                        null,
                                    null,
                                    null,
                                    subscriptionKey,
                                        null),
        buildStandardBuinessHeaderPart("QwertysampledateQwertysampledateQwertysampledateQwerty",
                                        "sampledate",
                                "SnL",
                "CFT",
                "SomeString"));
    }
}
