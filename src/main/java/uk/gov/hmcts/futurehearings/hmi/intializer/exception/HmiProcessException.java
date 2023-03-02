package uk.gov.hmcts.futurehearings.hmi.intializer.exception;

public class HmiProcessException extends Exception {

    private static final long serialVersionUID = 1L;

    public HmiProcessException() {
        super();
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public HmiProcessException(String message) {
        super(message);
    }
}
