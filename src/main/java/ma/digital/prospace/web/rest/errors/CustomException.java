package ma.digital.prospace.web.rest.errors;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
