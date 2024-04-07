package ma.digital.prospace.domain.enumeration;

public enum Role {
    ADMIN_PRO_SPACE,
    GESTIONNAIRE_ENTREPRISE,
    GESTIONNAIRE_ESPACE_PRO;

    public String getFormattedName() {
        return this.name().replace("_", " ");
    }

}
