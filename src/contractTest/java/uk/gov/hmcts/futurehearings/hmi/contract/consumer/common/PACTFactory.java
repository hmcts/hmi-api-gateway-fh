package uk.gov.hmcts.futurehearings.hmi.contract.consumer.common;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;

import java.io.IOException;
import java.util.Map;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PACTFactory {

    public static final RequestResponsePact buildPact(final Map<String, String> headersAsMap,
                                                      final PactDslWithProvider builder,
                                                      final String pactDescription,
                                                      final String requestPayloadJsonPath,
                                                      final String caseHQAPIPath,
                                                      final HttpMethod httpMethod,
                                                      final HttpStatus httpStatus,
                                                      final String apiState) throws IOException {
        return builder
                .given(apiState)
                .uponReceiving(pactDescription)
                .path(caseHQAPIPath)
                .method(httpMethod.toString())
                .headers(headersAsMap)
                .body(readFileContents(requestPayloadJsonPath), ContentType.APPLICATION_JSON)
                .willRespondWith()
                .status(httpStatus.value())
                .toPact();
    }

    public static final RequestResponsePact buildPactWithPayload(final Map<String, String> headersAsMap,
                                                      final PactDslWithProvider builder,
                                                      final String pactDescription,
                                                      final String requestPayload,
                                                      final String caseHQAPIPath,
                                                      final HttpMethod httpMethod,
                                                      final HttpStatus httpStatus,
                                                      final String apiState) throws IOException {
        return builder
                .given(apiState)
                .uponReceiving(pactDescription)
                .path(caseHQAPIPath)
                .method(httpMethod.toString())
                .headers(headersAsMap)
                .body(requestPayload, ContentType.APPLICATION_JSON)
                .willRespondWith()
                .status(httpStatus.value())
                .toPact();
    }

    public static final RequestResponsePact buildResponsePactFromSnL(final Map<String, String> headersAsMap,
                                                            final PactDslWithProvider builder,
                                                            final String pactDescription,
                                                            final String responseJsonPath,
                                                            final String caseHQAPIPath,
                                                            final HttpMethod httpMethod,
                                                            final HttpStatus httpStatus,
                                                            final String apiState) throws IOException {
        return builder
                .given(apiState)
                .uponReceiving(pactDescription)
                .path(caseHQAPIPath)
                .method(httpMethod.toString())
                .headers(headersAsMap)
                .willRespondWith()
                .body(readFileContents(responseJsonPath), ContentType.APPLICATION_JSON)
                .status(httpStatus.value())
                .toPact();
    }

}
