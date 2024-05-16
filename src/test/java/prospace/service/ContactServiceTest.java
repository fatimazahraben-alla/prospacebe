package prospace.service;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.service.ContactService;
import ma.digital.prospace.service.dto.ContactDTO;
import ma.digital.prospace.service.mapper.ContactMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
public class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindOne() {
        // Given
        UUID contactId = UUID.randomUUID();
        Contact contact = new Contact();
        contact.setId(contactId);

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contactId);

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));
        when(contactMapper.toDto(contact)).thenReturn(contactDTO);

        // When
        Optional<ContactDTO> result = contactService.findOne(contactId);

        // Then
        assertNotNull(result);
        assertEquals(contactDTO, result.orElse(null));
    }

    @Test
    void testSave() {
        // Given
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(UUID.randomUUID());

        Contact contact = new Contact();
        contact.setId(contactDTO.getId());

        when(contactMapper.toEntity(contactDTO)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactMapper.toDto(contact)).thenReturn(contactDTO);

        // When
        ContactDTO result = contactService.save(contactDTO);

        // Then
        assertNotNull(result);
        assertEquals(contactDTO.getId(), result.getId());
    }

    // Add other tests for other methods as needed
}