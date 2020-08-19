package uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory;

import uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.BusinessHeaderDTO;
import uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.SystemHeaderDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;

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

    public static final Map<String,String> buildHeaderWithNullValues() {

        return convertToMap(SystemHeaderDTO.builder().build(),
                           BusinessHeaderDTO.builder().build());

    }

    public static final Map<String,String> buildHeaderWithEmptyValues() {
        return convertToMap(SystemHeaderDTO.builder()
                        .contentType("")
                        .accept("")
                        .authorization("")
                        .contentEncoding("")
                        .cacheControl("")
                        .subscriptionKey("").build(),

                BusinessHeaderDTO.builder()
                        .requestCreatedAt("")
                        .requestProcessedAt("")
                        .sourceSystem("")
                        .destinationSystem("")
                        .requestType("").build());


    }

    public static final Map<String,String> createPayloadHeader(final String targetSubscriptionKey) {

        return Collections.unmodifiableMap(
                convertToMap(
                        buildStandardSytemHeaderPart(MediaType.APPLICATION_JSON_VALUE,
                                null,
                                null,
                                null,
                                targetSubscriptionKey,
                                null),

                        buildStandardBuinessHeaderPart("dateTime",
                                "dateTime",
                                "SnL",
                                "CFT",
                                "THEFT")));
    }

    public static final Map<String,String> createPayloadHeaderRemoveFields (final String targetSubscriptionKey,
                                                                             List<String> removeHeaderString) {

        return Collections.unmodifiableMap(convertToMapAfterHeadersRemoved(buildStandardSytemHeaderPart(
                MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                targetSubscriptionKey,
                null),

                buildStandardBuinessHeaderPart("dateTime",
                        "dateTime",
                        "SnL",
                        "CFT",
                        "THEFT"),removeHeaderString));
    }

    public static final Map<String,String> createPayloadHeaderNullFields (final String targetSubscriptionKey,
                                                                            List<String> nullHeaderString) {

        return Collections.unmodifiableMap(convertToMapAfterHeadersRemoved(buildStandardSytemHeaderPart(
                MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                targetSubscriptionKey,
                null),

                buildStandardBuinessHeaderPart("dateTime",
                        "dateTime",
                        "SnL",
                        "CFT",
                        "THEFT"),nullHeaderString));
    }

    public static final Map<String,String> createPayloadHeaderEmptyFields (final String targetSubscriptionKey,
                                                                          List<String> emptyHeaderString) {

        return Collections.unmodifiableMap(convertToMapAfterHeadersRemoved(buildStandardSytemHeaderPart(
                MediaType.APPLICATION_JSON_VALUE,
                null,
                null,
                null,
                targetSubscriptionKey,
                null),

                buildStandardBuinessHeaderPart("dateTime",
                        "dateTime",
                        "SnL",
                        "CFT",
                        "THEFT"),emptyHeaderString));
    }

    public static final Map<String,String> convertToMap (final SystemHeaderDTO systemHeaderDTO,
                                                           final BusinessHeaderDTO businessHeaderDTO) {
        final Map<String, String>  headerMap = new HashMap<>();
        headerMap.put("Content-Type",systemHeaderDTO.contentType());
        headerMap.put("Accept",systemHeaderDTO.accept());
        //headerMap.put("Content-Encoding",systemHeaderDTO.contentEncoding());
        headerMap.put("Authorization",systemHeaderDTO.authorization());
        headerMap.put("Ocp-Apim-Subscription-Key",systemHeaderDTO.subscriptionKey());

        headerMap.put("Source-System",businessHeaderDTO.sourceSystem());
        headerMap.put("Destination-System",businessHeaderDTO.destinationSystem());
        headerMap.put("Request-Created-At",businessHeaderDTO.requestCreatedAt());
        headerMap.put("Request-Processed-At",businessHeaderDTO.requestProcessedAt());
        headerMap.put("Request-Type",businessHeaderDTO.requestType());
        return headerMap;
    }

    public static final Map<String,String> convertToMapAfterHeadersRemoved (final SystemHeaderDTO systemHeaderDTO,
                                                         final BusinessHeaderDTO businessHeaderDTO,
                                                         final List<String> headersToRemove) {

        final Map<String, String>  headerMap = convertToMap(systemHeaderDTO,businessHeaderDTO);
        if (Objects.nonNull(headersToRemove)) {
            headersToRemove.stream().forEach((o)-> {
                headerMap.remove(o.trim());
            });
        }
        return headerMap;
    }

    public static final Map<String,String> convertToMapAfterHeadersNulled (final SystemHeaderDTO systemHeaderDTO,
                                                                            final BusinessHeaderDTO businessHeaderDTO,
                                                                            final List<String> headersToNull) {

        final Map<String, String>  headerMap = convertToMap(systemHeaderDTO,businessHeaderDTO);
        if (Objects.nonNull(headersToNull)) {
            headersToNull.stream().forEach((o)-> {
                if (headerMap.containsKey(o)) {
                    headerMap.replace(o,null);
                };
            });
        }
        return headerMap;
    }

    public static final Map<String,String> convertToMapAfterHeadersEmpty (final SystemHeaderDTO systemHeaderDTO,
                                                                           final BusinessHeaderDTO businessHeaderDTO,
                                                                           final List<String> headersToEmpty) {

        final Map<String, String>  headerMap = convertToMap(systemHeaderDTO,businessHeaderDTO);
        if (Objects.nonNull(headersToEmpty)) {
            headersToEmpty.stream().forEach((o)-> {
                if (headerMap.containsKey(o)) {
                    headerMap.replace(o,"");
                };
            });
        }
        return headerMap;
    }
}
