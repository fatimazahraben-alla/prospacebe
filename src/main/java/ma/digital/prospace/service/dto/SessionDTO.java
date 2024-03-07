package ma.digital.prospace.service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
public class SessionDTO {
    private String transactionId;
    private Date createdAt;
    private String jsonData;


}
