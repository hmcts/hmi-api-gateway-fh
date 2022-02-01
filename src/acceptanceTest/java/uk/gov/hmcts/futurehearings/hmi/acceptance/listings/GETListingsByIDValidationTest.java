package uk.gov.hmcts.futurehearings.hmi.acceptance.listings;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.error.HMICommonErrorVerifier;
import uk.gov.hmcts.futurehearings.hmi.acceptance.listings.verify.GETListingsByIdValidationVerifier;

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
class GETListingsByIDValidationTest extends ListingsValidationTest {

    @Value("${listings_idRootContext}")
    private String listings_idRootContext;

    @BeforeAll
    public void initialiseValues() throws Exception{
        super.initialiseValues();
        listings_idRootContext = String.format(listings_idRootContext,"12345");
        this.setRelativeURL(listings_idRootContext);
        this.setHttpMethod(HttpMethod.GET);
        this.setHttpSuccessStatus(HttpStatus.OK);
        this.setHmiSuccessVerifier(new GETListingsByIdValidationVerifier());
        this.setHmiErrorVerifier(new HMICommonErrorVerifier());
    }
}
