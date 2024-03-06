package ma.digital.prospace.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "transaction_id", length = 50, nullable = false)
    private String transactionId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @NotNull
    @Size(max = 50)
    @Column(name = "device_token", length = 50, nullable = false)
    private String deviceToken;

    @NotNull
    @Size(max = 500)
    @Column(name = "json_data", length = 500, nullable = false)
    private String jsonData;


    @ManyToOne
    @JoinColumn(name = "association", nullable = false)
    private Association association;

}
