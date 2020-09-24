package uk.gov.hmcts.futurehearings.hmi.intializer.exception;

public class HMIProcessException extends Exception {

    public HMIProcessException () {}

    public HMIProcessException (String message) {
        super(message);
    }
}
