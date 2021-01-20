package uk.gov.hmcts.futurehearings.hmi.smoke.common.header.factory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HeaderFactory {

    public static Map<String,String> createStandardHMIHeader(final String destinationSystem) {

        final LocalDateTime now = LocalDateTime.now();
        final String requestCreatedAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'"));

        Map<String,String> headersAsMap = new HashMap<String,String>();
        headersAsMap.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headersAsMap.put("Accept", MediaType.APPLICATION_JSON_VALUE+"; version=1.2");
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", requestCreatedAt);
        return headersAsMap;
        //return Collections.unmodifiableMap(headersAsMap);
    }
}
