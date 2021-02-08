package uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.test;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.header.factory.HeaderFactory.createStandardSnLHeader;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.security.OAuthTokenGenerator.generateOAuthTokenForSNL;
import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.security.OAuthTokenGenerator.generateOAuthTokenForVH;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public abstract class ContractTest {

    @Value("${grant_type_vh}")
    private String grantType_VH;

    @Value("${clientID_vh}")
    private String clientID_VH;

    @Value("${clientSecret_vh}")
    private String clientSecret_VH;

    @Value("${token_apiTenantId_vh}")
    private String token_apiTenantId_VH;

    @Value("${token_apiURL_vh}")
    private String token_apiURL_VH;

    @Value("${resource_vh}")
    private String resource_VH;

    @Value("${token_apiURL_snl}")
    private String token_apiURL_snl;

    @Value("${token_username_snl}")
    private String token_username_snl;

    @Value("${token_password_snl}")
    private String token_password_snl;

    protected Map<String, String> headersAsMap = new HashMap<>();
    protected String authorizationToken;

    protected String destinationSystem;

    @BeforeAll
    public void initialiseValues() throws Exception {

        if (Objects.nonNull(getDestinationSystem())
                && getDestinationSystem().trim().equals("SNL")) {

            this.authorizationToken = generateOAuthTokenForSNL(token_apiURL_snl,
                    token_username_snl,
                    token_password_snl, HttpStatus.OK);
        } else {

            this.authorizationToken = generateOAuthTokenForVH(token_apiURL_VH,
                    token_apiTenantId_VH,
                    grantType_VH,
                    clientID_VH,
                    clientSecret_VH,
                    resource_VH,
                    HttpStatus.OK);
        }
        this.setAuthorizationToken(authorizationToken);
        headersAsMap = createStandardSnLHeader(getAuthorizationToken(), getDestinationSystem());
    }
}
