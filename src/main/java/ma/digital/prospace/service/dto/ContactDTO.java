package ma.digital.prospace.service.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Component
public class ContactDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;


    @NotNull(message = "Le champ 'deviceToken' ne peut pas être nul")
    @Size(max = 255, message = "Le champ 'deviceToken' ne peut pas dépasser {max} caractères")
    private String deviceToken;

    @NotNull(message = "Le champ 'deviceOS' ne peut pas être nul")
    @Size(max = 50, message = "Le champ 'deviceOS' ne peut pas dépasser {max} caractères")
    private String deviceOS;

    @NotNull(message = "Le champ 'deviceVersion' ne peut pas être nul")
    @Size(max = 50, message = "Le champ 'deviceVersion' ne peut pas dépasser {max} caractères")
    private String deviceVersion;

    // Getters et Setters pour les attributs





    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                ", deviceToken='" + deviceToken + '\'' +
                ", deviceOS='" + deviceOS + '\'' +
                ", deviceVersion='" + deviceVersion + '\'' +
                '}';
    }
}
