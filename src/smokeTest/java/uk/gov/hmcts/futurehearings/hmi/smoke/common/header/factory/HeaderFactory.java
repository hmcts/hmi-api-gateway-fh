package uk.gov.hmcts.futurehearings.hmi.smoke.common.header.factory;

import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"HideUtilityClassConstructor"})
public class HeaderFactory { //NOSONAR

    public static Map<String, String> createStandardHmiHeader(final String destinationSystem) {

        final LocalDateTime now = LocalDateTime.now();
        final String requestCreatedAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'"));

        Map<String,String> headersAsMap = new ConcurrentHashMap<>();
        headersAsMap.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headersAsMap.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", requestCreatedAt);
        return headersAsMap;
    }
}
