package uk.gov.hmcts.futurehearings.hmi.acceptance.listings;

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
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HmiCommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.success.HmiCommonSuccessVerifier;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses(DeleteListingsValidationTest.class)
@IncludeTags("Delete")
@SuppressWarnings({"PMD.TestClassWithoutTestCases"})
class DeleteListingsValidationTest extends ListingsValidationTest {

    @Value("${listings_idRootContext}")
    private String listingsIdRootContext;

    @BeforeAll
    @Override
    public void initialiseValues() throws Exception {
        super.initialiseValues();
        listingsIdRootContext = String.format(listingsIdRootContext, "12345");
        this.setRelativeUrl(listingsIdRootContext);
        this.setHttpMethod(HttpMethod.DELETE);
        this.setInputPayloadFileName("delete-listings-request-valid.json");
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new HmiCommonSuccessVerifier());
        this.setHmiErrorVerifier(new HmiCommonErrorVerifier());
    }
}
