package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DirigeantPNDTO2 {
    @JsonProperty("TypePiece")
    private String typePiece;

    @JsonProperty("NumPiece")
    private String numPiece;

    @JsonProperty("Nom")
    private String nom;

    @JsonProperty("Prenom")
    private String prenom;

    @JsonProperty("Qualite")
    private String qualite;

    @JsonProperty("DateDebut")
    private String dateDebut;

    @JsonProperty("Entreprise")
    private EntrepriseDTO11 entreprise;

    public String getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public String getNumPiece() {
        return numPiece;
    }

    public void setNumPiece(String numPiece) {
        this.numPiece = numPiece;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public EntrepriseDTO11 getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO11 entreprise) {
        this.entreprise = entreprise;
    }
}
