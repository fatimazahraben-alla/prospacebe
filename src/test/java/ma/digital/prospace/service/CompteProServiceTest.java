package ma.digital.prospace.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.mapper.CompteProMapper;
import ma.digital.prospace.domain.ComptePro;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CompteProServiceTest {

    @Mock
    private CompteProRepository compteProRepository;

    @Mock
    private CompteProMapper compteProMapper;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private FirebaseNotificationService firebaseNotificationService;

    @Mock
    private com.google.firebase.messaging.FirebaseMessaging firebaseMessaging;

    @Mock
    private ProcurationRepository procurationRepository;

    private CompteProService compteProService;

    @BeforeEach
    void setUp() {
        // Pass all the mocked dependencies to the service
        compteProService = new CompteProService(
                compteProRepository,
                compteProMapper,
                contactRepository,
                firebaseNotificationService,
                firebaseMessaging,
                procurationRepository
        );
    }

    @Test
    void testCreateComptePro() {
        // Given
        CompteProDTO compteProDTO = new CompteProDTO();
        compteProDTO.setId("testId");

        ComptePro comptePro = new ComptePro();
        comptePro.setId(compteProDTO.getId());

        when(compteProMapper.toEntity(any(CompteProDTO.class))).thenReturn(comptePro);
        when(compteProRepository.save(any(ComptePro.class))).thenReturn(comptePro);
        when(compteProMapper.toDto(any(ComptePro.class))).thenReturn(compteProDTO);

        // When
        CompteProDTO savedDTO = compteProService.save(compteProDTO);

        // Then
        assertEquals(compteProDTO.getId(), savedDTO.getId());
        verify(compteProRepository).save(comptePro);
    }

    @Test
    void testDeleteComptePro() {
        // Arrange
        String id = "testId";
        when(compteProRepository.existsById(id)).thenReturn(true);

        // Act
        compteProService.delete(id);

        // Assert
        verify(compteProRepository).deleteById(id);
    }
}
