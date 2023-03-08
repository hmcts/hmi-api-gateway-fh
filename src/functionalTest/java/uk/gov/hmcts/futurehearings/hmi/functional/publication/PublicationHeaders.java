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
public class PublicationHeaders {

    protected void setPnIMandatoryHeaders(Map<String, Object> headersAsMap,
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

    protected void setPnIAdditionalHeaders(Map<String, Object> headersAsMap,
                                           String sourceArtefactIdValue,
                                           String sensitivityValue,
                                           String displayFromValue,
                                           String displayToValue) {
        headersAsMap.put("x-source-artefact-id", sourceArtefactIdValue);
        headersAsMap.put("x-sensitivity", sensitivityValue);
        headersAsMap.put("x-display-from", displayFromValue);
        headersAsMap.put("x-display-to", displayToValue);

    }

    protected void setAHeader(Map<String, Object> headersAsMap,
                              String headerKey,
                              String headerValue) {
        headersAsMap.put(headerKey, headerValue);
    }
}
