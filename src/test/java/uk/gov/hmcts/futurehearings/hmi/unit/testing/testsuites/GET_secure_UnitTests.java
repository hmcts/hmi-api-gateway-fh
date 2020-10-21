package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter.getObjStep;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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

@Slf4j
@SpringBootTest(classes = { Application.class })
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("GET /Secure - Retrieve Secure")
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

	@Value("${expiredAccessToken}")
	private String expiredAccessToken;

	@Value("${invalidTokenURL}")
	private String invalidTokenURL;

	@Value("${invalidScope}")
	private String invalidScope;

	@Test
	@Order(1)
	@DisplayName("Test for Valid OAuth Token")
	void testSecureRequestForValidOAuthToken() {

		accessToken = TestUtilities.getToken(grantType, clientID, clientSecret, tokenURL, scope);
		Response response = given()
				.auth()
				.oauth2(accessToken)
				.header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
				.baseUri(targetInstance)
				.basePath(secureApiRootContext)
				.when()
				.get();

		validateOauthResponse(response);
	}

	@Test
	@Order(2)
	@DisplayName("Test for Invalid Role in OAuth Token")
	void testSecureRequestForInvalidOAuthTokenRole() {

		accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);

		Response response = given()
				.auth()
				.oauth2(accessToken)
				.header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
				.baseUri(targetInstance)
				.basePath(secureApiRootContext)
				.when()
				.get();

		validateInvalidOauthResponse(response);
	}

	@Test
	@Order(3)
	@DisplayName("Test for Invalid OAuth Token")
	void testSecureRequestForInvalidOAuthToken() {

		Response response = given()
				.auth()
				.oauth2("accessToken")
				.header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
				.baseUri(targetInstance)
				.basePath(secureApiRootContext)
				.when()
				.get();

		validateInvalidOauthResponse(response);
	}

	@Test
	@Order(4)
	@DisplayName("Test for expired OAuth Token")
	void testSecureRequestForExpiredOAuthToken() {

		Response response = given()
				.auth()
				.oauth2(expiredAccessToken)
				.header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
				.baseUri(targetInstance)
				.basePath(secureApiRootContext)
				.when()
				.get();

		validateInvalidOauthResponse(response);
	}

	@Test
	@Order(5)
	@DisplayName("Test for No OAuth Token")
	void testSecureRequestForNoOAuthToken() {

		Response response = given()
				.header("Ocp-Apim-Subscription-Key", targetSubscriptionKey)
				.baseUri(targetInstance)
				.basePath(secureApiRootContext)
				.when()
				.get();

		validateInvalidOauthResponse(response);
	}

	void validateInvalidOauthResponse(Response response) {
		Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
		assertEquals(401, response.getStatusCode(), "Response Code Validation:");
		getObjStep().pass("Got the expected response code: 401");
		assertEquals("Access denied due to invalid OAuth information", responseMap.get(("message")),
				"Response Code Description Validation:");
		getObjStep().pass("Got the expected description: " + responseMap.get(("message")));
	}

	void validateOauthResponse(Response response) {
		Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
		assertEquals(200, response.getStatusCode(), "Response Code Validation:");
		getObjStep().pass("Got the expected response code: 200");
		assertEquals("Hello", responseMap.get(("message")), "Response Code Description Validation:");
		getObjStep().pass("Got the expected description: " + responseMap.get(("message")));
	}
}
