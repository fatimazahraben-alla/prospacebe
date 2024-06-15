package ma.digital.prospace.web.rest.errors;

public class ErrorResponse {
    private String message;
    private int statusCode;
    private String error;
    private String detail;

    // Constructors
    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, int statusCode, String error, String detail) {
        this.message = message;
        this.statusCode = statusCode;
        this.error = error;
        this.detail = detail;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
