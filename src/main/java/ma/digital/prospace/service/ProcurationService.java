package ma.digital.prospace.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.google.firebase.messaging.FirebaseMessagingException;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.InvitationRepository;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.dto.ProcurationDTO;
import ma.digital.prospace.service.mapper.ProcurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


/**
 * Service Implementation for managing {@link Procuration}.
 */
@Service
@Transactional
public class ProcurationService {
    private final Logger log = LoggerFactory.getLogger(ProcurationService.class);

    private final ProcurationRepository procurationRepository;
    private final FirebaseNotificationService firebaseNotificationService;
    private final ContactRepository contactRepository;
    private final ProcurationMapper procurationMapper;
    private final InvitationRepository invitationRepository;
    private final InvitationService invitationService;
    private final CompteProRepository compteProRepository;

    public ProcurationService(ProcurationRepository procurationRepository, ProcurationMapper procurationMapper, InvitationRepository invitationRepository, InvitationService invitationService, CompteProRepository compteProRepository, FirebaseNotificationService firebaseNotificationService, ContactRepository contactRepository) {
        this.procurationRepository = procurationRepository;
        this.procurationMapper = procurationMapper;
        this.invitationRepository = invitationRepository;
        this.compteProRepository = compteProRepository;
        this.invitationService = invitationService;
        this.firebaseNotificationService = firebaseNotificationService;
        this.contactRepository = contactRepository;
    }

    /**
     * Save a procuration.
     *
     * @param procurationDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcurationDTO save(ProcurationDTO procurationDTO) {
        log.debug("Request to save Procuration : {}", procurationDTO);
        Procuration procuration = procurationMapper.toEntity(procurationDTO);
        procuration = procurationRepository.save(procuration);
        return procurationMapper.toDto(procuration);
    }

    /**
     * Update a procuration.
     *
     * @param procurationDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcurationDTO update(ProcurationDTO procurationDTO) {
        log.debug("Request to update Procuration : {}", procurationDTO);
        Procuration procuration = procurationMapper.toEntity(procurationDTO);
        procuration = procurationRepository.save(procuration);
        return procurationMapper.toDto(procuration);
    }

    /**
     * Partially update a procuration.
     *
     * @param procuration the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcurationDTO> partialUpdate(ProcurationDTO procuration) {
        log.debug("Request to partially update Procuration : {}", procuration);

        return procurationRepository
                .findById(procuration.getId())
                .map(existingProcuration -> {
                    procurationMapper.partialUpdate(existingProcuration, procuration);

                    return existingProcuration;
                })
                .map(procurationRepository::save)
                .map(procurationMapper::toDto);
    }

    /**
     * Get all the procurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Procurations");
        return procurationRepository.findAll(pageable).map(procurationMapper::toDto);
    }

    /**
     * Get one procuration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcurationDTO> findOne(UUID id) {
        log.debug("Request to get Procuration : {}", id);
        return procurationRepository.findById(id).map(procurationMapper::toDto);
    }

    /**
     * Delete the procuration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Procuration : {}", id);
        procurationRepository.deleteById(id);
    }

    public ProcurationDTO createOrUpdateProcuration(ProcurationDTO procurationDTO) {
        ComptePro gestionnaire = compteProRepository.findById(procurationDTO.getGestionnaireEspaceProId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gestionnaire Espace Pro not found"));
        ComptePro utilisateur = compteProRepository.findById(procurationDTO.getUtilisateurProId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utilisateur Pro not found"));

        Procuration procuration = procurationMapper.toEntity(procurationDTO);
        procuration = procurationRepository.save(procuration);

        // Set status
        procuration.setStatut(procuration.getId() == null ? StatutInvitation.PENDING : StatutInvitation.ACCEPTED);

        // notification
        if (StatutInvitation.ACCEPTED.equals(procuration.getStatut())) {
            Contact contact = contactRepository.findByCompteProId(gestionnaire.getId());
            if (contact != null && contact.getDeviceToken() != null && !contact.getDeviceToken().isEmpty()) {
                try {
                    firebaseNotificationService.sendNotification(
                            contact.getDeviceToken(),
                            "Invitation Accepted",
                            "Invitation to manage the pro space has been accepted."
                    );
                } catch (Exception e) {
                    System.err.println("Failed to send notification: " + e.getMessage());
                }
            } else {
                System.out.println("No device token available for contact with ComptePro ID: " + gestionnaire.getId());
            }
        }

        return procurationMapper.toDto(procuration);
    }


    public void deleteProcuration(UUID procurationId) {
        Procuration procuration = procurationRepository.findById(procurationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procuration not found"));
        procuration.setDateFin(Instant.now());
        procurationRepository.save(procuration);
    }
}