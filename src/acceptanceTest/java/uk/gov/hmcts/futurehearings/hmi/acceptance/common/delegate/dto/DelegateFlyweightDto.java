package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto;

import io.restassured.http.Headers;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HmiVerifier;

import java.util.Map;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Builder
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public class DelegateFlyweightDto implements DelegateDto {

    private static final long serialVersionUID = 1L;

    private String targetSubscriptionKey;
    private String authorizationToken;
    private String targetUrl;
    private String inputFile;
    private Map<String, String> standardHeaderMap;
    private Headers headers;
    private Map<String, String> params;
    private HttpMethod httpMethod;
    private HttpStatus status;
    private String inputFileDirectory;
    private HmiVerifier hmiVerifier;
    private String expectedMessage;
}
