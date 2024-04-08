package ma.digital.prospace.service;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.service.dto.CompteProDTO;
import ma.digital.prospace.service.mapper.CompteProMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CompteProServiceTest {

    @Mock
    private CompteProRepository compteProRepository;

    @Mock
    private CompteProMapper compteProMapper;

    @InjectMocks
    private CompteProService compteProService;

    private CompteProDTO compteProDTO;
    private ComptePro comptePro;

    @BeforeEach
    public void setUp() {
        // Initialize your DTO and entity here
        compteProDTO = new CompteProDTO();
        comptePro = new ComptePro();
        // Configure the mapper's behavior
        when(compteProMapper.toEntity(any(CompteProDTO.class))).thenReturn(comptePro);
        when(compteProMapper.toDto(any(ComptePro.class))).thenReturn(compteProDTO);
    }

    @Test
    public void testSaveComptePro() {
        // Configure the repository's behavior
        when(compteProRepository.save(any(ComptePro.class))).thenReturn(comptePro);

        CompteProDTO savedDTO = compteProService.save(compteProDTO);

        assertNotNull(savedDTO);
        verify(compteProRepository).save(any(ComptePro.class));
        verify(compteProMapper).toDto(any(ComptePro.class));
    }
    @Test
    public void testUpdateComptePro() {
        when(compteProRepository.save(any(ComptePro.class))).thenReturn(comptePro);

        CompteProDTO updatedDTO = compteProService.update(compteProDTO);

        assertNotNull(updatedDTO);
        verify(compteProRepository).save(any(ComptePro.class));
        verify(compteProMapper).toDto(any(ComptePro.class));
    }

    @Test
    public void testPartialUpdateComptePro() {
        Long id = 1L;
        comptePro.setId(id);
        compteProDTO.setId(id);

        when(compteProRepository.findById(id)).thenReturn(Optional.of(comptePro));
        when(compteProRepository.save(any(ComptePro.class))).thenReturn(comptePro);

        Optional<CompteProDTO> result = compteProService.partialUpdate(compteProDTO);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(compteProRepository).findById(id);
        verify(compteProRepository).save(any(ComptePro.class));
    }

    @Test
    public void testFindAllComptePro() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ComptePro> page = new PageImpl<>(Collections.singletonList(comptePro));
        when(compteProRepository.findAll(pageable)).thenReturn(page);

        Page<CompteProDTO> result = compteProService.findAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(compteProRepository).findAll(pageable);
        verify(compteProMapper, times(1)).toDto(any(ComptePro.class));
    }

    @Test
    public void testFindOneComptePro() {
        Long id = 1L;
        when(compteProRepository.findById(id)).thenReturn(Optional.of(comptePro));

        Optional<CompteProDTO> result = compteProService.findOne(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(compteProRepository).findById(id);
    }

    @Test
    public void testDeleteComptePro() {
        Long id = 1L;
        doNothing().when(compteProRepository).deleteById(id);

        compteProService.delete(id);

        verify(compteProRepository).deleteById(id);
    }

}
