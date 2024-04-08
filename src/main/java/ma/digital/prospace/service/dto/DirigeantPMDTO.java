package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DirigeantPMDTO {
    @JsonProperty("NomComplet")
    private String nomComplet;

    @JsonProperty("Qualite")
    private String qualite;

    @JsonProperty("DateDebut")
    private String dateDebut;

    @JsonProperty("Representant")
    private List<RepresentantDTO> representants;

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

    public List<RepresentantDTO> getRepresentants() {
        return representants;
    }

    public void setRepresentants(List<RepresentantDTO> representants) {
        this.representants = representants;
    }


}
