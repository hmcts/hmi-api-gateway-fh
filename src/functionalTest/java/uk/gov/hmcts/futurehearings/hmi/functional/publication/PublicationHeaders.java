package uk.gov.hmcts.futurehearings.hmi.functional.publication;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Builder
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@SuppressWarnings({"HideUtilityClassConstructor"})
public final class PublicationHeaders { //NOSONAR

    public static void setPnIMandatoryHeaders(Map<String, Object> headersAsMap,
                                          String typeValue,
                                          String listTypeValue,
                                          String courtIdValue,
                                          String contentDateValue,
                                          String languageValue) {
        headersAsMap.put("x-type", typeValue);
        headersAsMap.put("x-list-type", listTypeValue);
        headersAsMap.put("x-court-id", courtIdValue);
        headersAsMap.put("x-content-date", contentDateValue);
        headersAsMap.put("x-language", languageValue);
    }

    public static void setPnIAdditionalHeaders(Map<String, Object> headersAsMap,
                                           String sensitivityValue,
                                           String displayFromValue,
                                           String displayToValue) {
        headersAsMap.put("x-sensitivity", sensitivityValue);
        headersAsMap.put("x-display-from", displayFromValue);
        headersAsMap.put("x-display-to", displayToValue);

    }

    public static void setAHeader(Map<String, Object> headersAsMap,
                              String headerKey,
                              String headerValue) {
        headersAsMap.put(headerKey, headerValue);
    }
}
