package uk.gov.hmcts.futurehearings.hmi.acceptance.schedules;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("acceptance")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("As we do not have the Wiremock Infrastructure in place till now - TODO -Tests and CodeQualityReview Processes")
class ScheduleInteractionTest {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/schedule/input";

    private WireMock wireMock;

    @Value("${mockServerHost}")
    private String mockServerHost;

    @Value("${mockServerPort}")
    private String mockServerPort;

    @Value("${targetInstance}")
    private String targetInstance;

    @BeforeAll
    public void initialiseValues() {
        RestAssured.baseURI = targetInstance;
        RestAssured.useRelaxedHTTPSValidation();
        wireMock = new WireMock(mockServerHost, Integer.parseInt(mockServerPort));
        wireMock.resetRequests();
    }

    @Test
    void should_work_from_standalone_mock() throws Exception {

        log.debug("The value of the base URI" + RestAssured.baseURI );
        try {
            wireMock.stubFor(get(urlEqualTo("/product/xxx"))
                    //.withHeader("Content-Type", equalTo(contentType))
                    .willReturn(aResponse()
                    .withStatus(200)
                            //.withHeader("Content-Type", contentType)
                            .withBody(readFileContents(INPUT_FILE_PATH+"/mock-demo-request.json"))));
            assertTrue(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Response response = expect().that().statusCode(200)
                .given().contentType("application/json")
                //.body(readFileContents(INPUT_FILE_PATH + "/employee-demo-request.json"))
                //.headers(headersAsMap)
                //.baseUri("http://localhost:4550")
                .basePath("/employee")
                .queryParam("id","3")
                .when().get().then().extract().response();
       */

        //wireMock.verify(1, getRequestedFor(urlEqualTo("/product/p0001")));
        //wireMock.verify(1, getRequestedFor(urlEqualTo("/product/p0001")));

        // When
        //LocalTime time = response.getTime("cet");
        /*System.out.println(response.getBody().asString());
        assertEquals(4,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("Tuesday",responseMap.get(("firstName")));*/

    }
}
