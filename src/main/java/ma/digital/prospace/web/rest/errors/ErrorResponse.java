package ma.digital.prospace.web.rest.errors;

public class ErrorResponse {
    private String message;
    private String errorCode;

    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    // Getters et setters

}
