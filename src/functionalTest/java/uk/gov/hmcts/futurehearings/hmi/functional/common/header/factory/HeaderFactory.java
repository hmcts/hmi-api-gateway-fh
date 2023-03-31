package uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"HideUtilityClassConstructor", "PMD.UseDiamondOperator"})
public class HeaderFactory { //NOSONAR

    private static final String SOURCE_SYSTEM = "Source-System";

    private static final String JSON = "application/json";

    public static Map<String, Object> createStandardHmiHeader(final String destinationSystem) {

        final LocalDateTime now = LocalDateTime.now();
        final String requestCreatedAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'"));

        Map<String,Object> headersAsMap = new ConcurrentHashMap<>();
        headersAsMap.put("Content-Type", JSON);
        headersAsMap.put("Accept", JSON);
        if ("PIH".equals(destinationSystem)) {
            headersAsMap.put(SOURCE_SYSTEM, "EMULATOR");
        } else {
            headersAsMap.put(SOURCE_SYSTEM, "CFT");
        }
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", requestCreatedAt);
        return headersAsMap;
    }

    public static Map<String, Object> createStandardHmiHeader(final String sourceSystem,
                                                             final String destinationSystem) {

        final LocalDateTime now = LocalDateTime.now();
        final String requestCreatedAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'Z'"));

        Map<String,Object> headersAsMap = new ConcurrentHashMap<>();
        headersAsMap.put("Content-Type", JSON);
        headersAsMap.put("Accept", JSON);
        headersAsMap.put(SOURCE_SYSTEM, sourceSystem);
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", requestCreatedAt);
        return headersAsMap;
    }
}
