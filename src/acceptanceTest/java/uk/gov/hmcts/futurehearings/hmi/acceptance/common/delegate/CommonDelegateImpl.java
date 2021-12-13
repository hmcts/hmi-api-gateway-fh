package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;
import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestClientTemplate.shouldExecute;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto.DelegateDTO;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HMIVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.restassured.http.Header;
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

    public void test_expected_response_for_supplied_header(final String authorizationToken,
                                                           final String targetURL,
                                                           final String inputFile,
                                                           final Map<String, String> standardHeaderMap,
                                                           final Headers headers,
                                                           final Map<String, String> params,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status,
                                                           final String inputFileDirectory,
                                                           final HMIVerifier hmiVerifier,
                                                           final String expectedMessage,
                                                           final DelegateDTO delegateFlyweightDT0) throws Exception {

        log.debug("The value of the target header (Header Map) : " + standardHeaderMap);
        log.debug("The value of the target header (Wiremock Header) :" + standardHeaderMap);
        Headers standardWireMockHeaders = null;
        if (Objects.nonNull(standardHeaderMap) && standardHeaderMap.size() > 0) {
            standardWireMockHeaders = convertHeaderMapToWireMockHeaders(standardHeaderMap);
        } else {
            standardWireMockHeaders = headers;
        }
        handleRestCall(targetURL, inputFile, standardWireMockHeaders, authorizationToken, params, httpMethod, status, inputFileDirectory, hmiVerifier, expectedMessage);

    }

    private void handleRestCall(final String targetURL,
                                final String inputFile,
                                final Headers headers,
                                final String authorizationToken,
                                final Map<String, String> params,
                                final HttpMethod httpMethod,
                                final HttpStatus status,
                                final String inputFileDirectory,
                                final HMIVerifier hmiVerifier,
                                final String expectedMessage) throws Exception {

        String inputPayload = null;
        switch (httpMethod) {
            case POST:
            case PUT:
                inputPayload = TestingUtils.readFileContents(String.format(INPUT_FILE_PATH, inputFileDirectory) + "/" + inputFile);
                hmiVerifier.verify(status, expectedMessage,
                        performRESTCall(targetURL, headers, authorizationToken, params, httpMethod, status, inputPayload));
                break;
            case DELETE:
                if(inputFileDirectory != null) {
                    inputPayload = TestingUtils.readFileContents(String.format(INPUT_FILE_PATH, inputFileDirectory) + "/" + inputFile);
                }
                hmiVerifier.verify(status, expectedMessage,
                        performRESTCall(targetURL, headers, authorizationToken, params, httpMethod, status, inputPayload));
                break;
            case GET:
                hmiVerifier.verify(status, expectedMessage, performRESTCall(targetURL, headers, authorizationToken, params, httpMethod, status, inputPayload));
                break;
            case OPTIONS:
                performRESTCall(targetURL, headers, authorizationToken, params, httpMethod, status, inputPayload);
        }
    }

    private Response performRESTCall(final String targetURL,
                                     final Headers headers,
                                     final String authorizationToken,
                                     final Map<String, String> params,
                                     final HttpMethod httpMethod,
                                     final HttpStatus status,
                                     final String inputPayload) {
        return shouldExecute(
                headers,
                authorizationToken,
                inputPayload,
                targetURL,
                params,
                status,
                httpMethod);
    }

    private static final Headers convertHeaderMapToWireMockHeaders(final Map<String, String> headerMap) {

        List<Header> listOfHeaders = new ArrayList<>();
        headerMap.forEach((key, value) -> {
            Header header = new Header(key, value);
            listOfHeaders.add(header);
        });
        Headers headers = new Headers(listOfHeaders);
        return headers;
    }
}
