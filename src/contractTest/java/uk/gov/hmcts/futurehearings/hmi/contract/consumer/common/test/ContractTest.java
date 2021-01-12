package uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.test;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.header.factory.HeaderFactory.createStandardSnLHeader;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.security.OAuthTokenGenerator.generateOAuthToken;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public abstract class ContractTest {

    @Value("${token_apiURL}")
    private String token_apiURL;

    @Value("${token_apiTenantId}")
    private String token_apiTenantId;

    @Value("${grantType}")
    private String grantType;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${scope}")
    private String scope;

    protected Map<String, String> headersAsMap = new HashMap<>();
    protected String authorizationToken;

    @BeforeEach
    public void initialiseValues() throws Exception {

        this.authorizationToken = generateOAuthToken (token_apiURL,
                token_apiTenantId,
                grantType, clientID,
                clientSecret,
                scope,
                HttpStatus.OK);
        this.setAuthorizationToken(authorizationToken);

        headersAsMap = createStandardSnLHeader(getAuthorizationToken(),"SNL");
    }
}
