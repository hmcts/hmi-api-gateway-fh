package uk.gov.hmcts.futurehearings.hmi.unit.testing.testsuites;

import static io.restassured.RestAssured.given;

import java.io.IOException;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ElinksResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@Slf4j
@SpringBootTest(classes = { Application.class })
@ActiveProfiles("local")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PUT /people - Update People")
class PUT_people_UnitTests {

	private static final String CORRECT_UPDATE_PEOPLE_PAYLOAD = "requests/update-resources-location-payload.json";

	@Value("${targetInstance}")
	private String targetInstance;

	@Value("${targetSubscriptionKey}")
	private String targetSubscriptionKey;

	@Value("${elinksApiRootContext}")
	private String elinksApiRootContext;

	@Value("${destinationSystem}")
	private String destinationSystem;

	private final Map<String, Object> headersAsMap = new HashMap<>();

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
		final Response response = whenUpdatePeopleRequestIsInvokedForWithPathParam();
		thenValidateResponseForUpdatePeopleById(response);
	}
	/**
	 * Missing Content-Type header retruns specific response
	 * @throws IOException
	 */
    @Test
    @Order(2)
    @DisplayName("Test for missing ContentType header")
    void testpdatePeopleWithMissingContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }
    @Test
    @Order(3)
    @DisplayName("Test for invalid ContentType header")
    void testUpdatePeopleWithInvalidContentTypeHeader() throws IOException {
        headersAsMap.remove("Content-Type");
        headersAsMap.put("Content-Type", "application/xml");
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForMissingOrInvalidContentTypeHeader(response);
    }

    @Test
    @Order(4)
    @DisplayName("Test for missing Accept header")
    void testUpdatePeopleWithMissingAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(5)
    @DisplayName("Test for invalid Accept header")
    void testUpdatePeopleWithInvalidAcceptHeader() throws IOException {
        headersAsMap.remove("Accept");
        headersAsMap.put("Accept", "application/jsonxml");
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForMissingOrInvalidAcceptHeader(response);
    }

    @Test
    @Order(6)
    @DisplayName("Test for missing OcpSubKey")
    void testUpdatePopleRequestWithMissingOcpSubKey() throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForMissingSubscriptionKeyHeader(response);
    }

    @Test
    @Order(7)
    @DisplayName("Test for invalid Ocp-Apim-Subscription-Key header")
    void testUpdatePeopleRequestWithInvalidOcpSubKey()throws IOException {
        headersAsMap.remove("Ocp-Apim-Subscription-Key");
        headersAsMap.put("Ocp-Apim-Subscription-Key","invalidocpsubkey");
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForInvalidSubscriptionKeyHeader(response);
    }

	@Order(8)
    @ParameterizedTest(name = "Test for missing madatory {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At","Request-Processed-At","Request-Type"})
    void testUpdatePeopleWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

	@Test
    @Order(9)
    @DisplayName("Test for Invalid AccessToken for Update People with ID")
    void testRetrievePeopleWithInvalidAccessToken() {
        final Response response = whenUpdatePeopleRequestIsInvokedForWithInvalidAcessToken();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
	}

	private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
	}
	
    private Response whenUpdatePeopleIsInvoked(final String input) {
        return updatePeopleResponseForAMissingOrInvalidHeader(elinksApiRootContext + "/PID012", headersAsMap, targetInstance, input);
    }
	
	private Response whenUpdatePeopleRequestIsInvokedForWithPathParam() {
		return updatePeopleForValidId(elinksApiRootContext + "/PID012", headersAsMap, targetInstance);
	}

	private Response whenUpdatePeopleRequestIsInvokedForWithInvalidAcessToken() {
        return updatePeopleForValidIdWithInvalidAccessToken(elinksApiRootContext + "/PID012", headersAsMap, targetInstance);
    }
	
    private Response whenUpdatePeopleRequestIsInvokedWithAnInvalidToken() {
        return updatePeopleWithInvalidToken(elinksApiRootContext, headersAsMap, targetInstance);
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
	
    private Response updatePeopleWithInvalidToken(final String api, final Map<String, Object> headersAsMap, 
			final String basePath) {
        return given()
                .auth()
                .oauth2("accessToken")
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
	}
	
	private Response updatePeopleResponseForAMissingOrInvalidHeader(final String api, final Map<String, Object> headersAsMap, final String basePath,  final String payloadBody) {
		log.info("Calling: {}{}", basePath, api);
		return given()
                .auth()
                .oauth2(accessToken)
                .body(payloadBody)
                .headers(headersAsMap)
                .baseUri(basePath)
                .basePath(api)
                .when().put().then().extract().response();
    }
}
