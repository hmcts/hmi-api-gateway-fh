package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HmiCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HmiCommonSuccessVerifier;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2187")
class DeleteSessionsValidationTest extends SessionsValidationTest {

    @Value("${sessions_idRootContext}")
    private String sessionsIdRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        sessionsIdRootContext = String.format(sessionsIdRootContext, "12345");
        this.setRelativeUrl(sessionsIdRootContext);
        this.setHttpMethod(HttpMethod.DELETE);
        this.setInputPayloadFileName("delete-sessions-request-valid.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new HmiCommonSuccessVerifier());
        this.setHmiErrorVerifier(new HmiCommonErrorVerifier());
    }
}