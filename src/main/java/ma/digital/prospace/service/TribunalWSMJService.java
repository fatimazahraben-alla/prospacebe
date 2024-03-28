package ma.digital.prospace.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.digital.prospace.service.dto.EntrepriseWSMJ;
import ma.digital.prospace.service.dto.Juridiction;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@Service
public class TribunalWSMJService {
    @Value("https://31926c9b-601b-40aa-9fca-023d373b15a4.mock.pstmn.io//wsRCPmModel7/ListJuridiction")
    private String tribunalUrl;

    private final RestTemplate restTemplate;

    public TribunalWSMJService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Juridiction> getListeTribunaux() {

        String url = "https://31926c9b-601b-40aa-9fca-023d373b15a4.mock.pstmn.io/wsRCPmModel7/ListJuridiction";
        System.out.println("Attempting to call API URL: " + url);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonString = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Juridiction> juridictions;
        try {
            juridictions = objectMapper.readValue(jsonString, new TypeReference<List<Juridiction>>() {});
            return juridictions;
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions en conséquence
            return null;
        }
    }

}




