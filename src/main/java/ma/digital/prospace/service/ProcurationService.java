package ma.digital.prospace.service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.google.firebase.messaging.FirebaseMessagingException;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.dto.NotificationDTO;
import ma.digital.prospace.service.dto.ProcurationDTO;
import ma.digital.prospace.service.mapper.ProcurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final NotificationService notificationService;
    private final ProcurationMapper procurationMapper;
    private final CompteProRepository compteProRepository;

    public ProcurationService(ProcurationRepository procurationRepository, ProcurationMapper procurationMapper, CompteProRepository compteProRepository, FirebaseNotificationService firebaseNotificationService, ContactRepository contactRepository, NotificationService notificationService) {
        this.procurationRepository = procurationRepository;
        this.procurationMapper = procurationMapper;
        this.compteProRepository = compteProRepository;
        this.firebaseNotificationService = firebaseNotificationService;
        this.contactRepository = contactRepository;
        this.notificationService = notificationService;
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

    public ProcurationDTO createProcuration(ProcurationDTO procurationDTO) throws FirebaseMessagingException {
        log.debug("Request to create Procuration : {}", procurationDTO);
        ComptePro gestionnaire = compteProRepository.findById(procurationDTO.getGestionnaireEspaceProId())
                .orElseThrow(() -> new RuntimeException("Gestionnaire Espace Pro introuvable"));

        ComptePro utilisateur = compteProRepository.findById(procurationDTO.getUtilisateurProId())
                .orElseThrow(() -> new RuntimeException("Utilisateur Pro introuvable"));

        Procuration procuration = new Procuration();
        procuration.setGestionnaireEspacePro(gestionnaire);
        procuration.setUtilisateurPro(utilisateur);
        procuration.setStatut(StatutInvitation.PENDING);

        Procuration savedProcuration = procurationRepository.save(procuration);

        // Prepare the notification details
        String title = "Nouvelle demande de procuration";
        String message = "Vous avez reçu une nouvelle demande de procuration de " + procurationDTO.getNomUtilisateurPro() + " " + procurationDTO.getPrenomUtilisateurPro();

        // Additional data for notification
        Map<String, String> data = new HashMap<>();
        data.put("procurationId", savedProcuration.getId().toString());
        data.put("emeteurId", utilisateur.getId());
        data.put("nom", procurationDTO.getNomUtilisateurPro());
        data.put("prenom", procurationDTO.getPrenomUtilisateurPro());

        // Send and persist the notification
        sendAndPersistNotification(gestionnaire.getId(), title, message, data);

        return procurationMapper.toDto(savedProcuration);
    }

    public ProcurationDTO changeProcurationStatus(UUID id, StatutInvitation statut) throws FirebaseMessagingException {
        log.debug("Request to change status of Procuration : {}", id);
        Procuration procuration = procurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procuration non trouvée"));
        procuration.setStatut(statut);
        Procuration savedProcuration = procurationRepository.save(procuration);

        String title = (statut == StatutInvitation.ACCEPTED) ? "Procuration acceptée" : "Procuration refusée";
        String message = String.format("Votre demande de procuration a été %s.",
                (statut == StatutInvitation.ACCEPTED) ? "acceptée" : "refusée");

        // Additional data for notification
        Map<String, String> data = new HashMap<>();
        data.put("procurationId", savedProcuration.getId().toString());
        data.put("emeteurId", savedProcuration.getGestionnaireEspacePro().getId());

        sendAndPersistNotification(savedProcuration.getUtilisateurPro().getId(), title, message, data);

        return procurationMapper.toDto(savedProcuration);
    }

    public void deleteProcuration(UUID procurationId) throws FirebaseMessagingException {
        Procuration procuration = procurationRepository.findById(procurationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procuration not found"));
        String messageToGestionnaire;
        String messageToUtilisateur;

        if (procuration.getStatut() == StatutInvitation.ACCEPTED) {
            messageToGestionnaire = String.format("Vous n'avez plus de procuration sur l'espace pro de %s.",
                    procuration.getUtilisateurPro().getNomFr() + " " + procuration.getUtilisateurPro().getPrenomFr());
            messageToUtilisateur = String.format("%s n'est plus gestionnaire de votre espace pro.",
                    procuration.getGestionnaireEspacePro().getNomFr() + " " + procuration.getGestionnaireEspacePro().getPrenomFr());
        } else {
            messageToGestionnaire = String.format("Procuration en cours sur l'espace pro de %s a été annulée.",
                    procuration.getUtilisateurPro().getNomFr() + " " + procuration.getUtilisateurPro().getPrenomFr());
            messageToUtilisateur = String.format("Votre demande de procuration pour %s a été annulée.",
                    procuration.getGestionnaireEspacePro().getNomFr() + " " + procuration.getGestionnaireEspacePro().getPrenomFr());
        }

        // Additional data for notification
        Map<String, String> data = new HashMap<>();
        data.put("procurationId", procurationId.toString());
        data.put("emeteurId", procuration.getGestionnaireEspacePro().getId());

        sendAndPersistNotification(procuration.getGestionnaireEspacePro().getId(), "Fin de procuration", messageToGestionnaire, data);
        sendAndPersistNotification(procuration.getUtilisateurPro().getId(), "Procuration annulée", messageToUtilisateur, data);

        procurationRepository.deleteById(procurationId);
    }

    public void removeDelegation(String utilisateurProId, String gestionnaireEspaceProId) throws FirebaseMessagingException {
        log.debug("Request to remove delegation: UtilisateurPro ID: {}, GestionnaireEspacePro ID: {}", utilisateurProId, gestionnaireEspaceProId);

        if (!procurationRepository.existsByGestionnaireEspaceProIdAndUtilisateurProId(gestionnaireEspaceProId, utilisateurProId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No delegation found for these ids");
        }

        Procuration procuration = procurationRepository
                .findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(utilisateurProId, gestionnaireEspaceProId);
        if (procuration == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Procuration not found");
        }

        ComptePro gestionnaire = procuration.getGestionnaireEspacePro();
        ComptePro utilisateurPro = procuration.getUtilisateurPro();
        String messageToGestionnaire = String.format("Vous n'êtes plus le gestionnaire de l'espace pro de %s.", utilisateurPro.getNomFr() + " " + utilisateurPro.getPrenomFr());
        String messageToUtilisateurPro = String.format("%s n'est plus un délégué de votre espace pro.", gestionnaire.getNomFr() + " " + gestionnaire.getPrenomFr());

        // Additional data for notification
        Map<String, String> data = new HashMap<>();
        data.put("procurationId", procuration.getId().toString());
        data.put("emeteurId", gestionnaire.getId());

        sendAndPersistNotification(gestionnaire.getId(), "Notification de retrait de délégation", messageToGestionnaire, data);
        sendAndPersistNotification(utilisateurPro.getId(), "Notification de retrait de délégation", messageToUtilisateurPro, data);

        procurationRepository.deleteById(procuration.getId());
    }

    private void sendAndPersistNotification(String compteProId, String title, String message, Map<String, String> data) throws FirebaseMessagingException {
        Contact contact = contactRepository.findByCompteProId(compteProId);
        if (contact != null && contact.getDeviceToken() != null && !contact.getDeviceToken().isEmpty()) {
            firebaseNotificationService.sendNotificationWithData(contact.getDeviceToken(), title, message, data);
            // Créer et enregistrer la notification dans la base de données
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setTitle(title);
            notificationDTO.setMessage(message);
            notificationDTO.setCompteProId(compteProId);
            notificationDTO.setCreatedAt(Instant.now());
            notificationService.createNotification(notificationDTO);
        } else {
            throw new RuntimeException("Contact introuvable pour l'ID de ComptePro : " + compteProId);
        }
    }

    public List<ProcurationDTO> findAllProcurationsByUtilisateurPro(String utilisateurProId) {
        log.debug("Request to get all Procurations for UtilisateurPro ID: {}", utilisateurProId);

        List<Procuration> procurations = procurationRepository.findByUtilisateurProId(utilisateurProId);

        return procurations.stream()
                .map(procurationMapper::toDto)
                .collect(Collectors.toList());
    }
}
