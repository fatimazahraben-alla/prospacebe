package ma.digital.prospace.service.dto;

import ma.digital.prospace.domain.enumeration.Statut;
import ma.digital.prospace.domain.enumeration.typeidentifiant;

import java.util.UUID;

public class EntrepriseRequest2 {

    private String tribunal;
    private String numeroRC;
    private typeidentifiant indentifianttype;
    private String CIN;
    private String COMPID;
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


    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }
    public typeidentifiant getIndentifianttype() {
        return indentifianttype;
    }

    public void setIndentifianttype(typeidentifiant indentifianttype) {
        this.indentifianttype = indentifianttype;
    }


    public String getCOMPID() {
        return COMPID;
    }

    public void setCOMPID(String COMPID) {
        this.COMPID = COMPID;
    }

    public Statut getPerphysique_Permorale() {
        return Perphysique_Permorale;
    }

    public void setPerphysique_Permorale(Statut perphysique_Permorale) {
        Perphysique_Permorale = perphysique_Permorale;
    }


}
