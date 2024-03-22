package ma.digital.prospace.web.RestTemplate;

import ma.digital.prospace.service.dto.EntrepriseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/*
@Component
public class MinistryOfJusticeClient {

    @Value("${ministryOfJustice.url}")
    private String ministryOfJusticeUrl;

    private final RestTemplate restTemplate;

    public MinistryOfJusticeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EntrepriseDTO findEntrepriseById(Long id) {
        String url = ministryOfJusticeUrl + "/entreprise/{id}";
        return restTemplate.getForObject(url, EntrepriseDTO.class, id);
    }
  // {
     //   String url = ministryOfJusticeUrl + "/companyManagements";
     //   CompanyManagement[] companyManagements = restTemplate.getForObject(url, CompanyManagement[].class);
       // return Arrays.asList(companyManagements);
   // }

}
*/
