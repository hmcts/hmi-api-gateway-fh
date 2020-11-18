package uk.gov.hmcts.futurehearings.snl.acceptance.common.header.dto;

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
public class SystemHeaderDTO {
    private String subscriptionKey;
    private String accept;
    private String contentType;
    private String contentEncoding;
    private String authorization;
    private String cacheControl;
}
