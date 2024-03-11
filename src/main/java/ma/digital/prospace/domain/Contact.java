package ma.digital.prospace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name = "contact")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @NotNull
    @Size(max =50)
    @Column(name = "mail", length = 50, nullable = false)
    private String mail;

    @NotNull
    @Size(max =50)
    @Column(name = "telephone", length = 50, nullable = false)
    private String telephone;

    @NotNull
    @Size(max =50)
    @Column(name = "deviceToken", length = 50, nullable = false)
    private String deviceToken;


    @NotNull
    @Size(max =50)
    @Column(name = "deviceOS", length = 50, nullable = false)
    private String deviceOS;


    @NotNull
    @Size(max =50)
    @Column(name = "deviceVersion", length = 50, nullable = false)
    private String deviceVersion;


    @OneToOne
    @JoinColumn(name = "compte_pro", referencedColumnName = "id", nullable = false) // Ajoutez cette annotation pour mapper la relation avec ComptePro
    @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
    private ComptePro comptePro;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

    public ComptePro getComptePro() {
        return comptePro;
    }

    public void setComptePro(ComptePro comptePro) {
        this.comptePro = comptePro;
    }
}
