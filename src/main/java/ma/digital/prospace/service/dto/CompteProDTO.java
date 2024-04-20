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

