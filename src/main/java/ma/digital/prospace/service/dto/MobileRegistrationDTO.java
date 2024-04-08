package ma.digital.prospace.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class MobileRegistrationDTO {
    @NotNull(message = "Le champ 'deviceToken' ne peut pas être nul")
    @Size(max = 1000, message = "Le champ 'deviceToken' ne peut pas dépasser {max} caractères")
    private String deviceToken;

    @NotNull(message = "Le champ 'deviceOS' ne peut pas être nul")
    @Size(max = 50, message = "Le champ 'deviceOS' ne peut pas dépasser {max} caractères")
    private String deviceOS;

    @NotNull(message = "Le champ 'deviceVersion' ne peut pas être nul")
    @Size(max = 50, message = "Le champ 'deviceVersion' ne peut pas dépasser {max} caractères")
    private String deviceVersion;

    public UUID getCompteId() {
        return compteId;
    }

    public void setCompteId(UUID compteId) {
        this.compteId = compteId;
    }

    @NotNull
    private UUID compteId;






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
