package uk.gov.hmcts.futurehearings.hmi.acceptance.sessions.helper;

import java.util.HashMap;
import java.util.Map;

public class SessionsParamsHelper {

    public static Map<String, String> buildValidRetrieveScheduleParams() {
        final Map<String, String>  retrieveScheduleParams = new HashMap<>();
        retrieveScheduleParams.put("hearing_venue_id ", "234");

        return retrieveScheduleParams;
    }
}
