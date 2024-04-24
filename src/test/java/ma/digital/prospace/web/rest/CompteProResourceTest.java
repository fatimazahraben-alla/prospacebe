package ma.digital.prospace.web.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ma.digital.prospace.service.CompteProService;
import ma.digital.prospace.service.dto.CompteProDTO;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CompteProResourceTest {

    private MockMvc mockMvc;

    @Mock
    private CompteProService compteProService;

    @InjectMocks
    private CompteProResource compteProResource;

    private CompteProDTO compteProDTO;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(compteProResource).build();
        compteProDTO = new CompteProDTO();
        compteProDTO.setId("testId");
    }

    @Test
    public void testCreateComptePro() throws Exception {
        when(compteProService.createAccountWithMobileRegistration(any())).thenReturn(compteProDTO);

        mockMvc.perform(post("/api/compte-pros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"testId\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(compteProDTO.getId()));
    }

    @Test
    public void testUpdateComptePro() throws Exception {
        when(compteProService.update(any(CompteProDTO.class))).thenReturn(compteProDTO);

        mockMvc.perform(put("/api/compte-pros/testId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"testId\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(compteProDTO.getId()));
    }

    @Test
    public void testDeleteComptePro() throws Exception {
        doNothing().when(compteProService).delete(anyString());

        mockMvc.perform(delete("/api/compte-pros/testId"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetComptePro() throws Exception {
        when(compteProService.findOne(anyString())).thenReturn(Optional.of(compteProDTO));

        mockMvc.perform(get("/api/compte-pros/testId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(compteProDTO.getId()));
    }

    // Ajoutez d'autres méthodes de test ici...
}
