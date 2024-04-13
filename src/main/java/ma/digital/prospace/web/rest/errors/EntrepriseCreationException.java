package ma.digital.prospace.web.rest.errors;

public class EntrepriseCreationException extends Exception {
    private String errorCode;  // Optionnel, pour des codes d'erreur spécifiques

    public EntrepriseCreationException(String message) {
        super(message);
    }

    public EntrepriseCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntrepriseCreationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
