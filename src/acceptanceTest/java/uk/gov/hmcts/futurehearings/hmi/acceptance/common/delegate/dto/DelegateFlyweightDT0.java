package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.dto.HMIDTO;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HMIVerifier;

import java.util.Map;

import io.restassured.http.Headers;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Builder
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public class DelegateFlyweightDT0 implements DelegateDTO {

    private String targetSubscriptionKey;
    private String authorizationToken;
    private String targetURL;
    private String inputFile;
    private Map<String, String> standardHeaderMap;
    private Headers headers;
    private Map<String, String> params;
    private HttpMethod httpMethod;
    private HttpStatus status;
    private String inputFileDirectory;
    private HMIVerifier hmiVerifier;
    private String expectedMessage;
}
