package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidResource;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAcceptHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidContentTypeHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingOrInvalidAccessToken;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForDelete;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForInvalidSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.HearingsResponseVerifier.thenValidateResponseForMissingSubscriptionKeyHeader;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = { Application.class })
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestReporter.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("DELETE /hearings - Delete Hearings")
@SuppressWarnings("java:S2699")
class DELETE_hearings_UnitTests {

	private static final String CORRECT_DELETE_REQUEST_PAYLOAD = "requests/delete-request-payload.json";

	@Value("${targetInstance}")
	private String targetInstance;

	@Value("${targetSubscriptionKey}")
	private String targetSubscriptionKey;

	@Value("${hearingApiRootContext}")
	private String hearingApiRootContext;

	@Value("${destinationSystem}")
	private String destinationSystem;

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

	private final Map<String, Object> headersAsMap = new HashMap<>();

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
	@DisplayName("Test for Invalid Resource")
	void testDeleteHearingRequestForInvalidResource() throws Exception {
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedForInvalidResource(input);
		thenValidateResponseForInvalidResource(response);
	}

	@Test
	@Order(2)
	@DisplayName("Test for missing ContentType header")
	void testDeleteHearingRequestWithMissingContentTypeHeader() throws IOException {
		headersAsMap.remove("Content-Type");
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
	}

	@Test
	@Order(3)
	@DisplayName("Test for invalid ContentType header")
	void testDeleteHearingRequestWithInvalidContentTypeHeader() throws IOException {
		headersAsMap.remove("Content-Type");
		headersAsMap.put("Content-Type", "application/xml");
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
	}

	@Test
	@Order(4)
	@DisplayName("Test for missing Accept header")
	void testDeleteHearingRequestWithMissingAcceptHeader() throws IOException {
		headersAsMap.remove("Accept");
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForMissingOrInvalidAcceptHeader(response);
	}

	@Test
	@Order(5)
	@DisplayName("Test for invalid Accept header")
	void testDeleteHearingRequestWithInvalidAcceptHeader() throws IOException {
		headersAsMap.remove("Accept");
		headersAsMap.put("Accept", "application/jsonxml");
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForMissingOrInvalidAcceptHeader(response);
	}

	@Test
	@Order(6)
	@DisplayName("Test for missing Ocp-Apim-Subscription-Key header")
	void testDeleteResourcesRequestWithMissingOcpSubKey() throws IOException {
		headersAsMap.remove("Ocp-Apim-Subscription-Key");
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForDelete(response);
	}

	@Order(8)
	@ParameterizedTest(name = "Test for missing {0} header")
	@ValueSource(strings = { "Source-System", "Destination-System", "Request-Created-At", "Request-Processed-At" })
	void testDeleteHearingRequestWithMissingHeader(String iteration) throws IOException {
		headersAsMap.remove(iteration);
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForMissingOrInvalidHeader(response, iteration);
	}

	@Order(9)
	@ParameterizedTest(name = "Test for invalid {0} header")
	@ValueSource(strings = { "Source-System", "Destination-System", "Request-Created-At", "Request-Processed-At" })
	void testDeleteHearingRequestWithInvalidHeader(String iteration) throws IOException {
		headersAsMap.remove(iteration);
		headersAsMap.put(iteration, "A");
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForMissingOrInvalidHeader(response, iteration);
	}

	@Test
	@Order(10)
	@DisplayName("Test for Correct Headers and Payload")
	void testDeleteHearingRequestWithCorrectHeaders() throws IOException {
		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithCorrectHeaders(input);
		thenValidateResponseForDelete(response);
	}

	@Test
	@Order(11)
	@DisplayName("Test for missing Access Token")
	void testDeleteHearingRequestWithMissingAccessToken() throws IOException {

		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingAccessToken(input);
		thenValidateResponseForMissingOrInvalidAccessToken(response);
	}

	@Test
	@Order(12)
	@DisplayName("Test for invalid Access Token")
	void testDeleteHearingRequestWithInvalidAccessToken() throws IOException {
		accessToken = TestUtilities.getToken(grantType, invalidClientID, invalidClientSecret, invalidTokenURL, invalidScope);

		final String input = givenAPayload(CORRECT_DELETE_REQUEST_PAYLOAD);
		final Response response = whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(input);
		thenValidateResponseForMissingOrInvalidAccessToken(response);
	}

	private Response whenDeleteHearingRequestIsInvokedForInvalidResource(final String input) {
		return deleteHearingsResponseForInvalidResource(hearingApiRootContext + "delete", headersAsMap, targetInstance, input);
	}

	private Response whenDeleteHearingRequestIsInvokedWithCorrectHeaders(final String input) {
		return deleteHearingsResponseForCorrectHeaders(hearingApiRootContext, headersAsMap, targetInstance, input);
	}

	private Response whenDeleteHearingRequestIsInvokedWithMissingAccessToken(final String input) {
		return deleteHearingsResponseForMissingAccessToken(hearingApiRootContext, headersAsMap, targetInstance, input);
	}

	private Response whenDeleteHearingRequestIsInvokedWithMissingOrInvalidHeader(final String input) {
		return deleteHearingsResponseForMissingOrInvalidHeader(hearingApiRootContext, headersAsMap, targetInstance, input);
	}

	private String givenAPayload(final String path) throws IOException {
		return readFileContents(path);
	}

	private Response deleteHearingsResponseForInvalidResource(final String api, final Map<String, Object> headersAsMap,
			final String basePath, final String payloadBody) {

		return given()
				.auth()
				.oauth2(accessToken)
				.body(payloadBody)
				.headers(headersAsMap)
				.baseUri(basePath)
				.basePath(api)
				.when().delete().then().extract().response();
	}

	private Response deleteHearingsResponseForCorrectHeaders(final String api, final Map<String, Object> headersAsMap,
			final String basePath, final String payloadBody) {

		return given()
				.auth()
				.oauth2(accessToken)
				.body(payloadBody)
				.headers(headersAsMap)
				.baseUri(basePath)
				.basePath(api)
				.when().delete().then().extract().response();
	}

	private Response deleteHearingsResponseForMissingAccessToken(final String api, final Map<String, Object> headersAsMap,
			final String basePath, final String payloadBody) {

		return given()
				.body(payloadBody)
				.headers(headersAsMap)
				.baseUri(basePath)
				.basePath(api)
				.when().delete().then().extract().response();
	}

	private Response deleteHearingsResponseForMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath, final String payloadBody) {

        return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().delete().then().extract().response();

    }
}
