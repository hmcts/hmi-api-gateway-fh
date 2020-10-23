package uk.gov.hmcts.futurehearings.hmi.acceptance.common.mock;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestClientTemplate.shouldExecute;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class CommonStubFactory {

    private CommonStubFactory() {

    }

    public static void resetMocks (String requestURL) {

        shouldExecute(null,
                null,
                requestURL,
                null,
                HttpStatus.OK,
                HttpMethod.POST);
    }

    public static  void uploadCommonMocks (String requestURL, String payload) {

        shouldExecute(null,
                payload,
                requestURL,
                null,
                HttpStatus.OK,
                HttpMethod.POST);

    }
}
