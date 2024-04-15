package ma.digital.prospace.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String GESTIONNAIRE_ENTREPRISE = "ROLE_GESTIONNAIREENTREPRISE";

   public static final String GESTIONNAIRE_ESPACE = "ROLE_GESTIONNAIREESPACE";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
