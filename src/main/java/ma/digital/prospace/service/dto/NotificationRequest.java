package ma.digital.prospace.service.dto;

import java.util.List;
import lombok.Data;

import java.util.Map;
@Data
public class NotificationMessage {
    private String deviceToken;
    private String transactionID;
    private Long fs;
    private Long compteID;
    private List<String> entrepriseList;
    private String Title;
    private String body;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Long getFs() {
        return fs;
    }

    public void setFs(Long fs) {
        this.fs = fs;
    }

    public Long getCompteID() {
        return compteID;
    }

    public void setCompteID(Long compteID) {
        this.compteID = compteID;
    }

    public List<String> getEntrepriseList() {
        return entrepriseList;
    }

    public void setEntrepriseList(List<String> entrepriseList) {
        this.entrepriseList = entrepriseList;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
