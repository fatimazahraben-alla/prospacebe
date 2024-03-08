package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;

import java.util.List;

public class AuthenticationResponse {

    private final Entreprise entreprise;
    private final ComptePro comptePro;
    private final List<String> roles;

    // Constructor
    public AuthenticationResponse(Entreprise entreprise, ComptePro comptePro, List<String> roles) {
        this.entreprise = entreprise;
        this.comptePro = comptePro;
        this.roles = roles;
    }

    // Getters
    public Entreprise getEntreprise() {
        return entreprise;
    }

    public ComptePro getComptePro() {
        return comptePro;
    }

    public List<String> getRoles() {
        return roles;
    }

    // Since the fields are final and set through the constructor, setters are not provided.
    // If you need to change the values, you would create a new instance of this class.
}
