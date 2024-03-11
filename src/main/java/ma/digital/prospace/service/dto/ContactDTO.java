package ma.digital.prospace.service.dto;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Component
@Data
public class ContactDTO {

 @NotNull
 @Size(max = 50, message = "Le champ 'deviceVersion' ne peut pas dépasser {max} caractères")
 private Long COMPID;

    @NotNull(message = "Le champ 'deviceToken' ne peut pas être nul")
    @Size(max = 255, message = "Le champ 'deviceToken' ne peut pas dépasser {max} caractères")
    private String deviceToken;

    @NotNull(message = "Le champ 'deviceOS' ne peut pas être nul")
    @Size(max = 50, message = "Le champ 'deviceOS' ne peut pas dépasser {max} caractères")
    private String deviceOS;

    @NotNull(message = "Le champ 'deviceVersion' ne peut pas être nul")
    @Size(max = 50, message = "Le champ 'deviceVersion' ne peut pas dépasser {max} caractères")
    private String deviceVersion;






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

 public Long getCOMPID() {
  return COMPID;
 }

 public void setCOMPID(Long COMPID) {
  this.COMPID = COMPID;
 }
}
