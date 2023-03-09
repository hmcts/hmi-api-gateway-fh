package uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"HideUtilityClassConstructor", "PMD.UseDiamondOperator"})
public class HeaderFactory {

    private static final String JSON = "application/json";

    public static Map<String, Object> createStandardHmiHeader(final String destinationSystem) {

        final LocalDateTime now = LocalDateTime.now();
        final String requestCreatedAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'"));

        Map<String,Object> headersAsMap = new ConcurrentHashMap<>();
        headersAsMap.put("Content-Type", JSON);
        headersAsMap.put("Accept", JSON);
        if ("PIH".equals(destinationSystem)) {
            headersAsMap.put("Source-System", "EMULATOR");
        } else {
            headersAsMap.put("Source-System", "CFT");
        }
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", requestCreatedAt);
        //return Collections.unmodifiableMap(headersAsMap);
        return headersAsMap;
    }

    public static Map<String, Object> createStandardHmiHeader(final String sourceSystem,
                                                             final String destinationSystem) {

        final LocalDateTime now = LocalDateTime.now();
        final String requestCreatedAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'"));

        Map<String,Object> headersAsMap = new ConcurrentHashMap<>();
        headersAsMap.put("Content-Type", JSON);
        headersAsMap.put("Accept", JSON);
        headersAsMap.put("Source-System", sourceSystem);
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", requestCreatedAt);
        //return Collections.unmodifiableMap(headersAsMap);
        return headersAsMap;
    }
}
