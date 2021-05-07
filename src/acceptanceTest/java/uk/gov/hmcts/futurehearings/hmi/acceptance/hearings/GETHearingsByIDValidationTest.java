package uk.gov.hmcts.futurehearings.hmi.acceptance.hearings;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createHeaderWithEmulatorValues;

import org.junit.Ignore;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.CommonDelegate;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.CaseHQCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
class GETHearingsByIDValidationTest extends HearingValidationTest {

    @Qualifier("CommonDelegate")
    @Autowired(required = true)
    private CommonDelegate commonDelegate;

    @Value("${hearings_idRootContext}")
    private String hearings_idRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        hearings_idRootContext = String.format(hearings_idRootContext,"12345");
        this.setRelativeURL(hearings_idRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setInputPayloadFileName("hearing-request-standard.json");
        this.setHttpSucessStatus(HttpStatus.OK);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("hearings","hearing"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
