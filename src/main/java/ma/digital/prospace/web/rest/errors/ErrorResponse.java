package ma.digital.prospace.web.rest.errors;

public class ErrorResponse {
    private String message;
    private int statusCode;
    private String error;
    private String detail;

    public ErrorResponse(String message, int statusCode, String error, String detail) {
        this.message = message;
        this.statusCode = statusCode;
        this.error = error;
        this.detail = detail;
    }
}
