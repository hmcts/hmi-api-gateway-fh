package uk.gov.hmcts.futurehearings.hmi.acceptance.common.mock;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestClientTemplate.shouldExecute;

import java.util.List;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Slf4j
public class CommonStubFactory {

    private CommonStubFactory() {
    }

    public static void resetMocks (String requestURL) {

        log.debug("The value of the RequestURL "+requestURL);
        Header header = new Header("Content-Type", "application/json");
        Headers headers = new Headers(List.of(header));
        shouldExecute(headers,
                "{}",
                requestURL,
                null,
                HttpStatus.OK,
                HttpMethod.POST);
    }

    public static void uploadCommonMocks (String requestURL, String payload) {

        log.debug("The value of the RequestURL "+requestURL);
        log.debug("The value of the body " +payload);

        Header header = new Header("Content-Type", "application/json");
        Headers headers = new Headers(List.of(header));
        shouldExecute(headers,
                payload,
                requestURL,
                null,
                HttpStatus.OK,
                HttpMethod.POST);

    }
}
