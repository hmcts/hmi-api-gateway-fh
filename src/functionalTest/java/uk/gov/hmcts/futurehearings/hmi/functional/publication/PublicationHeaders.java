package uk.gov.hmcts.futurehearings.hmi.functional.publication;

import java.util.Map;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Builder
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public class PublicationHeaders {

    protected void setPnIMandatoryHeaders(Map<String, Object> headersAsMap,
                                          String typeValue,
                                          String list_typeValue,
                                          String court_idValue,
                                          String content_dateValue,
                                          String languageValue) {
        headersAsMap.put("x-type", typeValue);
        headersAsMap.put("x-list-type", list_typeValue);
        headersAsMap.put("x-court-id", court_idValue);
        headersAsMap.put("x-content-date", content_dateValue);
        headersAsMap.put("x-language", languageValue);
    }

    protected void setPnIAdditionalHeaders(Map<String, Object> headersAsMap,
                                           String source_artefact_idValue,
                                           String x_sensitivityValue,
                                           String display_fromValue,
                                           String display_toValue) {
        headersAsMap.put("x-source-artefact-id", source_artefact_idValue);
        headersAsMap.put("x-sensitivity", x_sensitivityValue);
        headersAsMap.put("x-display-from", display_fromValue);
        headersAsMap.put("x-display-to", display_toValue);

    }

    protected void setAHeader(Map<String, Object> headersAsMap,
                              String headerKey,
                              String headerValue) {
        headersAsMap.put(headerKey, headerValue);
    }
}
