package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import static io.restassured.RestAssured.expect;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /Secure - Retrieve Secure")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("java:S2699")
class GET_secure_UnitTests {

        @Value("${tokenURL}")
        private String tokenURL;

        @Value("${clientID}")
        private String clientID;

        @Value("${clientSecret}")
        private String clientSecret;

        @Value("${scope}")
        private String scope;

        @Value("${grantType}")
        private String grantType;

        @Value("${invalidClientID}")
        private String invalidClientID;

        @Value("${invalidClientSecret}")
        private String invalidClientSecret;

        private static String accessToken;

        @Value("${targetInstance}")
        private String targetInstance;

        @Value("${targetSubscriptionKey}")
        private String targetSubscriptionKey;

        @Value("${secureApiRootContext}")
        private String secureApiRootContext;

        @Test
        @Order(1)
        @DisplayName("Test for Valid OAuth Token")
        void testSecureRequestForValidOAuthToken() {

            accessToken = TestUtilities.getToken(grantType,clientID,clientSecret,tokenURL,scope);
            Response response =  expect().that().statusCode(200)
                                .given().auth()
                                .oauth2(accessToken)
                                .header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
                                .baseUri(targetInstance)
                                .basePath(secureApiRootContext)
                                .when().get();
        }


        @Test
        @Order(2)
        @DisplayName("Test for Invalid Role in OAuth Token")
        void testSecureRequestForInvalidOAuthTokenRole() {

                accessToken = TestUtilities.getToken(grantType,invalidClientID,invalidClientSecret,tokenURL,scope);
                Response response =  expect().that().statusCode(401)
                        .given().auth()
                        .oauth2(accessToken)
                        .header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
                        .baseUri(targetInstance)
                        .basePath(secureApiRootContext)
                        .when().get();
        }

        @Test
        @Order(3)
        @DisplayName("Test for Invalid OAuth Token")
        void testSecureRequestForInvalidOAuthToken() {

                accessToken = TestUtilities.getToken(grantType,invalidClientID,invalidClientSecret,tokenURL,scope);
                Response response =  expect().that().statusCode(401)
                        .given().auth()
                        .oauth2("accessToken")
                        .header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
                        .baseUri(targetInstance)
                        .basePath(secureApiRootContext)
                        .when().get();
        }

}
