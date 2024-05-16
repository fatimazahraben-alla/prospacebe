package prospace.service;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.CompteProService;
import ma.digital.prospace.service.ContactService;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.mapper.CompteProMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompteServiceTest {
    @Mock
    private CompteProRepository compteProRepository;

    @Mock
    private ProcurationRepository procurationRepository;

    @Mock
    private AssociationRepository associationRepository;
    @Mock
    private CompteProMapper compteProMapper;

    @Mock
    private ContactService contactRepository;
    @InjectMocks
    private CompteProService compteProService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        // Given
        CompteProDTO compteProDTO = new CompteProDTO();
        compteProDTO.setId("1");
        compteProDTO.setNomAr("John Doe");

        ComptePro comptePro = new ComptePro();
        comptePro.setId("1");
        comptePro.setNomAr("John Doe");

        when(compteProMapper.toEntity(compteProDTO)).thenReturn(comptePro);
        when(compteProRepository.save(any(ComptePro.class))).thenReturn(comptePro);
        when(compteProMapper.toDto(comptePro)).thenReturn(compteProDTO);

        // When
        CompteProDTO savedCompteProDTO = compteProService.save(compteProDTO);

        // Then
        assertEquals(compteProDTO.getId(), savedCompteProDTO.getId());
        assertEquals(compteProDTO.getNomAr(), savedCompteProDTO.getNomAr());
    }

    @Test
    public void testUpdate() {
        // Given
        CompteProDTO compteProDTO = new CompteProDTO();
        compteProDTO.setId("1L");
        compteProDTO.setNomAr("Test");

        ComptePro comptePro = new ComptePro();
        comptePro.setId("1L");
        comptePro.setNomAr("Test");

        // Mocking
        when(compteProMapper.toEntity(compteProDTO)).thenReturn(comptePro);
        when(compteProRepository.save(any(ComptePro.class))).thenReturn(comptePro);
        when(compteProMapper.toDto(comptePro)).thenReturn(compteProDTO);

        // When
        CompteProDTO updatedCompteProDTO = compteProService.update(compteProDTO);

        // Then
        verify(compteProMapper).toEntity(compteProDTO);
        verify(compteProRepository).save(any(ComptePro.class));
        verify(compteProMapper).toDto(comptePro);

        // Additional verifications
        assertNotNull(updatedCompteProDTO);
        assertEquals(compteProDTO.getNomAr(), updatedCompteProDTO.getNomAr());
        assertEquals(compteProDTO.getId(), updatedCompteProDTO.getId());
    }
    @Test
    void testFindOne() {
        // Given
        String id = "1";
        ComptePro comptePro = new ComptePro();
        comptePro.setId(id);
        when(compteProRepository.findById(id)).thenReturn(Optional.of(comptePro));

        CompteProDTO compteProDTO = new CompteProDTO();
        compteProDTO.setId(id);
        when(compteProMapper.toDto(comptePro)).thenReturn(compteProDTO);

        // When
        Optional<CompteProDTO> result = compteProService.findOne(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }




    // Add more test cases as needed...
}

