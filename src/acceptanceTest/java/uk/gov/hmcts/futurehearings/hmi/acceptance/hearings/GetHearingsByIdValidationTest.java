package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

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
class GetHearingsByIdValidationTest extends HearingValidationTest {

    @Value("${hearings_idRootContext}")
    private String hearingsIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        hearingsIdRootContext = String.format(hearingsIdRootContext, "12345");
        this.setRelativeUrl(hearingsIdRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setInputPayloadFileName("hearing-request-standard.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new HmiCommonSuccessVerifier());
        this.setHmiErrorVerifier(new HmiCommonErrorVerifier());
    }
}
