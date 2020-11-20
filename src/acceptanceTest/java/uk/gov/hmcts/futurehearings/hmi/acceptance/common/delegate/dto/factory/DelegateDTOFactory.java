package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto.factory;

import static uk.gov.hmcts.futurehearings.hmi.acceptance.common.helper.CommonHeaderHelper.createStandardPayloadHeader;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto.DelegateFlyweightDT0;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HMIVerifier;

import java.util.Map;

import io.restassured.http.Headers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DelegateDTOFactory {

    public static DelegateFlyweightDT0 buildDelegateDTO(
            final String subscriptionKey,
            final String oauthToken,
            final String relativeURL,
            final String inputFileName,
            final Map<String, String> headersAsMap,
            final Headers restAssuredHeaders,
            final Map<String, String> queryParameters,
            final HttpMethod httpMethod,
            final HttpStatus httpStatus,
            final String inputFileDirectory,
            final HMIVerifier hmiVerifier,
            final String expectedMessage) {

        return DelegateFlyweightDT0.builder()
                .targetSubscriptionKey(subscriptionKey)
                .authorizationToken(oauthToken)
                .targetURL(relativeURL)
                .inputFile(inputFileName)
                .standardHeaderMap(headersAsMap)
                .headers(restAssuredHeaders)
                .params(queryParameters)
                .httpMethod(httpMethod)
                .status(httpStatus)
                .inputFileDirectory(inputFileDirectory)
                .hmiVerifier(hmiVerifier)
                .expectedMessage(expectedMessage).build();

    }
}
