package prospace.service;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.repository.RoleeRepository;
import ma.digital.prospace.service.RoleeService;
import ma.digital.prospace.service.dto.RoleeDTO;
import ma.digital.prospace.service.mapper.RoleeMapper;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class RoleServiceTest {
    public class RoleeServiceTest {

        @Mock
        private RoleeRepository roleeRepository;

        @Mock
        private RoleeMapper roleeMapper;

        @InjectMocks
        private RoleeService roleeService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testSaveRolee() {
            RoleeDTO roleeDTO = new RoleeDTO();
            Rolee rolee = new Rolee();

            when(roleeMapper.toEntity(roleeDTO)).thenReturn(rolee);
            when(roleeRepository.save(rolee)).thenReturn(rolee);
            when(roleeMapper.toDto(rolee)).thenReturn(roleeDTO);

            RoleeDTO savedDTO = roleeService.save(roleeDTO);

            assertEquals(roleeDTO, savedDTO);
            verify(roleeRepository, times(1)).save(rolee);
        }

        @Test
        void testUpdateRolee() {
            RoleeDTO roleeDTO = new RoleeDTO();
            roleeDTO.setId(UUID.randomUUID());
            Rolee rolee = new Rolee();

            when(roleeMapper.toEntity(roleeDTO)).thenReturn(rolee);
            when(roleeRepository.save(rolee)).thenReturn(rolee);
            when(roleeMapper.toDto(rolee)).thenReturn(roleeDTO);

            RoleeDTO updatedDTO = roleeService.update(roleeDTO);

            assertEquals(roleeDTO, updatedDTO);
            verify(roleeRepository, times(1)).save(rolee);
        }

        @Test
        void testFindAllRolees() {
            Rolee rolee = new Rolee();
            rolee.setId(UUID.randomUUID());
            List<Rolee> roleeList = Collections.singletonList(rolee);

            when(roleeRepository.findAll()).thenReturn(roleeList);
            when(roleeMapper.toDto(rolee)).thenReturn(new RoleeDTO());

            List<RoleeDTO> result = roleeService.findAll();

            assertEquals(1, result.size());
            assertEquals(rolee.getId(), result.get(0).getId());
        }

        @Test
        void testFindAllRolees_EmptyList() {
            when(roleeRepository.findAll()).thenReturn(Collections.emptyList());

            List<RoleeDTO> result = roleeService.findAll();

            assertEquals(0, result.size());
        }

        // Test other methods similarly...
    }
}
