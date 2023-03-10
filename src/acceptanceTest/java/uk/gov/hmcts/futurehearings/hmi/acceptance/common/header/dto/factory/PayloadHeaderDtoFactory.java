package uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.BusinessHeaderDto;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.SystemHeaderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public class PayloadHeaderDtoFactory {

    public static final SystemHeaderDto buildStandardSystemHeaderPart(final String contentType,
        final String accept, final String authorization, final String contentEncoding, final String cacheControl) {
        return SystemHeaderDto.builder()
                .contentType(contentType)
                .accept(accept)
                .authorization(authorization)
                .contentEncoding(contentEncoding)
                .cacheControl(cacheControl)
                .build();
    }

    public static final BusinessHeaderDto buildStandardBusinessHeaderPart(final String requestCreatedAt,
        final String sourceSystem, final String destinationSystem) {
        return BusinessHeaderDto.builder()
                .requestCreatedAt(requestCreatedAt)
                .sourceSystem(sourceSystem)
                .destinationSystem(destinationSystem)
                .build();
    }

    public static final Headers convertToRestAssuredHeaderRequiredHeaders(final SystemHeaderDto systemHeaderDto,
        final BusinessHeaderDto businessHeaderDto, final Map<String, String> mapAddedHeaderValues) {

        List<Header> listOfHeaders = new ArrayList<Header>();
        Header contentTypeHeader =  new Header("Content-Type", systemHeaderDto.contentType());
        listOfHeaders.add(contentTypeHeader);
        Header acceptHeader =  new Header("Accept", systemHeaderDto.accept());
        listOfHeaders.add(acceptHeader);
        Header sourceSystemHeader =  new Header("Source-System", businessHeaderDto.sourceSystem());
        listOfHeaders.add(sourceSystemHeader);
        Header destinationSystemHeader =  new Header("Destination-System", businessHeaderDto.destinationSystem());
        listOfHeaders.add(destinationSystemHeader);
        Header requestCreatedAtHeader =  new Header("Request-Created-At", businessHeaderDto.requestCreatedAt());
        listOfHeaders.add(requestCreatedAtHeader);
        mapAddedHeaderValues.forEach((key, value) -> {
            Header extraHeader = new Header(key, value);
            listOfHeaders.add(extraHeader);
        });
        Headers headers = new Headers(listOfHeaders);
        return headers;
    }

    public static final Map<String, String> convertToMapWithMandatoryHeaders(final SystemHeaderDto systemHeaderDto,
        final BusinessHeaderDto businessHeaderDto) {

        final Map<String, String> headerMap = new ConcurrentHashMap<>();
        headerMap.put("Content-Type", systemHeaderDto.contentType());
        headerMap.put("Accept", systemHeaderDto.accept());
        headerMap.put("Source-System", businessHeaderDto.sourceSystem());
        headerMap.put("Destination-System", businessHeaderDto.destinationSystem());
        headerMap.put("Request-Created-At", businessHeaderDto.requestCreatedAt());
        return headerMap;
    }

    public static final Map<String, String> convertToMapWithAllHeaders(final SystemHeaderDto systemHeaderDto,
                                                                       final BusinessHeaderDto businessHeaderDto) {
        final Map<String, String> headerMap = convertToMapWithMandatoryHeaders(systemHeaderDto, businessHeaderDto);
        headerMap.put("Cache-Control", systemHeaderDto.cacheControl());
        headerMap.put("Content-Encoding", systemHeaderDto.contentEncoding());
        return headerMap;
    }

    public static final Map<String, String> convertToMapAfterHeadersAdded(final SystemHeaderDto systemHeaderDto,
                                                                            final BusinessHeaderDto businessHeaderDto,
                                                                            final Map<String,String> headersToAdd) {

        final Map<String, String> headerMap = convertToMapWithMandatoryHeaders(systemHeaderDto, businessHeaderDto);
        headerMap.putAll(headersToAdd);
        return headerMap;
    }

    private PayloadHeaderDtoFactory() {
    }
}
