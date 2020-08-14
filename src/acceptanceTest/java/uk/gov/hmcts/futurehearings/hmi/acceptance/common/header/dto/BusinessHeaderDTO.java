package uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
public class BusinessHeaderDTO {
    private String destination;
    private String companyName;
    private String source;
    private String dateTime;
    private String requestType;
}
