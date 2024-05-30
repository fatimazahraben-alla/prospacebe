package ma.digital.prospace.service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.enumeration.StatutCompte;
import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.mapper.ContactMapper;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.service.mapper.CompteProMapper;
import ma.digital.prospace.service.dto.*;
import org.springframework.web.server.ResponseStatusException;
/**
 * Service Implementation for managing {@link ComptePro}.
 */
@Service
@Transactional
public class CompteProService {

    private final Logger log = LoggerFactory.getLogger(CompteProService.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("");
    private final AssociationRepository associationRepository;
    private final CompteProRepository compteProRepository;
    private final ProcurationRepository procurationRepository;
    private final CompteProMapper compteProMapper;
    private final ContactMapper contactMapper;
    private final AuditLogService auditLogService;
    private final ContactRepository contactRepository;
    private final FirebaseNotificationService firebaseNotificationService;
    @Autowired
    private final FirebaseMessaging firebaseMessaging;

    public CompteProService(CompteProRepository compteProRepository, CompteProMapper compteProMapper,ContactRepository contactRepository, FirebaseNotificationService firebaseNotificationService, FirebaseMessaging firebaseMessaging, ProcurationRepository procurationRepository, AuditLogService auditLogService, AssociationRepository associationRepository, ContactMapper contactMapper) {
        this.compteProRepository = compteProRepository;
        this.compteProMapper = compteProMapper;
        this.contactMapper = contactMapper;
        this.contactRepository = contactRepository;
        this.firebaseNotificationService = firebaseNotificationService;
        this.firebaseMessaging = firebaseMessaging;
        this.procurationRepository = procurationRepository;
        this.auditLogService = auditLogService;
        this.associationRepository = associationRepository;
    }

    /**
     * Save a comptePro.
     *
     * @param compteProDTO the entity to save.
     * @return the persisted entity.
     */
    public CompteProDTO save(CompteProDTO compteProDTO) {
        log.debug("Request to save ComptePro : {}", compteProDTO);
        ComptePro comptePro = compteProMapper.toEntity(compteProDTO);
        comptePro = compteProRepository.save(comptePro);
        return compteProMapper.toDto(comptePro);
    }

    /**
     * Update a comptePro.
     *
     * @param compteProDTO the entity to save.
     * @return the persisted entity.
     */
    public CompteProDTO update(CompteProDTO compteProDTO) {
        log.debug("Request to update ComptePro : {}", compteProDTO);
        ComptePro comptePro = compteProMapper.toEntity(compteProDTO);
        comptePro = compteProRepository.save(comptePro);

        return compteProMapper.toDto(comptePro);
    }

    /**
     * Partially update a comptePro.
     *
     * @param comptePro the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompteProDTO> partialUpdate(CompteProDTO comptePro) {
        log.debug("Request to partially update ComptePro : {}", comptePro);

        return compteProRepository
            .findById(comptePro.getId())
            .map(existingComptePro -> {
                compteProMapper.partialUpdate(existingComptePro, comptePro);

                return existingComptePro;
            })
            .map(compteProRepository::save)
            .map(compteProMapper::toDto);
    }





    /**
     * Get all the comptePros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompteProDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComptePros");
        Page<CompteProDTO> result = compteProRepository.findAll(pageable).map(compteProMapper::toDto);

        // Audit log entry
        auditLogger.info("Performed a retrieval of all ComptePros - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());

        return result;
    }

    /**
     * Get one comptePro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompteProDTO> findOne(String id) {
        log.debug("Request to get ComptePro : {}", id);
        return compteProRepository.findById(id).map(compteProMapper::toDto);
    }

    /**
     * Delete the comptePro by id.
     *
     * @param id the id of the entity.
     */
    /*public void delete(String id) {
        log.debug("Request to delete ComptePro : {}", id);
        Contact contact = contactRepository.findByCompteProId(id);
        if (contact != null) {
            contactRepository.delete(contact);
        }
        compteProRepository.deleteById(id);
    }*/
    public String delete(String id) {
        if (!compteProRepository.existsById(id)) {
            return "not_found";
        }

        ComptePro comptePro = compteProRepository.findById(id).orElse(null);
        if (comptePro == null) {
            return "already_deleted";
        }

        // Remove all related entities
        // Remove all procurations related to this ComptePro
        procurationRepository.deleteAll(procurationRepository.findByGestionnaireEspaceProId(id));
        procurationRepository.deleteAll(procurationRepository.findByUtilisateurProId(id));
        associationRepository.deleteAll(associationRepository.findAllByCompteID(id));
        // Then delete the associated contact
        Contact contact = contactRepository.findByCompteProId(id);
        if (contact != null) {
            contactRepository.delete(contact);
        }

        // Finally delete the ComptePro
        compteProRepository.deleteById(id);

        return "deleted";
    }

    /**
     * create an account
     */
    public CompteProDTO createAccountWithMobileRegistration(MobileRegistrationDTO registrationDTO) {
        if (StringUtils.isEmpty(registrationDTO.getCompteId())) {
            throw new IllegalArgumentException("Le subId ne doit pas être nul ou vide");
        }

        if (compteProRepository.findById(registrationDTO.getCompteId()).isPresent()) {
            throw new EntityExistsException("Un compte avec cet ID existe déjà");
        }

        ComptePro comptePro = new ComptePro();
        comptePro.setId(registrationDTO.getCompteId());
        comptePro.setStatut(StatutCompte.VALIDE);
        comptePro.setCreatedAt(new Date());
        comptePro.setDeleted(false);
        ComptePro savedComptePro = compteProRepository.save(comptePro); // Save and store the returned ComptePro

        Contact contact = new Contact();
        contact.setComptePro(savedComptePro);
        contact.setDeviceToken(registrationDTO.getDeviceToken());
        contact.setDeviceOS(registrationDTO.getDeviceOS());
        contact.setDeviceVersion(registrationDTO.getDeviceVersion());
        contactRepository.save(contact);

        // Audit Log
        Map<String, Object> data = new HashMap<>();
        data.put("actionDetail", "Create new ComptePro");
        CompteProDTO createdCompteProDTO = compteProMapper.toDto(savedComptePro);
        auditLogService.logAudit("CREATE_COMPTE_PRO", "admin", savedComptePro.getId(), "@IP", "test", data, createdCompteProDTO);

        return createdCompteProDTO;
    }
    public List<CompteProDTO> listerMesEspaces(String userId) {
        List<ComptePro> relatedSpaces = compteProRepository.findAllRelatedByUser(userId);
        return relatedSpaces.stream()
                .map(compteProMapper::toDto)
                .collect(Collectors.toList());
    }
    public Optional<CompteProContactDTO> findCompteProWithContact(String compteProId) {
        Optional<ComptePro> compteProOptional = compteProRepository.findById(compteProId);
        if (compteProOptional.isPresent()) {
            CompteProDTO compteProDTO = compteProMapper.toDto(compteProOptional.get());
            Contact contact = contactRepository.findByCompteProId(compteProId);
            ContactDTO contactDTO = contact != null ? contactMapper.toDto(contact) : null;
            return Optional.of(new CompteProContactDTO(compteProDTO, contactDTO));
        }
        return Optional.empty();
    }
}