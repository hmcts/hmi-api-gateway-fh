package uk.gov.hmcts.futurehearings.hmi.functional.sessions.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.rest.RestClientTemplate.callRestEndpointWithQueryParams;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Step;
import org.springframework.http.HttpStatus;

@Slf4j
public class SessionsLookUpSteps {

    @Step("User makes a request to Get the Sessions with the right Query parameter conditions")
    public void checkSessionsForAllTheRelevantQueryParameters(final String apiURL,
                                                              final Map<String, Object> headersAsMap,
                                                              final String authorizationToken,
                                                              final Map<String, String> queryParameters) throws Exception {

        Response response = callRestEndpointWithQueryParams(apiURL,
                headersAsMap,
                authorizationToken,
                queryParameters, HttpStatus.OK);
        String value = response.getBody().asString();
        log.debug("The value of the Response body" + value);
        final DocumentContext context = JsonPath.parse(value);

        if (Objects.nonNull(queryParameters.get("requestSessionType"))) {
            final List<String> sessionTypeList = context.read("$.sessionsResponse.sessions[*].sessionType");
            assertTrue(sessionTypeList.stream().allMatch(s -> {
                return s.equals(queryParameters.get("requestSessionType").trim());
            }));
        }
        if (Objects.nonNull(queryParameters.get("requestDuration"))) {
            final List<Integer> sessionTypeList = context.read("$.sessionsResponse.sessions[*].sessionDuration");
            assertTrue(sessionTypeList.stream().allMatch(s -> {
                return s == Integer.parseInt(queryParameters.get("requestDuration"));
            }));
        }
        if (Objects.nonNull(queryParameters.get("requestJudgeType"))) {
            final List<String> sessionTypeList = context.read("$.sessionsResponse.sessions[*].sessionJudges[*].sessionJudgeType");
            assertTrue(sessionTypeList.stream().allMatch(s -> {
                return s.equals(queryParameters.get("requestJudgeType").trim());
            }));
        }
        if (Objects.nonNull(queryParameters.get("requestLocationId"))) {
            final List<String> sessionTypeList = context.read("$.sessionsResponse.sessions[*].sessionRoomId");
            assertTrue(sessionTypeList.stream().allMatch(s -> {
                return s.equals(queryParameters.get("requestLocationId").trim());
            }));
        }
        if (Objects.nonNull(queryParameters.get("requestStartDate")) && Objects.nonNull(queryParameters.get("requestEndDate"))) {
            final LocalDateTime dateTimeStartDateInput = LocalDateTime.parse(queryParameters.get("requestStartDate"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            final LocalDateTime dateTimeEndDateInput = LocalDateTime.parse(queryParameters.get("requestEndDate"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            log.debug("The value of the given date" + dateTimeEndDateInput);
            log.debug("The value of the given date seconds" + dateTimeEndDateInput.getSecond());
            final List<String> sessionsList = context.read("$.sessionsResponse.sessions[*].sessionStartTime");
            assertTrue(sessionsList.stream().allMatch(s -> {
                final LocalDateTime dateTime = LocalDateTime.parse(s,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
                return (dateTime.isAfter(dateTimeStartDateInput) && dateTime.isBefore(dateTimeEndDateInput)
                        || (dateTime.isEqual(dateTimeStartDateInput) || dateTime.isEqual(dateTimeEndDateInput)));
            }));
        }
    }
}
