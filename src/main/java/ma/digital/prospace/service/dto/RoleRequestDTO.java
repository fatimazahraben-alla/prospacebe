package ma.digital.prospace.service.dto;

import java.util.UUID;

public class RoleRequestDTO {
    private String compteId;
    private UUID entrepriseId;
    private UUID roleId;

    // Getters and setters

    public String getCompteId() {
        return compteId;
    }

    public void setCompteId(String compteId) {
        this.compteId = compteId;
    }

    public UUID getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(UUID entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}
