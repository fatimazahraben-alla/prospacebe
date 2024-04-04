package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.enumeration.Statut;
import ma.digital.prospace.domain.enumeration.typeidentifiant;

public class EntrepriseRequest2 {

    private String tribunal;
    private String numeroRC;

    private String ice;

    private Long COMPID;

    private Statut Perphysique_Permorale;







    public String getTribunal() {
        return tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

    public String getNumeroRC() {
        return numeroRC;
    }

    public void setNumeroRC(String numeroRC) {
        this.numeroRC = numeroRC;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public Long getCOMPID() {
        return COMPID;
    }

    public void setCOMPID(Long COMPID) {
        this.COMPID = COMPID;
    }

    public Statut getPerphysique_Permorale() {
        return Perphysique_Permorale;
    }

    public void setPerphysique_Permorale(Statut perphysique_Permorale) {
        Perphysique_Permorale = perphysique_Permorale;
    }


}
