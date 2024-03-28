package ma.digital.prospace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ma.digital.prospace.service.dto.DIRIGEANTDTO;
import ma.digital.prospace.service.dto.DirigeantPMDTO2;
import ma.digital.prospace.service.dto.EntrepriseWSMJ;
import ma.digital.prospace.service.dto.IdentificationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntrepriseWSMJService {

    @Value("https://4f0b2fbc-c27b-40dc-973a-ff95f3930be0.mock.pstmn.io/wsRcPmModel7/PersonneMorale/1/1/ADD")
    private String entrepriseApiUrl;

    @Value("https://d6ea7a3e-8efd-41f2-852b-d3b7da86c863.mock.pstmn.io/wsRcPmModel7/DirigeantsPM")
    private String entrepriseApiUrl2;

    private final RestTemplate restTemplate;

    public EntrepriseWSMJService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EntrepriseWSMJ getEntrepriseByJuridictionAndNumRC(String codeJuridiction, String numRC) {
        String url = "https://4f0b2fbc-c27b-40dc-973a-ff95f3930be0.mock.pstmn.io/wsRcPmModel7/PersonneMorale/1/1/ADD";
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

    public DIRIGEANTDTO getDirigeantBycodeJuridictionAndnumRC(String codeJuridiction, String numRC, String codePartenaire) {
        String url = "https://d6ea7a3e-8efd-41f2-852b-d3b7da86c863.mock.pstmn.io/wsRcPmModel7/DirigeantsPM/1/1/1";
         System.out.println("Attempting to call API URL: " + url);

         ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
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


}







