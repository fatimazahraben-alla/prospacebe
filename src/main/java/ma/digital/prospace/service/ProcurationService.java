package ma.digital.prospace.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.firebase.messaging.FirebaseMessagingException;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.ProcurationRepository;
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
    private static final Logger auditLogger1 = LoggerFactory.getLogger("ma.digital.prospace.audit3");
    private final ProcurationRepository procurationRepository;
    private final FirebaseNotificationService firebaseNotificationService;
    private final ContactRepository contactRepository;
    private final ProcurationMapper procurationMapper;
    private final CompteProRepository compteProRepository;

    public ProcurationService(ProcurationRepository procurationRepository, ProcurationMapper procurationMapper, CompteProRepository compteProRepository, FirebaseNotificationService firebaseNotificationService, ContactRepository contactRepository) {
        this.procurationRepository = procurationRepository;
        this.procurationMapper = procurationMapper;
        this.compteProRepository = compteProRepository;
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

    public ProcurationDTO createProcuration(ProcurationDTO procurationDTO) {
        // Check if a procuration exists
        boolean exists = procurationRepository.existsByGestionnaireEspaceProIdAndUtilisateurProId(
                procurationDTO.getGestionnaireEspaceProId(),
                procurationDTO.getUtilisateurProId()
        );

        if (exists) {
            throw new IllegalStateException("Procuration existe déjà entre le gestionnaire et l'Espace Pro.");
        }

        ComptePro gestionnaire = compteProRepository.findById(procurationDTO.getGestionnaireEspaceProId())
                .orElseThrow(() -> new RuntimeException("Gestionnaire Espace Pro introuvable"));

        ComptePro utilisateur = compteProRepository.findById(procurationDTO.getUtilisateurProId())
                .orElseThrow(() -> new RuntimeException("Utilisateur Pro introuvable"));

        Procuration procuration = procurationMapper.toEntity(procurationDTO);
        procuration.setGestionnaireEspacePro(gestionnaire);
        procuration.setUtilisateurPro(utilisateur);
        procuration.setStatut(StatutInvitation.PENDING);
        Procuration savedProcuration = procurationRepository.save(procuration);

        sendNotification(gestionnaire.getId(), "Nouvelle demande de procuration", "Vous avez reçu une nouvelle demande de procuration de la part de " + utilisateur.getNomFr() + " " + utilisateur.getPrenomFr());

        return procurationMapper.toDto(savedProcuration);
    }

    public ProcurationDTO changeProcurationStatus(UUID id, StatutInvitation statut) {
        log.debug("Request to change status of Procuration : {}", id);
        Procuration procuration = procurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procuration non trouvée"));
        procuration.setStatut(statut);
        Procuration savedProcuration = procurationRepository.save(procuration);
        String actionDetail = (statut == StatutInvitation.ACCEPTED) ? "ACCEPTED_PROCURATION" : "REFUSED_PROCURATION";
        auditLogger1.info("{timestamp: '{}', correlationID: '{}', classe/méthode: 'ProcurationService/changeProcurationStatus', Level: 'INFO', actionType: '{}', username: '{}', userId: '{}', ipAddress: '{}', userAgent: '{}', dataReturned: '{}'}", Instant.now(), UUID.randomUUID(), actionDetail, "Utilisateur", "userId", "IP", "User-Agent", savedProcuration.getId());
        // Envoi de notification
        String title;
        String message;
        if (statut == StatutInvitation.ACCEPTED) {
            title = "Procuration acceptée";
            message = String.format("Votre demande de procuration a été acceptée par %s.", savedProcuration.getUtilisateurPro().getNomFr());
        } else {
            title = "Procuration refusée";
            message = String.format("Votre demande de procuration a été refusée par %s.", savedProcuration.getUtilisateurPro().getNomFr());
        }
        sendNotification(savedProcuration.getGestionnaireEspacePro().getId(), title, message);

        return procurationMapper.toDto(savedProcuration);
    }


    private void sendNotification(String compteProId, String title, String message) {
        Contact contact = contactRepository.findByCompteProId(compteProId);
        if (contact != null && contact.getDeviceToken() != null && !contact.getDeviceToken().isEmpty()) {
            try {
                firebaseNotificationService.sendNotification(contact.getDeviceToken(), title, message);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException("Échec de l'envoi de la notification Firebase", e);
            }
        } else {
            throw new RuntimeException("Contact introuvable pour l'ID de ComptePro : " + compteProId);
        }
    }

    public void deleteProcuration(UUID procurationId) throws FirebaseMessagingException {
        Procuration procuration = procurationRepository.findById(procurationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Procuration not found"));

        // Vérifier le statut de la procuration avant de procéder à la suppression
        if (!procuration.getStatut().equals(StatutInvitation.ACCEPTED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seules les procurations acceptées peuvent être révoquées");
        }
        // Envoi de notifications avant la suppression
        String messageToGestionnaire = String.format("Vous n'avez plus de procuration sur l'espace pro de %s.",
                (procuration.getUtilisateurPro().getNomFr() + " " + procuration.getUtilisateurPro().getPrenomFr()));
        sendNotification(procuration.getGestionnaireEspacePro().getId(), "Fin de procuration", messageToGestionnaire);

        String messageToUtilisateur = String.format("%s n'est plus gestionnaire de votre espace pro.",
                (procuration.getGestionnaireEspacePro().getNomFr() + " " + procuration.getGestionnaireEspacePro().getPrenomFr()));
        sendNotification(procuration.getUtilisateurPro().getId(), "Gestionnaire parti", messageToUtilisateur);
        // Suppression effective de la procuration
        procurationRepository.deleteById(procurationId);
    }

    @Transactional
    public void removeDelegation(String utilisateurProId, String gestionnaireEspaceProId) throws FirebaseMessagingException {
        log.debug("Request to remove delegation: UtilisateurPro ID: {}, GestionnaireEspacePro ID: {}", utilisateurProId, gestionnaireEspaceProId);

        // Vérifie si procuration existe
        if (!procurationRepository.existsByGestionnaireEspaceProIdAndUtilisateurProId(gestionnaireEspaceProId, utilisateurProId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No delegation found for these ids");
        }

        // Récupère la procuration
        Procuration procuration = procurationRepository
                .findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(utilisateurProId, gestionnaireEspaceProId);
        if (procuration == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Procuration not found");
        }

        // notification
        ComptePro gestionnaire = procuration.getGestionnaireEspacePro();
        ComptePro utilisateurPro = procuration.getUtilisateurPro();
        if (gestionnaire != null && utilisateurPro != null) {
            String messageToGestionnaire = String.format("Vous n'êtes plus le gestionnaire de l'espace pro de %s.", utilisateurPro.getNomFr());
            sendNotification(gestionnaire.getId(), "Notification de retrait de délégation", messageToGestionnaire);

            String messageToUtilisateurPro = String.format("%s n'est plus un délégué de votre espace pro.", gestionnaire.getNomFr());
            sendNotification(utilisateurPro.getId(), "Notification de retrait de délégation", messageToUtilisateurPro);
        }

        // Supprimer procuration
        procurationRepository.deleteById(procuration.getId());
        auditLogger1.info("Delegation removed for GestionnaireEspacePro ID: {} from UtilisateurPro ID: {}", gestionnaireEspaceProId, utilisateurProId);

        log.info("Delegation removed for GestionnaireEspacePro ID: {} from UtilisateurPro ID: {}", gestionnaireEspaceProId, utilisateurProId);
    }

    public List<ComptePro> findAllProcurationsByUtilisateurPro(String utilisateurProId) {
        auditLogger1.info("Requête pour récupérer toutes les procurations pour l'utilisateur professionnel ID: {}", utilisateurProId);

        List<Procuration> procurations = procurationRepository.findByUtilisateurProId(utilisateurProId);

        // Convertir les Procuration en ComptePro
        List<ComptePro> comptePros = procurations.stream()
                .map(procuration -> {
                    ComptePro gestionnaireEspacePro = procuration.getGestionnaireEspacePro();
                    if (gestionnaireEspacePro != null) {
                        return gestionnaireEspacePro;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Log de l'utilisateur professionnel
        auditLogger1.info("Utilisateur professionnel : {}", utilisateurProId);

        return comptePros;
    }
}