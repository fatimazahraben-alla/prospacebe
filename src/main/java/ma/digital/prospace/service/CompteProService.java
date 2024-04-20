package ma.digital.prospace.service;

import java.util.Date;
import java.util.Optional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.enumeration.StatutCompte;
import ma.digital.prospace.repository.ContactRepository;
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
    private static final Logger auditLogger = LoggerFactory.getLogger("ma.digital.prospace.audit2");

    private final CompteProRepository compteProRepository;

    private final CompteProMapper compteProMapper;

    private final ContactRepository contactRepository;
    private final FirebaseNotificationService firebaseNotificationService;
    @Autowired
    private final FirebaseMessaging firebaseMessaging;

    public CompteProService(CompteProRepository compteProRepository, CompteProMapper compteProMapper,ContactRepository contactRepository, FirebaseNotificationService firebaseNotificationService, FirebaseMessaging firebaseMessaging) {
        this.compteProRepository = compteProRepository;
        this.compteProMapper = compteProMapper;
        this.contactRepository = contactRepository;
        this.firebaseNotificationService = firebaseNotificationService;
        this.firebaseMessaging = firebaseMessaging;
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
    public void delete(String id) {
        log.debug("Request to delete ComptePro : {}", id);
        compteProRepository.deleteById(id);
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
        comptePro = compteProRepository.save(comptePro);

        Contact contact = new Contact();
        contact.setComptePro(comptePro);

        contact.setDeviceToken(registrationDTO.getDeviceToken());
        contact.setDeviceOS(registrationDTO.getDeviceOS());
        contact.setDeviceVersion(registrationDTO.getDeviceVersion());
        contactRepository.save(contact);

        return compteProMapper.toDto(comptePro);
    }
}

