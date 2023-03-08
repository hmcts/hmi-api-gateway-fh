package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.TestingUtils;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto.DelegateDto;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HmiVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.RestClientTemplate.shouldExecute;

@Slf4j
@Component("CommonDelegate")
public class CommonDelegateImpl implements CommonDelegate {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/acceptance/%s/input";

    public void test_expected_response_for_supplied_header(final String authorizationToken,
                                                           final String targetUrl,
                                                           final String inputFile,
                                                           final Map<String, String> standardHeaderMap,
                                                           final Headers headers,
                                                           final Map<String, String> params,
                                                           final HttpMethod httpMethod,
                                                           final HttpStatus status,
                                                           final String inputFileDirectory,
                                                           final HmiVerifier hmiVerifier,
                                                           final String expectedMessage,
                                                           final DelegateDto delegateFlyweightDT0) throws Exception {

        log.debug("The value of the target header (Header Map) : " + standardHeaderMap);
        log.debug("The value of the target header (Wiremock Header) :" + standardHeaderMap);
        Headers standardWireMockHeaders = null;
        if (Objects.nonNull(standardHeaderMap) && standardHeaderMap.size() > 0) {
            standardWireMockHeaders = convertHeaderMapToWireMockHeaders(standardHeaderMap);
        } else {
            standardWireMockHeaders = headers;
        }
        handleRestCall(targetUrl, inputFile, standardWireMockHeaders,
                authorizationToken, params, httpMethod, status, inputFileDirectory, hmiVerifier, expectedMessage);

    }

    private void handleRestCall(final String targetUrl,
                                final String inputFile,
                                final Headers headers,
                                final String authorizationToken,
                                final Map<String, String> params,
                                final HttpMethod httpMethod,
                                final HttpStatus status,
                                final String inputFileDirectory,
                                final HmiVerifier hmiVerifier,
                                final String expectedMessage) throws Exception {

        String inputPayload = null;
        switch (httpMethod) {
            case POST:
            case PUT:
                inputPayload = TestingUtils.readFileContents(String.format(INPUT_FILE_PATH,
                        inputFileDirectory) + "/" + inputFile);
                hmiVerifier.verify(status, expectedMessage,
                        performRestCall(targetUrl, headers, authorizationToken,
                                params, httpMethod, status, inputPayload));
                break;
            case DELETE:
                if (inputFileDirectory != null) {
                    inputPayload = TestingUtils.readFileContents(String.format(INPUT_FILE_PATH,
                            inputFileDirectory) + "/" + inputFile);
                }
                hmiVerifier.verify(status, expectedMessage,
                        performRestCall(targetUrl, headers, authorizationToken,
                                params, httpMethod, status, inputPayload));
                break;
            case GET:
                hmiVerifier.verify(status, expectedMessage, performRestCall(targetUrl, headers,
                        authorizationToken, params, httpMethod, status, inputPayload));
                break;
            case OPTIONS:
                performRestCall(targetUrl, headers, authorizationToken,
                        params, httpMethod, status, inputPayload);
                break;
            default:
                log.error("Http method not in the list");
                break;
        }
    }

    private Response performRestCall(final String targetUrl,
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
                targetUrl,
                params,
                status,
                httpMethod);
    }

    private static Headers convertHeaderMapToWireMockHeaders(final Map<String, String> headerMap) {

        List<Header> listOfHeaders = new ArrayList<>();
        headerMap.forEach((key, value) -> {
            Header header = new Header(key, value);
            listOfHeaders.add(header);
        });
        Headers headers = new Headers(listOfHeaders);
        return headers;
    }
}
