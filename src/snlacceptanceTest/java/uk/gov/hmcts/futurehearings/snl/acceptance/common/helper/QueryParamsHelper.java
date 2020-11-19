package uk.gov.hmcts.futurehearings.snl.acceptance.common.helper;

import java.util.HashMap;
import java.util.Map;

public class QueryParamsHelper {

    public final static Map<String, String> buildQueryParams(final String paramKey, final String paramValue) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey, paramValue);
        return queryParams;
    }

    public final static Map<String, String> buildQueryParams(final String paramKey, final String paramValue,
                                                             final String paramKey1, final String paramValue1) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey, paramValue);
        queryParams.put(paramKey1, paramValue1);
        return queryParams;
    }

    public final static Map<String, String> buildQueryParams(final String paramKey1, final String paramValue1,
                                                             final String paramKey2, final String paramValue2,
                                                             final String paramKey3, final String paramValue3) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey1, paramValue1);
        queryParams.put(paramKey2, paramValue2);
        queryParams.put(paramKey3, paramValue3);
        return queryParams;
    }

    public final static Map<String, String> buildQueryParams(final String paramKey1, final String paramValue1,
                                                             final String paramKey2, final String paramValue2,
                                                             final String paramKey3, final String paramValue3,
                                                             final String paramKey4, final String paramValue4) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey1, paramValue1);
        queryParams.put(paramKey2, paramValue2);
        queryParams.put(paramKey3, paramValue3);
        queryParams.put(paramKey4, paramValue4);
        return queryParams;
    }

    public final static Map<String, String> buildQueryParams(final String paramKey1, final String paramValue1,
                                                             final String paramKey2, final String paramValue2,
                                                             final String paramKey3, final String paramValue3,
                                                             final String paramKey4, final String paramValue4,
                                                             final String paramKey5, final String paramValue5) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey1, paramValue1);
        queryParams.put(paramKey2, paramValue2);
        queryParams.put(paramKey3, paramValue3);
        queryParams.put(paramKey4, paramValue4);
        queryParams.put(paramKey5, paramValue5);
        return queryParams;
    }

    public final static Map<String, String> buildQueryParams(final String paramKey1, final String paramValue1,
                                                             final String paramKey2, final String paramValue2,
                                                             final String paramKey3, final String paramValue3,
                                                             final String paramKey4, final String paramValue4,
                                                             final String paramKey5, final String paramValue5,
                                                             final String paramKey6, final String paramValue6) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey1, paramValue1);
        queryParams.put(paramKey2, paramValue2);
        queryParams.put(paramKey3, paramValue3);
        queryParams.put(paramKey4, paramValue4);
        queryParams.put(paramKey5, paramValue5);
        queryParams.put(paramKey6, paramValue6);
        return queryParams;
    }

    public final static Map<String, String> buildQueryParams(final String paramKey1, final String paramValue1,
                                                             final String paramKey2, final String paramValue2,
                                                             final String paramKey3, final String paramValue3,
                                                             final String paramKey4, final String paramValue4,
                                                             final String paramKey5, final String paramValue5,
                                                             final String paramKey6, final String paramValue6,
                                                             final String paramKey7, final String paramValue7) {
        final Map<String, String>  queryParams = new HashMap<>();
        queryParams.put(paramKey1, paramValue1);
        queryParams.put(paramKey2, paramValue2);
        queryParams.put(paramKey3, paramValue3);
        queryParams.put(paramKey4, paramValue4);
        queryParams.put(paramKey5, paramValue5);
        queryParams.put(paramKey6, paramValue6);
        queryParams.put(paramKey7, paramValue7);
        return queryParams;
    }
}
