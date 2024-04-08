package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.enumeration.TypePiece;

public class CnieInfo {
    private Long subId;
    private String cinCs;
    private TypePiece typeIdentite;
    private String nomAr;
    private String nomFr;
    private String prenomAr;
    private String prenomFr;
    private String adresse;
    private String photo;

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }
    public String getCinCs() {
        return cinCs;
    }

    public void setCinCs(String cinCs) {
        this.cinCs = cinCs;
    }

    public TypePiece getTypeIdentite() {
        return typeIdentite;
    }

    public void setTypeIdentite(TypePiece typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    public String getNomAr() {
        return nomAr;
    }

    public void setNomAr(String nomAr) {
        this.nomAr = nomAr;
    }

    public String getNomFr() {
        return nomFr;
    }

    public void setNomFr(String nomFr) {
        this.nomFr = nomFr;
    }

    public String getPrenomAr() {
        return prenomAr;
    }

    public void setPrenomAr(String prenomAr) {
        this.prenomAr = prenomAr;
    }

    public String getPrenomFr() {
        return prenomFr;
    }

    public void setPrenomFr(String prenomFr) {
        this.prenomFr = prenomFr;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
