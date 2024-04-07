package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommercantDto {

    @JsonProperty("TypePiece")
    private String typePiece;

    @JsonProperty("NumPiece")
    private String numPiece;

    @JsonProperty("Nom")
    private String nom;

    @JsonProperty("Prenom")
    private String prenom;

    @JsonProperty("Nationalite")
    private String nationalite;

    @JsonProperty("AdressePersonnelle")
    private String adressePersonnelle;

    // Getters and setters


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

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getAdressePersonnelle() {
        return adressePersonnelle;
    }

    public void setAdressePersonnelle(String adressePersonnelle) {
        this.adressePersonnelle = adressePersonnelle;
    }
}
