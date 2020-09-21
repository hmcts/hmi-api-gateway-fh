package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestClientTemplate.shouldExecute;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HMIVerifier;

import java.io.IOException;
import java.util.Map;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component("CommonDelegate")
public class CommonDelegateImpl implements CommonDelegate {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/%s/input";
    public void test_expected_response_for_supplied_header(final String targetSubscriptionKey,
                                                           final String targetURL,
                                                           final String inputFile,
                                                           final Map<String,String> standardHeaderMap,
                                                           final Headers headers,
                                                           final Map<String, String> params,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status,
                                                           final String inputFileDirectory,
                                                           final HMIVerifier hmiVerifier,
                                                           final String expectedMessage) throws IOException {

        log.debug("The value of the target header" +standardHeaderMap);
        String inputPayload = null;
        switch (httpMethod) {
            case POST:
            case PUT:
            case DELETE:
                inputPayload = TestingUtils.readFileContents(String.format(INPUT_FILE_PATH, inputFileDirectory) + "/" + inputFile);
                hmiVerifier.verify(status,expectedMessage,
                        performRESTCall(targetURL, standardHeaderMap, headers, params, httpMethod, status, inputPayload));
                break;
            case GET:
                hmiVerifier.verify(status,expectedMessage,performRESTCall(targetURL, standardHeaderMap, headers, params, httpMethod, status, inputPayload));
                break;
            case OPTIONS:
                performRESTCall(targetURL, standardHeaderMap, headers, params, httpMethod, status, inputPayload);
        }

    }

    private Response performRESTCall(final String targetURL, final Map<String, String> standardHeaderMap, final Headers headers, final Map<String, String> params, final HttpMethod httpMethod, final HttpStatus status, final String inputPayload) {
        return shouldExecute(standardHeaderMap,
                    headers,
                    inputPayload,
                    targetURL,
                    params,
                    status,
                    httpMethod);
    }
}
