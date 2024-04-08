package ma.digital.prospace.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.Association;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.Session;
import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.SessionRepository;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.dto.EntrepriseDTO;
import ma.digital.prospace.service.dto.CompteEntrepriseDTO;
import ma.digital.prospace.service.dto.CompteFSAssociationDTO;
import ma.digital.prospace.service.mapper.AssociationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@SpringBootTest
public class AssociationServiceTest {

    @Mock
    private AssociationRepository associationRepository;
    @Mock
    private AssociationMapper associationMapper;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AssociationService associationService;

    private Association association;
    private AssociationDTO associationDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        association = new Association();
        associationDTO = new AssociationDTO();
        // Configure the mocks for a default behavior
        when(associationMapper.toEntity(any(AssociationDTO.class))).thenReturn(association);
        when(associationMapper.toDto(any(Association.class))).thenReturn(associationDTO);
    }

    @Test
    public void testSaveAssociation() {
        when(associationRepository.save(any(Association.class))).thenReturn(association);
        AssociationDTO result = associationService.save(new AssociationDTO());
        assertNotNull(result);
        verify(associationRepository).save(any(Association.class));
    }

    @Test
    public void testDeleteAssociation() {
        doNothing().when(associationRepository).deleteById(anyLong());
        associationService.delete(1L);
        verify(associationRepository).deleteById(anyLong());
    }

    @Test
    public void testFindAllAssociations() {
        Page<Association> page = new PageImpl<>(Collections.singletonList(association));
        when(associationRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<AssociationDTO> result = associationService.findAll(Pageable.unpaged());
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(associationRepository).findAll(any(Pageable.class));
    }

    @Test
    public void testUpdateAssociation() {
        when(associationRepository.save(any(Association.class))).thenReturn(association);
        AssociationDTO result = associationService.update(new AssociationDTO());
        assertNotNull(result);
        verify(associationRepository).save(any(Association.class));
    }

    @Test
    public void testFindOneAssociation() {
        when(associationRepository.findById(anyLong())).thenReturn(Optional.of(association));
        Optional<AssociationDTO> result = associationService.findOne(1L);
        assertTrue(result.isPresent());
        verify(associationRepository).findById(anyLong());
    }

    @Test
    public void testProcessAuthenticationStep2Success() {
        when(associationRepository.findAllByFsAndCompteID(anyLong(), anyLong())).thenReturn(Collections.singletonList(association));
        Session session = new Session();
        session.setStatus(Session.Status.IN_PROGRESS);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(contactRepository.findByCompteProId(anyLong())).thenReturn(new Contact());
        CompteFSAssociationDTO result = associationService.processAuthenticationStep2(1L, 2L, "txn123");
        assertNotNull(result);
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    public void testProcessAuthenticationStep2NoAssociations() {
        when(associationRepository.findAllByFsAndCompteID(anyLong(), anyLong())).thenReturn(Collections.emptyList());
        CompteFSAssociationDTO result = associationService.processAuthenticationStep2(1L, 2L, "txn123");
        assertNull(result);
        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    public void testPushCompteEntreprise() throws JsonProcessingException {
        CompteEntrepriseDTO compteEntrepriseDTO = new CompteEntrepriseDTO();
        compteEntrepriseDTO.setTransactionId("txn123");
        Session session = new Session();
        session.setTransactionId(compteEntrepriseDTO.getTransactionId());
        when(sessionRepository.findByTransactionId(anyString())).thenReturn(Optional.of(session));
        when(objectMapper.writeValueAsString(any())).thenReturn("{}"); // Simplify the JSON string

        assertDoesNotThrow(() -> associationService.pushCompteEntreprise(compteEntrepriseDTO));
        verify(sessionRepository).save(any(Session.class));
        verify(objectMapper).writeValueAsString(any());
    }
    @Test
    public void testCheckAuthenticationStep2Success() throws JsonProcessingException {
        String transactionId = "txn123";
        String jsonData = "{\"comptePro\":{\"id\":1},\"entreprise\":{\"id\":2},\"roles\":[\"ROLE_USER\"]}";
        Session session = new Session();
        session.setStatus(Session.Status.COMPLETED);
        session.setJsonData(jsonData);

        when(sessionRepository.findByTransactionId(transactionId)).thenReturn(Optional.of(session));
        when(objectMapper.readValue(eq(jsonData), any(TypeReference.class))).thenReturn(new HashMap<String, Object>() {{
            put("comptePro", new CompteProDTO());
            put("entreprise", new EntrepriseDTO());
            put("roles", Collections.singletonList("ROLE_USER"));
        }});

        CompteEntrepriseDTO result = associationService.checkAuthenticationStep2(transactionId);

        assertNotNull(result);
        assertNotNull(result.getComptePro());
        assertNotNull(result.getEntreprise());
        assertFalse(result.getRoles().isEmpty());
        verify(sessionRepository).findByTransactionId(transactionId);
        verify(objectMapper).readValue(eq(jsonData), any(TypeReference.class));
    }

    @Test
    public void testCheckAuthenticationStep2Failure() {
        String invalidTransactionId = "invalid_txn";
        when(sessionRepository.findByTransactionId(invalidTransactionId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> associationService.checkAuthenticationStep2(invalidTransactionId));
        assertTrue(thrown.getMessage().contains("Session not completed or not found"));
    }

}

