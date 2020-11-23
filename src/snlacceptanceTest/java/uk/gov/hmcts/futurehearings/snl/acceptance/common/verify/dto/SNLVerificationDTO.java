package uk.gov.hmcts.futurehearings.snl.acceptance.common.verify.dto;

import uk.gov.hmcts.futurehearings.snl.acceptance.common.dto.SNLDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Builder
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SNLVerificationDTO implements SNLDto {

    public HttpStatus httpStatus;
    public String errorCode;
    public String errorDescription;
    public String errorLinkID;
}
