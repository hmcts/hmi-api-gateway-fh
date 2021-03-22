package uk.gov.hmcts.futurehearings.hmi.acceptance.videohearings;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HMICommonSuccessVerifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(DELETEVideoHearingsValidationTest.class)
@IncludeTags("Post")
class DELETEVideoHearingsValidationTest extends VideoHearingValidationTest {

    @Value("${videohearings_idRootContext}")
    private String videohearings_idRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        this.setRelativeURL(videohearings_idRootContext);
        this.setInputPayloadFileName("delete-video-hearing-request.json");
        this.setHttpMethod(HttpMethod.DELETE);
        this.setHttpSucessStatus(HttpStatus.NO_CONTENT);
        this.setRelativeURLForNotFound(this.getRelativeURL().replace("video-hearing","video-hearings"));
        this.setHmiSuccessVerifier(new HMICommonSuccessVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
