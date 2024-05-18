package ma.digital.prospace.service.dto;

import jakarta.persistence.Column;
import ma.digital.prospace.domain.enumeration.StatutCompte;
import ma.digital.prospace.domain.enumeration.typeidentifiant;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ma.digital.prospace.domain.ComptePro} entity.
 */
@Component
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompteProDTO implements Serializable {

    private String id;


    @NotNull
    @Size(max = 10)
    private String identifiant;

    @NotNull
    @Size(max = 25)
    private String typeIdentifiant;

    @NotNull
    @Size(max = 50)
    private String nomAr;

    @NotNull
    @Size(max = 50)
    private String nomFr;

    @NotNull
    @Size(max = 50)
    private String prenomAr;

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getTypeIdentifiant() {
        return typeIdentifiant;
    }

    public void setTypeIdentifiant(String typeIdentifiant) {
        this.typeIdentifiant = typeIdentifiant;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @NotNull
    @Size(max = 50)
    private String prenomFr;

    @Size(max = 50)
    private String address;

    private String photo;
    private Date createdAt;

    private Date updatedAt;

    private boolean deleted;
    private StatutCompte statut;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public StatutCompte getStatut() {
        return statut;
    }

    public void setStatut(StatutCompte statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteProDTO)) {
            return false;
        }

        CompteProDTO comptePro = (CompteProDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, comptePro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

}