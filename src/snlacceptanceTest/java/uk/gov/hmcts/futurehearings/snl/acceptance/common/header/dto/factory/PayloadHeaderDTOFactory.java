package uk.gov.hmcts.futurehearings.snl.acceptance.common.header.dto.factory;

import uk.gov.hmcts.futurehearings.snl.acceptance.common.header.dto.BusinessHeaderDTO;
import uk.gov.hmcts.futurehearings.snl.acceptance.common.header.dto.SystemHeaderDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PayloadHeaderDTOFactory {

    public static final SystemHeaderDTO buildStandardSytemHeaderPart(final String contentType,
                                                                     final String accept,
                                                                     final String authorization,
                                                                     final String contentEncoding,
                                                                     final String subscriptionKey,
                                                                     final String cacheControl) {
        return SystemHeaderDTO.builder()
                .contentType(contentType)
                .accept(accept)
                .authorization(authorization)
                .contentEncoding(contentEncoding)
                .cacheControl(cacheControl)
                .subscriptionKey(subscriptionKey).build();
    }

    public static final BusinessHeaderDTO buildStandardBuinessHeaderPart(final String requestCreatedAt,
                                                                         final String requestProcessedAt,
                                                                         final String sourceSystem,
                                                                         final String destinationSystem,
                                                                         final String requestType) {
        return BusinessHeaderDTO.builder()
                .requestCreatedAt(requestCreatedAt)
                .requestProcessedAt(requestProcessedAt)
                .sourceSystem(sourceSystem)
                .destinationSystem(destinationSystem)
                .requestType(requestType).build();
    }

    public static final Headers convertToRestAssuredHeaderRequiredHeaders(final SystemHeaderDTO systemHeaderDTO,
                                                                          final BusinessHeaderDTO businessHeaderDTO,
                                                                          final Map<String, String> mapAddedHeaderValues) {
        List<Header> listOfHeaders = new ArrayList<Header>();
        Header subscriptionKeyHeader = new Header("Subscription-Key", systemHeaderDTO.subscriptionKey());
        listOfHeaders.add(subscriptionKeyHeader);
        Header contentTypeHeader =  new Header("Content-Type", systemHeaderDTO.contentType());
        listOfHeaders.add(contentTypeHeader);
        Header acceptHeader =  new Header("Accept", systemHeaderDTO.accept());
        listOfHeaders.add(acceptHeader);
        Header sourceSystemHeader =  new Header("Source-System", businessHeaderDTO.sourceSystem());
        listOfHeaders.add(sourceSystemHeader);
        Header destinationSystemHeader =  new Header("Destination-System", businessHeaderDTO.destinationSystem());
        listOfHeaders.add(destinationSystemHeader);
        Header requestCreatedAtHeader =  new Header("Request-Created-At", businessHeaderDTO.requestCreatedAt());
        listOfHeaders.add(requestCreatedAtHeader);
        Header requestProcessedAtHeader =  new Header("Request-Processed-At", businessHeaderDTO.requestProcessedAt());
        listOfHeaders.add(requestProcessedAtHeader);
        Header requestTypeHeader =  new Header("Request-Type", businessHeaderDTO.requestType());
        listOfHeaders.add(requestTypeHeader);
        mapAddedHeaderValues.forEach((key, value) -> {
           Header extraHeader = new Header (key,value);
            listOfHeaders.add(extraHeader);
        });
        Headers headers = new Headers(listOfHeaders);
        return headers;
    }

    public static final Multimap<String, String> convertToMultiMapWithRequiredHeaders(final SystemHeaderDTO systemHeaderDTO,
                                                                                      final BusinessHeaderDTO businessHeaderDTO,
                                                                                      final Map<String, String> mapAddedHeaderValues) {
        final Multimap<String, String> headerAsMultiMap = ArrayListMultimap.create();
        headerAsMultiMap.put("Subscription-Key", systemHeaderDTO.subscriptionKey());
        headerAsMultiMap.put("Content-Type", systemHeaderDTO.contentType());
        headerAsMultiMap.put("Accept", systemHeaderDTO.accept());
        headerAsMultiMap.put("Source-System", businessHeaderDTO.sourceSystem());
        headerAsMultiMap.put("Destination-System", businessHeaderDTO.destinationSystem());
        headerAsMultiMap.put("Request-Created-At", businessHeaderDTO.requestCreatedAt());
        headerAsMultiMap.put("Request-Processed-At", businessHeaderDTO.requestProcessedAt());
        headerAsMultiMap.put("Request-Type", businessHeaderDTO.requestType());
        mapAddedHeaderValues.forEach((key, value) -> {
            headerAsMultiMap.put(key, value);
        });
        return headerAsMultiMap;
    }

    public static final Map<String, String> convertToMapWithMandatoryHeaders(final SystemHeaderDTO systemHeaderDTO,
                                                                             final BusinessHeaderDTO businessHeaderDTO) {
        final Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Subscription-Key", systemHeaderDTO.subscriptionKey());
        headerMap.put("Content-Type", systemHeaderDTO.contentType());
        headerMap.put("Accept", systemHeaderDTO.accept());
        headerMap.put("Source-System", businessHeaderDTO.sourceSystem());
        headerMap.put("Destination-System", businessHeaderDTO.destinationSystem());
        headerMap.put("Request-Created-At", businessHeaderDTO.requestCreatedAt());
        headerMap.put("Request-Processed-At", businessHeaderDTO.requestProcessedAt());
        headerMap.put("Request-Type", businessHeaderDTO.requestType());
        return headerMap;
    }

    public static final Map<String, String> convertToMapWithAllHeaders(final SystemHeaderDTO systemHeaderDTO,
                                                                       final BusinessHeaderDTO businessHeaderDTO) {
        final Map<String, String> headerMap = convertToMapWithMandatoryHeaders(systemHeaderDTO, businessHeaderDTO);
        headerMap.put("Cache-Control", systemHeaderDTO.cacheControl());
        headerMap.put("Content-Encoding", systemHeaderDTO.contentEncoding());
        return headerMap;
    }

    public static final Map<String, String> convertToMapAfterHeadersRemoved(final SystemHeaderDTO systemHeaderDTO,
                                                                            final BusinessHeaderDTO businessHeaderDTO,
                                                                            final List<String> headersToRemove) {

        final Map<String, String> headerMap = convertToMapWithMandatoryHeaders(systemHeaderDTO, businessHeaderDTO);
        if (Objects.nonNull(headersToRemove)) {
            headersToRemove.stream().forEach((o) -> {
                headerMap.remove(o.trim());
            });
        }
        return headerMap;
    }

    public static final Multimap<String, String> convertToMapAfterExtraMapFieldsAdded(final SystemHeaderDTO systemHeaderDTO,
                                                                                 final BusinessHeaderDTO businessHeaderDTO,
                                                                                 final Map<String, String> extraHeadersToAdd) {

        return convertToMultiMapWithRequiredHeaders(systemHeaderDTO, businessHeaderDTO, extraHeadersToAdd);
    }

    public static final Map<String, String> convertToMapAfterTruncatingHeaderKey(final SystemHeaderDTO systemHeaderDTO,
                                                                                 final BusinessHeaderDTO businessHeaderDTO,
                                                                                 final List<String> headersToTruncate) {

        final Map<String, String> headerMap = convertToMapWithMandatoryHeaders(systemHeaderDTO, businessHeaderDTO);
        if (Objects.nonNull(headersToTruncate)) {
            headersToTruncate.stream().forEach((o) -> {
                if (headerMap.containsKey(o)) {
                    String newKey = o.substring(0, o.length() - 1);
                    String newValue = headerMap.get(o);
                    headerMap.remove(o);
                    headerMap.put(newKey, newValue);
                }
                ;
            });
        }
        return headerMap;
    }
}
