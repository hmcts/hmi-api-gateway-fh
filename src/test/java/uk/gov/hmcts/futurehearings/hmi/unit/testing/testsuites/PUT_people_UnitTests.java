package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ElinksResponseVerifier.*;

@Slf4j
@SpringBootTest(classes = { Application.class })
@ActiveProfiles("local")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PUT /people - Update People")
class PUT_people_UnitTests {

	@Value("${targetInstance}")
	private String targetInstance;

	@Value("${targetSubscriptionKey}")
	private String targetSubscriptionKey;

	@Value("${elinksApiRootContext}")
	private String elinksApiRootContext;

	@Value("${destinationSystem}")
	private String destinationSystem;

	private final Map<String, Object> headersAsMap = new HashMap<>();
	private final Map<String, Object> queryParams = new HashMap<>();

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

	private static String accessToken;

	@Value("${invalidTokenURL}")
	private String invalidTokenURL;

	@Value("${invalidScope}")
	private String invalidScope;

	@Value("${invalidClientID}")
	private String invalidClientID;

	@Value("${invalidClientSecret}")
	private String invalidClientSecret;

	@BeforeAll
	void setToken() {
		accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
	}

	@BeforeEach
	void initialiseValues() {
		headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
		headersAsMap.put("Content-Type", "application/json");
		headersAsMap.put("Accept", "application/json");
		headersAsMap.put("Source-System", "CFT");
		headersAsMap.put("Destination-System", destinationSystem);
		headersAsMap.put("Request-Type", "THEFT");
		headersAsMap.put("Request-Created-At", "2018-01-29 20:36:01Z");
		headersAsMap.put("Request-Processed-At", "2018-02-29 20:36:01Z");
	}

	@Test
	@Order(1)
	@DisplayName("Test for Valid Path Param")
	void testRetrievePeopleForValidPathParams() {
		final Response response = whenGetPeopleRequestIsInvokedForWithPathParam();
		thenValidateResponseForUpdatePeopleById(response);
	}

	@Test
    @Order(2)
    @DisplayName("Test for Invalid Header for Update People with ID")
    void testRetrievePeopleForInvalidHeader() {
        headersAsMap.remove("Request-Created-At");
        headersAsMap.put("Request-Created-At", "invalid date");
        final Response response = whenGetPeopleRequestIsInvokedForWithPathParam();
        thenValidateResponseForUpdatePeopleByIdWithInvalidHeader(response);
    }

	@Test
    @Order(3)
    @DisplayName("Test for Invalid AccessToken for Update People with ID")
    void testRetrievePeopleForInvalidAccessToken() {
        final Response response = whenUpdatePeopleRequestIsInvokedForWithInvalidAcessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
	}

    @Test
    @Order(4)
    @DisplayName("Test for Invalid AccessToken for Update People with ID")
    void testRetrievePeopleWithParamsForInvalidAccessToken() {
        final Response response = whenUpdatePeopleRequestIsInvokedWithAnInvalidToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
	}
	
	private Response whenGetPeopleRequestIsInvokedForWithPathParam() {
		return updatePeopleForValidId(elinksApiRootContext + "/PID012", headersAsMap, targetInstance);
	}

	private Response whenUpdatePeopleRequestIsInvokedForWithInvalidAcessToken() {
        return updatePeopleForValidIdWithInvalidAccessToken(elinksApiRootContext + "/PID012", headersAsMap, targetInstance);
    }
	
    private Response whenUpdatePeopleRequestIsInvokedWithAnInvalidToken() {
        return updatePeopleWithInvalidToken(elinksApiRootContext, headersAsMap, queryParams, targetInstance);
	}
	
	private Response updatePeopleForValidId(final String api, final Map<String, Object> headersAsMap, final String basePath) {
		return given()
				.auth()
				.oauth2(accessToken)
				.headers(headersAsMap)
				.baseUri(basePath)
				.basePath(api)
				.when().get().then().extract().response();
	}

	private Response updatePeopleForValidIdWithInvalidAccessToken(final String api, final Map<String, Object> headersAsMap, final String basePath) {
        return given()
                .auth()
                .oauth2("accessToken")
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
	}
	
    private Response updatePeopleWithInvalidToken(final String api, final Map<String, Object> headersAsMap, final Map<String, Object> queryParams, final String basePath) {
        return given()
                .auth()
                .oauth2("accessToken")
                .headers(headersAsMap)
                .queryParams(queryParams)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
    }
}
