package ma.digital.prospace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "contact")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @NotNull
    @Size(max =50)
    @Column(name = "mail", length = 50, nullable = false)
    private String mail;
    private String telephone;
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


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
    @JoinColumn(name = "compte_pro_id", referencedColumnName = "id", nullable = false) // Ajoutez cette annotation pour mapper la relation avec ComptePro
    @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
    private ComptePro comptePro;


    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }



    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", deviceOS='" + deviceOS + '\'' +
                ", deviceVersion='" + deviceVersion + '\'' +
                '}';
    }
}
