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
import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestReporter;
import uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.ElinksResponseVerifier.*;
import static uk.gov.hmcts.futurehearings.hmi.unit.testing.util.TestUtilities.readFileContents;

@SpringBootTest(classes = { Application.class })
@ActiveProfiles("test")
@ExtendWith(TestReporter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PUT /people - Update People")
class PUT_people_UnitTests {

	private static final String CORRECT_UPDATE_PEOPLE_PAYLOAD = "requests/update-people-payload.json";

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
		headersAsMap.put("Content-Type", "application/json");
		headersAsMap.put("Accept", "application/json");
		headersAsMap.put("Source-System", "CFT");
		headersAsMap.put("Destination-System", destinationSystem);
		headersAsMap.put("Request-Type", "THEFT");
		headersAsMap.put("Request-Created-At", "2018-01-29T20:36:01Z");
	}

	@Test
	@Order(1)
	@DisplayName("Test for Valid People Update")
	void testRetrievePeopleForValidPathParams() throws IOException {
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
		thenValidateResponseForUpdate(response);
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

	@Order(6)
    @ParameterizedTest(name = "Test for missing madatory {0} header")
    @ValueSource(strings = {"Source-System","Destination-System","Request-Created-At"})
    void testUpdatePeopleWithMissingHeader(String iteration) throws IOException {
        headersAsMap.remove(iteration);
        final String input = givenAPayload(CORRECT_UPDATE_PEOPLE_PAYLOAD);
        final Response response = whenUpdatePeopleIsInvoked(input);
        thenValidateResponseForMissingOrInvalidHeader(response, iteration);
    }

	@Test
    @Order(7)
    @DisplayName("Test for Invalid AccessToken for Update People with ID")
    void testRetrievePeopleWithInvalidAccessToken() {
		accessToken = "invalidToken";
        final Response response = whenUpdatePeopleRequestNoPayloadIsInvoked();
        thenValidateResponseForMissingOrInvalidAccessToken(response);
	}

	private String givenAPayload(final String path) throws IOException {
        return readFileContents(path);
	}
	
    private Response whenUpdatePeopleIsInvoked(final String input) {
        return updatePeopleWithPayload(elinksApiRootContext + "/2959d456-cee3-4011-bf16-e028025ca775", headersAsMap, targetInstance, input);
    }
	
	private Response whenUpdatePeopleRequestNoPayloadIsInvoked() {
		return updatePeopleNoPayload(elinksApiRootContext + "/2959d456-cee3-4011-bf16-e028025ca775", headersAsMap, targetInstance);
	}
	
	private Response updatePeopleNoPayload(final String api, final Map<String, Object> headersAsMap, final String basePath) {
		return given()
				.auth()
				.oauth2(accessToken)
				.headers(headersAsMap)
				.baseUri(basePath)
				.basePath(api)
				.when().get().then().extract().response();
	}
	
	private Response updatePeopleWithPayload(final String api, final Map<String, Object> headersAsMap, final String basePath,  final String payloadBody) {
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
