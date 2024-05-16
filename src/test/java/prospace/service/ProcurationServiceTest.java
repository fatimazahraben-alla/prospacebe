package prospace.service;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.ProcurationService;
import ma.digital.prospace.service.dto.ProcurationDTO;
import ma.digital.prospace.service.mapper.ProcurationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ProcurationServiceTest {

    @Mock
    private ProcurationRepository procurationRepository;

    @Mock
    private ProcurationMapper procurationMapper;

    @InjectMocks
    private ProcurationService procurationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProcuration() {
        ProcurationDTO procurationDTO = new ProcurationDTO();
        Procuration procuration = new Procuration();

        when(procurationMapper.toEntity(procurationDTO)).thenReturn(procuration);
        when(procurationRepository.save(procuration)).thenReturn(procuration);
        when(procurationMapper.toDto(procuration)).thenReturn(procurationDTO);

        ProcurationDTO savedDTO = procurationService.save(procurationDTO);

        assertEquals(procurationDTO, savedDTO);
        verify(procurationRepository, times(1)).save(procuration);
    }

    @Test
    void testUpdateProcuration() {
        ProcurationDTO procurationDTO = new ProcurationDTO();
        procurationDTO.setId(UUID.randomUUID());
        Procuration procuration = new Procuration();

        when(procurationMapper.toEntity(procurationDTO)).thenReturn(procuration);
        when(procurationRepository.save(procuration)).thenReturn(procuration);
        when(procurationMapper.toDto(procuration)).thenReturn(procurationDTO);

        ProcurationDTO updatedDTO = procurationService.update(procurationDTO);

        assertEquals(procurationDTO, updatedDTO);
        verify(procurationRepository, times(1)).save(procuration);
    }

    @Test
    void testFindAllProcurationsByUtilisateurPro() {
        String utilisateurProId = "utilisateurProId";
        Procuration procuration = new Procuration();
        ComptePro comptePro = new ComptePro();
        procuration.setGestionnaireEspacePro(comptePro);
        List<Procuration> procurations = Collections.singletonList(procuration);

        when(procurationRepository.findByUtilisateurProId(utilisateurProId)).thenReturn(procurations);

        List<ComptePro> result = procurationService.findAllProcurationsByUtilisateurPro(utilisateurProId);

        assertEquals(Collections.singletonList(comptePro), result);
    }

    // Test other methods similarly...
}