package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DomiciliationDTO {
    @JsonProperty("DateDebut")
    private String dateDebut;

    @JsonProperty("DateFin")
    private String dateFin;

    @JsonProperty("Domiciliataire")
    private String domiciliataire;

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDomiciliataire() {
        return domiciliataire;
    }

    public void setDomiciliataire(String domiciliataire) {
        this.domiciliataire = domiciliataire;
    }
}
