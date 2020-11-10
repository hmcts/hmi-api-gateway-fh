package uk.gov.hmcts.futurehearings.hmi.functional.common.header.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HeaderFactory {

    public static Map<String,Object> createStandardHMIHeader(final String targetSubscriptionID,
            final String destinationSystem) {

        Map<String,String> headersAsMap = new HashMap<String,String>();
        headersAsMap.put("Content-Type", "application/json");
        headersAsMap.put("Accept", "application/json");
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionID);
        headersAsMap.put("Source-System", "CFT");
        headersAsMap.put("Destination-System", destinationSystem);
        headersAsMap.put("Request-Created-At", "2002-10-02T15:00:00Z");
        headersAsMap.put("Request-Processed-At", "2002-10-02 15:00:00Z");
        headersAsMap.put("Request-Type", "ASSAULT");
        return Collections.unmodifiableMap(headersAsMap);
    }
}
