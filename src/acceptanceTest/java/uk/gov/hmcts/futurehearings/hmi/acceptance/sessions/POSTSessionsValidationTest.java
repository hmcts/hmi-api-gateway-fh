package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMIUnsupportedDestinationsErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
class POSTSessionsValidationTest extends SessionsValidationTest {

    @Value("${sessionsRootContext}")
    private String sessionsRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(sessionsRootContext);
        this.setHttpMethod(HttpMethod.POST);
        this.setInputPayloadFileName("post-sessions-request-valid.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
