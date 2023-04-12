package uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate;

import io.restassured.http.Headers;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.delegate.dto.DelegateDto;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.verify.HmiVerifier;

import java.util.Map;

@SuppressWarnings({"PMD.ExcessiveParameterList", "PMD.UseObjectForClearerAPI"})
public interface CommonDelegate {


    void testExpectedResponseForSuppliedHeader(String authorizationToken,
                                                           String targetUrl,
                                                           String inputFile,
                                                           Map<String, String> standardHeaderMap,
                                                           Headers headers,
                                                           Map<String, String> params,
                                                           HttpMethod httpMethod,
                                                           HttpStatus status,
                                                           String inputFileDirectory,
                                                           HmiVerifier hmiVerifier,
                                                           String expectedMessage,
                                                           DelegateDto delegateDT0) throws Exception; //NOSONAR
}
