package uk.gov.hmcts.futurehearings.hmi.acceptance.schedules.helper;

import java.util.HashMap;
import java.util.Map;

public class SessionsParamsHelper {

    public static Map<String, String> buildQueryParams(final String paramKey, final String paramValue) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey, paramValue);
        return queryParams;
    }

    public static Map<String, String> buildMultipleQueryParams(final String paramKey, final String paramValue,
                                                               final String paramKey1, final String paramValue1) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey, paramValue);
        queryParams.put(paramKey1, paramValue1);
        return queryParams;
    }

    public static Map<String, String> buildMultipleQueryParams(final String paramKey1, final String paramValue1,
                                                               final String paramKey2, final String paramValue2,
                                                               final String paramKey3, final String paramValue3) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey1, paramValue1);
        queryParams.put(paramKey2, paramValue2);
        queryParams.put(paramKey3, paramValue3);
        return queryParams;
    }

    public static Map<String, String> buildAllQueryParams(final String paramKey, final String paramValue,
                                                          final String paramKey1, final String paramValue1,
                                                          final String paramKey2, final String paramValue2,
                                                          final String paramKey3, final String paramValue3,
                                                          final String paramKey4, final String paramValue4,
                                                          final String paramKey5, final String paramValue5) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey, paramValue);
        queryParams.put(paramKey1, paramValue1);
        queryParams.put(paramKey2, paramValue2);
        queryParams.put(paramKey3, paramValue3);
        queryParams.put(paramKey4, paramValue4);
        queryParams.put(paramKey5, paramValue5);
        return queryParams;
    }
}
