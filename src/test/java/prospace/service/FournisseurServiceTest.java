package prospace.service;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.repository.FournisseurServiceRepository;
import ma.digital.prospace.service.FournisseurServiceService;
import ma.digital.prospace.service.dto.FournisseurServiceDTO;
import ma.digital.prospace.service.mapper.FournisseurServiceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
public class FournisseurServiceTest {
    @Mock
    private FournisseurServiceRepository fournisseurServiceRepository;

    @Mock
    private FournisseurServiceMapper fournisseurServiceMapper;

    @InjectMocks
    private FournisseurServiceService fournisseurServiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFournisseurService() {
        FournisseurServiceDTO fournisseurServiceDTO = new FournisseurServiceDTO();
        FournisseurService fournisseurService = new FournisseurService();

        when(fournisseurServiceMapper.toEntity(fournisseurServiceDTO)).thenReturn(fournisseurService);
        when(fournisseurServiceRepository.save(fournisseurService)).thenReturn(fournisseurService);
        when(fournisseurServiceMapper.toDto(fournisseurService)).thenReturn(fournisseurServiceDTO);

        FournisseurServiceDTO savedDTO = fournisseurServiceService.save(fournisseurServiceDTO);

        assertEquals(fournisseurServiceDTO, savedDTO);
        verify(fournisseurServiceRepository, times(1)).save(fournisseurService);
    }

    @Test
    void testUpdateFournisseurService() {
        FournisseurServiceDTO fournisseurServiceDTO = new FournisseurServiceDTO();
        fournisseurServiceDTO.setId("1");
        FournisseurService fournisseurService = new FournisseurService();

        when(fournisseurServiceMapper.toEntity(fournisseurServiceDTO)).thenReturn(fournisseurService);
        when(fournisseurServiceRepository.save(fournisseurService)).thenReturn(fournisseurService);
        when(fournisseurServiceMapper.toDto(fournisseurService)).thenReturn(fournisseurServiceDTO);

        FournisseurServiceDTO updatedDTO = fournisseurServiceService.update(fournisseurServiceDTO);

        assertEquals(fournisseurServiceDTO, updatedDTO);
        verify(fournisseurServiceRepository, times(1)).save(fournisseurService);
    }

    @Test
    void testFindAllFournisseurServices() {
        // Given
        FournisseurService fournisseurService = new FournisseurService();
        fournisseurService.setId("1");
        List<FournisseurService> fournisseurServiceList = Collections.singletonList(fournisseurService);

        when(fournisseurServiceRepository.findAll()).thenReturn(fournisseurServiceList);

        // Mapping FournisseurService to FournisseurServiceDTO
        List<FournisseurServiceDTO> fournisseurServiceDTOList = fournisseurServiceList.stream()
                .map(fournisseurServiceMapper::toDto)
                .collect(Collectors.toList());

        // When
        List<FournisseurServiceDTO> result = fournisseurServiceService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(fournisseurServiceDTOList, result);
    }


    @Test
    void testFindAllFournisseurServices_EmptyList() {
        when(fournisseurServiceRepository.findAll()).thenReturn(Collections.emptyList());

        List<FournisseurServiceDTO> result = fournisseurServiceService.findAll();

        assertEquals(0, result.size());
    }

    // Test other methods similarly...
}
