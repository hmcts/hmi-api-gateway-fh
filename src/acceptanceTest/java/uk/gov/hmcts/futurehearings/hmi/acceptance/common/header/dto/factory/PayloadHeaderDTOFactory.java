package uk.gov.hmcts.futurehearings.hmi.acceptance.common.header.dto.factory;

import uk.gov.hmcts.futurehearings.hmi.acceptance.delegate.dto.BusinessHeaderDTO;
import uk.gov.hmcts.futurehearings.hmi.acceptance.delegate.dto.SystemHeaderDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
                                                                     final String trace,
                                                                     final String host,
                                                                     final String subscriptionKey) {
        return SystemHeaderDTO.builder()
                .contentType(contentType)
                .trace(trace)
                .host(host)
                .subscriptionKey(subscriptionKey).build();
    }

    public static final BusinessHeaderDTO buildStandardBuinessHeaderPart(final String companyName,
                                                                         final String dateTime,
                                                                         final String source,
                                                                         final String destination,
                                                                         final String requestType) {
        return BusinessHeaderDTO.builder()
                .companyName(companyName)
                .dateTime(dateTime)
                .source(source)
                .destination(destination).requestType(requestType).build();
    }

    public static final Map<String,String> buildHeaderWithNullVaules() {
        return convertToMap(new SystemHeaderDTO(null,null,null,null),
                new BusinessHeaderDTO(null,null,null,null,null));
    }

    public static final Map<String,String> buildHeaderWithEmptyVaules() {
        return convertToMap(new SystemHeaderDTO("","","",""),
                new BusinessHeaderDTO("","","","",""));
    }

    public static final Map<String,String> convertToMap (final SystemHeaderDTO systemHeaderDTO,
                                                           final BusinessHeaderDTO businessHeaderDTO) {
        final Map<String, String>  headerMap = new HashMap<>();
        headerMap.put("Ocp-Apim-Subscription-Key",systemHeaderDTO.subscriptionKey());
        headerMap.put("Ocp-Apim-Trace",systemHeaderDTO.trace());
        headerMap.put("Host",systemHeaderDTO.host());
        headerMap.put("Content-Type",systemHeaderDTO.contentType());

        headerMap.put("Source",businessHeaderDTO.source());
        headerMap.put("Destination",businessHeaderDTO.destination());
        headerMap.put("Company-Name",businessHeaderDTO.companyName());
        headerMap.put("DateTime",businessHeaderDTO.dateTime());
        headerMap.put("RequestType",businessHeaderDTO.requestType());
        return headerMap;
    }

    public static final Map<String,String> convertToMap (final SystemHeaderDTO systemHeaderDTO,
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

}
