package ma.digital.prospace.service;

import java.util.Optional;
import java.util.UUID;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
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
    public Optional<CompteProDTO> findOne(UUID id) {
        log.debug("Request to get ComptePro : {}", id);
        return compteProRepository.findById(id).map(compteProMapper::toDto);
    }

    /**
     * Delete the comptePro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ComptePro : {}", id);
        compteProRepository.deleteById(id);
    }

    public void registerContactDTO(MobileRegistrationDTO requestDTO) {
        auditLogger.info("Initiating registration/update for compteId: {}", requestDTO.getCompteId());

        ComptePro comptePro = compteProRepository.findById(requestDTO.getCompteId())
                .orElseThrow(() -> {
                    auditLogger.error("Failed to find compte with ID: {}", requestDTO.getCompteId());
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compte not found");
                });

        Contact contact = contactRepository.findByCompteProId(requestDTO.getCompteId());

        if (contact == null) {
            auditLogger.info("Creating new contact for compteId: {}", requestDTO.getCompteId());
            contact = new Contact();
            contact.setComptePro(comptePro);
        } else {
            auditLogger.info("Updating existing contact for compteId: {}", requestDTO.getCompteId());
        }

        // Update contact fields
        contact.setDeviceToken(requestDTO.getDeviceToken());
        contact.setDeviceOS(requestDTO.getDeviceOS());
        contact.setDeviceVersion(requestDTO.getDeviceVersion());

        // Save or update the contact
        contactRepository.save(contact);
        auditLogger.info("Completed registration/update for compteId: {}", requestDTO.getCompteId());
    }

    /**
     * create an account
     */
    public CompteProDTO createAccount(String deviceToken, UUID subId) {
        auditLogger.info("Creating account with subId: {0}", subId);

        ComptePro comptePro = new ComptePro();
        comptePro.setId(subId);
        comptePro.setStatut(StatutCompte.VALIDE);

        comptePro = compteProRepository.save(comptePro);
        auditLogger.info("Account saved with ID: {0}", comptePro.getId());

        // Notification
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("Bienvenue")
                        .setBody("Votre compte a été créé avec succès.")
                        .build())
                .setToken(deviceToken)
                .build();

        try {
            String response = firebaseMessaging.send(message);
            auditLogger.info("Notification sent, response: {0}", response);
        } catch (FirebaseMessagingException e) {
            auditLogger.warn("Failed to send notification", e);
            throw new RuntimeException(e);
        }

        CompteProDTO result = compteProMapper.toDto(comptePro);
        auditLogger.info("Account DTO created with ID: {0}", result.getId());
        return result;
    }
}

