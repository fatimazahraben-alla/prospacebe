package ma.digital.prospace.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class InvitationDTO {
    private Long id;
    private String token;
    private String typeIdentifiantTo;
    private String identifiantTo;
    private String object;
    private Date dateCreation;
    private Date dateFin;
    private String status;



}

