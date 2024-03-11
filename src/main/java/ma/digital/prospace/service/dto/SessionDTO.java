package ma.digital.prospace.service.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private String transactionId;
    private Date createdAt;
    private String jsonData;


}
