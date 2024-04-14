package ma.digital.prospace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.digital.prospace.service.dto.DIRIGEANTDTO;
import ma.digital.prospace.service.dto.EntrepriseWSMJ;
import ma.digital.prospace.service.dto.PersonnephysiqueDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Service
public class EntrepriseWSMJService {
    @Value("${mj.company.url}")
    public String companyUrl;

    @Value("${mj.Dirigeants.url}")
    public String dirigeantUrl;

    @Value("${mj.PersonnePhysique.url}")
    public String PersonnePhysiqueUrl;
    private final Logger log = LoggerFactory.getLogger( EntrepriseWSMJService.class);
    private static final Logger auditLogger1 = LoggerFactory.getLogger("ma.digital.prospace.audit");

    private final RestTemplate restTemplate;

    public EntrepriseWSMJService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EntrepriseWSMJ getEntrepriseByJuridictionAndNumRC(String codeJuridiction, String numRC) {
        String url = companyUrl + codeJuridiction + "/" + numRC;
        auditLogger1.info("Attempting to call API URL: {}", url);

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            auditLogger1.error("Failed to retrieve data from API: {}", e.getMessage());
            return null; // Renvoie directement null en cas d'erreur réseau ou autre exception
        }

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            auditLogger1.error("API call returned non-OK status: {}", responseEntity.getStatusCode());
            return null; // Renvoie null si le statut HTTP n'est pas 200 OK
        }

        String jsonString = responseEntity.getBody();
        if (jsonString == null || jsonString.trim().isEmpty()) {
            auditLogger1.info("Received empty or null response from API for URL: {}", url);
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, EntrepriseWSMJ.class);
        } catch (IOException e) {
            auditLogger1.error("Error parsing JSON from response", e);
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
        String url = PersonnePhysiqueUrl + codeJuridiction + "/" + numRC;
        auditLogger1.info("Attempting to call API URL: {}", url);

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, String.class);
        } catch (RestClientException e) {
            auditLogger1.error("Failed to fetch data from API for codeJuridiction: {}, numRC: {}. Error: {}", codeJuridiction, numRC, e.getMessage());
            return null;
        }

        String jsonString = responseEntity.getBody();
        if (jsonString == null || jsonString.trim().isEmpty()) {
            auditLogger1.warn("Empty JSON response received for codeJuridiction: {}, numRC: {}", codeJuridiction, numRC);
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PersonnephysiqueDTO personnephysiqueDTO = objectMapper.readValue(jsonString, PersonnephysiqueDTO.class);
            auditLogger1.info("Successfully parsed JSON for codeJuridiction: {}, numRC: {}", codeJuridiction, numRC);
            return personnephysiqueDTO;
        } catch (IOException e) {
            auditLogger1.error("Error parsing JSON for codeJuridiction: {}, numRC: {}. JSON: {}", codeJuridiction, numRC, jsonString, e);
            return null;
        }
    }



}







