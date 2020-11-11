package uk.gov.hmcts.futurehearings.hmi.functional.directlisting.process;

import java.util.Map;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DirectListingResponseProcess {

    public static String getSessionId(Response response) {
        log.debug(response.getBody().prettyPrint());
        Map<String, ?> sessionObject = response.getBody().jsonPath().getMap("$.sessionsResponse.sessions[0]");
        return (String) sessionObject.get("sessionIdCaseHQ");
    }
}
