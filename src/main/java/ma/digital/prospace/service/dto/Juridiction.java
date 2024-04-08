package ma.digital.prospace.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Juridiction {

    @JsonProperty("Code")
    private String code;

    @JsonProperty("LibelleAr")
    private String libelleAr;

    @JsonProperty("LibelleFr")
    private String libelleFr;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelleAr() {
        return libelleAr;
    }

    public void setLibelleAr(String libelleAr) {
        this.libelleAr = libelleAr;
    }

    public String getLibelleFr() {
        return libelleFr;
    }

    public void setLibelleFr(String libelleFr) {
        this.libelleFr = libelleFr;
    }
}
