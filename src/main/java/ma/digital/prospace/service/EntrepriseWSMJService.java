package ma.digital.prospace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.digital.prospace.service.dto.DIRIGEANTDTO;
import ma.digital.prospace.service.dto.EntrepriseWSMJ;
import ma.digital.prospace.service.dto.PersonnephysiqueDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class EntrepriseWSMJService {
    @Value("${mj.company.url}")
    public String companyUrl;

    @Value("${mj.Dirigeants.url}")
    public String dirigeantUrl;

    @Value("${mj.PersonnePhysique.url}")
    public String PersonnePhysiqueUrl;


    private final RestTemplate restTemplate;

    public EntrepriseWSMJService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EntrepriseWSMJ getEntrepriseByJuridictionAndNumRC(String codeJuridiction, String numRC) {

        String url = companyUrl+codeJuridiction+"/"+numRC;
        System.out.println("Attempting to call API URL: " + url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonString = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
      EntrepriseWSMJ entrepriseWSMJ;
        try {
           entrepriseWSMJ = objectMapper.readValue(jsonString, EntrepriseWSMJ.class);
            return entrepriseWSMJ;
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions en conséquence
            return null;
        }

    }

    public DIRIGEANTDTO getDirigeantBycodeJuridictionAndnumRC(String codeJuridiction, String numRC) {
            String url2 = dirigeantUrl+codeJuridiction+"/"+numRC;
         System.out.println("Attempting to call API URL: " + url2);

         ResponseEntity<String> responseEntity = restTemplate.getForEntity(url2, String.class);
         String jsonString = responseEntity.getBody();

        // Utilisation de Jackson pour mapper le JSON à l'objet DTO EntrepriseWSMJ
        ObjectMapper objectMapper = new ObjectMapper();
        DIRIGEANTDTO dirigeantDTO;
        try {
            dirigeantDTO = objectMapper.readValue(jsonString, DIRIGEANTDTO.class);
            return dirigeantDTO;
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions en conséquence
            return null;
        }

    }

    public PersonnephysiqueDTO getBycodeJuridictionAndnumRC(String codeJuridiction, String numRC) {
        String url3 = PersonnePhysiqueUrl+codeJuridiction+"/"+numRC;
        System.out.println("Attempting to call API URL: " + url3);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url3, String.class);
        String jsonString = responseEntity.getBody();

        // Utilisation de Jackson pour mapper le JSON à l'objet DTO EntrepriseWSMJ
        ObjectMapper objectMapper = new ObjectMapper();
        PersonnephysiqueDTO personnephysiqueDTO;
        try {
            personnephysiqueDTO = objectMapper.readValue(jsonString, PersonnephysiqueDTO.class);
            return personnephysiqueDTO;
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions en conséquence
            return null;
        }

    }


}






