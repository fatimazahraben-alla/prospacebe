package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DirigeantPMDTO2 {
    @JsonProperty("NomComplet")
    private String nomComplet;

    @JsonProperty("Qualite")
    private String qualite;

    @JsonProperty("DateDebut")
    private String dateDebut;

    @JsonProperty("Representant")
    private List<RepresentantDTO> representant; // Changer le nom du champ pour correspondre au JSON

    @JsonProperty("Entreprise")
    private EntrepriseDTO11 entreprise;


    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getQualite() {
        return qualite;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public List<RepresentantDTO> getRepresentant() {
        return representant;
    }

    public void setRepresentant(List<RepresentantDTO> representant) {
        this.representant = representant;
    }

    public EntrepriseDTO11 getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO11 entreprise) {
        this.entreprise = entreprise;
    }
}
