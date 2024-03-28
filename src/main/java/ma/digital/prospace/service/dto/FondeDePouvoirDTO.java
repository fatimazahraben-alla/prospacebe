package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FondeDePouvoirDTO {
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
}
