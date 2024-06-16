package ma.digital.prospace.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.service.mapper.AssociationMapper;
import ma.digital.prospace.web.rest.errors.ErrorResponse;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import ma.digital.prospace.domain.*;
import ma.digital.prospace.repository.*;
import ma.digital.prospace.service.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 * Service Implementation for managing {@link Association}.
 */
@Service
@Transactional
public class AssociationService {
    @Autowired
    private ContactRepository contactRepository;
    private final Logger log = LoggerFactory.getLogger(AssociationService.class);
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private AuditAssociationRepository auditAssociationRepository;
    @Autowired
    private AssociationMapper associationMapper;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ProcurationRepository procurationRepository;
    @Autowired
    private CompteProRepository compteProRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Autowired
    private RoleeRepository roleeRepository;
    @Autowired
    private final FirebaseMessaging firebaseMessaging;
    private Logger logger = LoggerFactory.getLogger(AssociationService.class);
    private final FirebaseNotificationService firebaseNotificationService;
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    // Constructor
    public AssociationService(AssociationRepository associationRepository,
                              AuditAssociationRepository auditAssociationRepository,
                              AssociationMapper associationMapper,
                              SessionRepository sessionRepository,
                              CompteProRepository compteProRepository,
                              EntrepriseRepository entrepriseRepository,
                              RoleeRepository roleeRepository,
                              ObjectMapper objectMapper,
                              FirebaseMessaging firebaseMessaging,
                              FirebaseNotificationService firebaseNotificationService,
                              NotificationService notificationService,
                              ProcurationRepository procurationRepository) { // Add ObjectMapper to the constructor
        this.associationRepository = associationRepository;
        this.auditAssociationRepository = auditAssociationRepository;
        this.associationMapper = associationMapper;
        this.sessionRepository = sessionRepository;
        this.compteProRepository = compteProRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.roleeRepository = roleeRepository;
        this.objectMapper = objectMapper; // Initialize ObjectMapper*
        this.firebaseMessaging = firebaseMessaging;
        this.firebaseNotificationService = firebaseNotificationService;
        this.notificationService = notificationService;
        this.procurationRepository = procurationRepository;
    }
    public AssociationDTO save(AssociationDTO associationDTO) {
        log.debug("Request to save Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }
    public AssociationDTO update(AssociationDTO associationDTO) {
        log.debug("Request to update Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }

    public Optional<AssociationDTO> partialUpdate(AssociationDTO association) {
        log.debug("Request to partially update Association : {}", association);

        return associationRepository
                .findById(association.getId())
                .map(existingAssociation -> {
                    associationMapper.partialUpdate(existingAssociation, association);

                    return existingAssociation;
                })
                .map(associationRepository::save)
                .map(associationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<AssociationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Associations");
        return associationRepository.findAll(pageable).map(associationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AssociationDTO> findOne(UUID id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id).map(associationMapper::toDto);
    }

    public void delete(UUID id) {
        log.debug("Request to delete Association : {}", id);
        associationRepository.deleteById(id);
    }

    /**
     *  check that there is an association between the FS and the account and create a session for the current transaction (see header) and create a new session object (transactionID, status = IN_PROGRESS).
     */
    private String constructAndSendPushNotification(String deviceToken, String transactionID, String Title, String Body, Map data) {

        Notification notification = Notification.builder()
                .setTitle(Title)
                .setBody(Body)
                .build();


        Message message = Message.builder()
                .setNotification(notification)
                .putAllData(data)
                .setToken(deviceToken)
                .build();
        try {
            firebaseMessaging.send(message);
            return "Success Sending notification";
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "error sending notification ";
        }
    }
    private EntrepriseDTO2 convertToDTO(Entreprise entreprise) {
        EntrepriseDTO2 dto = new EntrepriseDTO2();
        dto.setId(entreprise.getId());
        dto.setEtat(entreprise.getEtat());

        return dto;
    }
    public CompteFSAssociationDTO processAuthenticationStep2(String compteID, String fs, String transactionID) throws JsonProcessingException {
        Optional<Session> optionalSession = sessionRepository.findByTransactionId(transactionID);
        if (optionalSession.isPresent()) {
            CompteFSAssociationDTO responseDTO = new CompteFSAssociationDTO();
            responseDTO.setCompteID(compteID);
            responseDTO.setFs(fs);
            Session existingSession = optionalSession.get();
            if (existingSession.getStatus() == Session.Status.COMPLETED) {
                // Convertir la chaîne JSON en objet Map<String, Object>
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> dataMap = objectMapper.readValue(existingSession.getJsonData(), new TypeReference<Map<String, Object>>() {});
                // Extraire les données de la map et les convertir en objets DTO
                EntrepriseDTO2 entrepriseDTO = objectMapper.convertValue(dataMap.get("entreprise"), EntrepriseDTO2.class);
                CompteProDTO compteProDTO = objectMapper.convertValue(dataMap.get("comptePro"), CompteProDTO.class);
                List<String> roles = objectMapper.convertValue(dataMap.get("roles"), new TypeReference<List<String>>() {});
                // Créer un objet CompteEntrepriseDTO à partir des objets DTO obtenus
                CompteEntrepriseDTO compteEntrepriseDTO = new CompteEntrepriseDTO();
                responseDTO.setEntreprise(entrepriseDTO);
                responseDTO.setComptePro(compteProDTO);
                responseDTO.setRoles(roles);
                responseDTO.setStatut(Session.Status.COMPLETED.toString());
            }
            return responseDTO;

            //return processWithCurrentLogic(compteID, fs, transactionID); // Or you can throw an exception or return a default response
        }
        return processWithCurrentLogic(compteID, fs, transactionID);
    }


    private CompteFSAssociationDTO processWithCurrentLogic(String compteID, String fs, String transactionID) {
        List<Association> associations = associationRepository.findAllByFsAndCompteID(fs, compteID);
        if (associations != null && !associations.isEmpty()) {
            Session session = new Session();
            session.setTransactionId(transactionID);
            session.setCreatedAt(new Date());
            session.setStatus(Session.Status.IN_PROGRESS);
            sessionRepository.save(session);
            List<String> entrepriseList = new ArrayList<>();
            for (Association association : associations) {
                Entreprise entreprise = association.getEntreprise();
                EntrepriseDTO2 entrepriseDTO = convertToDTO(entreprise); // Convert Entreprise to EntrepriseDTO
                String entrepriseString = Objects.toString(entrepriseDTO, null);
                entrepriseList.add(entrepriseString);
            }
            CompteFSAssociationDTO responseDTO = new CompteFSAssociationDTO();
            responseDTO.setCompteID(compteID);
            responseDTO.setFs(fs);
            responseDTO.setEntreprises(entrepriseList);
            // String devicetoken =  "fGe7ud_0RceyA-GZyBXJV2:APA91bEacIJWRhkniNtGOc73zMV-KlC3sSojMh6pitdNOnxf-sA_qWs3ThABFOOHd9jtwGcBulXcn9bCsVgfwjHUII43_IdmCEjQWk-q1iuVShaDc5D_xaE-0MMX1A24uuDFXHpzwdH3";
            Contact contact = contactRepository.findByCompteProId(compteID);
            String devicetoken = contact.getDeviceToken();
            try {

                if (devicetoken != null) {
                    logger.info("Device token récupéré avec succès : {}", devicetoken);
                    Map<String, String> data = new HashMap<>();
                    data.put("transactionID", transactionID);
                    data.put("compteID", compteID);
                    data.put("fs", fs);
                    constructAndSendPushNotification(devicetoken, transactionID, "Notification process auth", "contenu", data);
                    return responseDTO;
                }
            } catch (Exception e) {
                logger.info("***verifier device token**");
                logger.info("Une erreur est survenue lors de la récupération du device token");

            }
            logger.info("*acunne device token n'est trouvé");
            return null;
        } else {
            return null;
        }

    }

    /**
     * push CompteEntreprise object and update session object (transactionID, jsonData).
     */
    public void pushCompteEntreprise(CompteEntrepriseDTO compteEntrepriseDTO) throws JsonProcessingException {
        CompteProDTO compteProDTO = compteEntrepriseDTO.getComptePro();
        EntrepriseDTO2 entrepriseDTO = compteEntrepriseDTO.getEntreprise();
        List<String> roleNames = compteEntrepriseDTO.getRoles();

        // Create a data map containing the information from DTOs and the roles list
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("comptePro", compteProDTO);
        dataMap.put("entreprise", entrepriseDTO);
        dataMap.put("roles", roleNames);

        // Serialize the map to JSON
        String jsonData = objectMapper.writeValueAsString(dataMap);

        // Update the session
        Session session = sessionRepository.findByTransactionId(compteEntrepriseDTO.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("Session not found with transaction ID: " + compteEntrepriseDTO.getTransactionId()));

        session.setJsonData(jsonData);
        session.setStatus(Session.Status.COMPLETED);
        sessionRepository.save(session);
    }


    /**
     * check if the session is finished and if ok (see transactionID header), return the account data plus the company data and associated roles, parameter a transactionID as data header.
     */
    public Session.Status checkAuthenticationStep2(String transactionId) throws EntityNotFoundException {
        return sessionRepository.findByTransactionId(transactionId)
                .map(session -> {
                    if (session.getStatus() == Session.Status.COMPLETED) {
                        return session.getStatus();
                    } else {
                        throw new EntityNotFoundException("Session found but not completed. Status: " + session.getStatus());
                    }
                })
                .orElseThrow(() -> new EntityNotFoundException("Session not found with transaction ID: " + transactionId));
    }
    /**
     * Create an association
     */

    public Optional<Object> createAssociationWithNotification(
            String compteID, String destinataireID, String entrepriseID, UUID roleID,
            String compteInitiateurID, String prenomInitiateur, String nomInitiateur, String nomEntreprise) throws FirebaseMessagingException {

        log.debug("Tentative de création d'une association avec les paramètres : compteID={}, destinataireID={}, entrepriseID={}, roleID={}, compteInitiateurID={}, prenomInitiateur={}, nomInitiateur={}, nomEntreprise={}",
                compteID, destinataireID, entrepriseID, roleID, compteInitiateurID, prenomInitiateur, nomInitiateur, nomEntreprise);

        // Vérifier que compteInitiateurID est autorisé à créer une association au nom de compteID
        if (!isAuthorizedInitiator(compteID, compteInitiateurID)) {
            return Optional.of(new ErrorResponse(
                    "Le compte initiateur n'a pas les droits nécessaires pour créer cette association.",
                    403,
                    "Forbidden",
                    "Procuration ou association non acceptée."
            ));
        }

        // Vérification de l'existence de l'association avec le même rôle et entreprise
        List<Association> existingAssociations = associationRepository.findByCompteIdAndEntrepriseId(destinataireID, entrepriseID);
        for (Association assoc : existingAssociations) {
            if (assoc.getRole().getId().equals(roleID)) {
                log.warn("Une association avec le même rôle et entreprise existe déjà pour le ComptePro ID: {} et l'Entreprise ID: {}",
                        destinataireID, entrepriseID);
                return Optional.of(new ErrorResponse(
                        "Une association existe déjà entre ces comptes pour le même rôle et entreprise.",
                        409,
                        "Conflict",
                        "Une association pour le même rôle et entreprise existe déjà."
                ));
            }
        }

        // Récupération des entités
        ComptePro initiateur = compteProRepository.findById(compteInitiateurID)
                .orElseThrow(() -> new EntityNotFoundException("Le compte initiateur est introuvable."));
        ComptePro destinataire = compteProRepository.findById(destinataireID)
                .orElseThrow(() -> new EntityNotFoundException("Le compte destinataire est introuvable."));
        Entreprise entreprise = entrepriseRepository.findById(entrepriseID)
                .orElseThrow(() -> new EntityNotFoundException("L'entreprise est introuvable."));
        Rolee role = roleeRepository.findById(roleID)
                .orElseThrow(() -> new EntityNotFoundException("Le rôle est introuvable."));

        // Création de l'association
        Association association = new Association();
        association.setCompte(destinataire);
        association.setEntreprise(entreprise);
        association.setRole(role);
        association.setStatut(StatutAssociation.PENDING);
        association.setCompteInitiateurID(compteInitiateurID);

        Association savedAssociation = associationRepository.save(association);

        // Audit de l'association
        AuditAssociation audit = new AuditAssociation();
        audit.setCompteId(compteID); // Stocker compteID pour vérification future
        audit.setAssociationId(savedAssociation.getId());
        audit.setAction("CREATE");
        audit.setTimestamp(Instant.now());
        auditAssociationRepository.save(audit);

        // Notification
        String message = String.format("%s %s vous invite en tant que %s dans l'entreprise %s",
                prenomInitiateur, nomInitiateur, role.getNom(), nomEntreprise);

        Map<String, String> data = new HashMap<>();
        data.put("associationId", savedAssociation.getId().toString());
        data.put("emeteurId", compteInitiateurID);
        data.put("nom", nomInitiateur);
        data.put("prenom", prenomInitiateur);
        data.put("nomEntreprise", nomEntreprise);
        data.put("typeNotification", "invitation");

        sendAndPersistNotification(destinataire.getId(), "Nouvelle invitation", message, data);

        log.info("Association créée avec succès entre le ComptePro ID: {} et l'Entreprise ID: {} pour le rôle ID: {}",
                destinataireID, entrepriseID, roleID);

        return Optional.of(associationMapper.toDto(savedAssociation));
    }

    private boolean isAuthorizedInitiator(String compteID, String compteInitiateurID) {
        // Vérifier si le compte initiateur est le compte principal
        if (compteID.equals(compteInitiateurID)) {
            return true;
        }

        // Vérifier si le compte initiateur a une procuration acceptée
        boolean hasAcceptedProcuration = procurationRepository.existsByGestionnaireEspaceProIdAndUtilisateurProIdAndStatut(
                compteInitiateurID, // Le gestionnaire de l'espace (initiateur)
                compteID,          // Le compte principal
                StatutInvitation.ACCEPTED
        );

        // Vérifier si le compte initiateur est un gestionnaire d'entreprise accepté
        boolean hasAcceptedAssociation = auditAssociationRepository.existsByCompteIdAndAssociationIdAndAssociationRoleNomAndAssociationStatut(
                compteInitiateurID,   // Le gestionnaire d'entreprise (initiateur)
                compteID,             // Le compte principal
                "gestionnaire_entreprise", // Le rôle de gestionnaire d'entreprise
                StatutAssociation.ACCEPTED
        );

        return hasAcceptedProcuration || hasAcceptedAssociation;
    }


    public List<AssociationDTO> findAssociationsByCompteId(String compteId) {
        List<AuditAssociation> audits = auditAssociationRepository.findByCompteId(compteId);
        List<UUID> associationIds = audits.stream()
                .map(AuditAssociation::getAssociationId)
                .distinct()
                .collect(Collectors.toList());
        List<Association> associations = associationRepository.findAllById(associationIds);
        return associations.stream()
                .map(associationMapper::toDto)
                .collect(Collectors.toList());
    }
    public AssociationDTO updateAssociationStatut(UUID associationId, StatutAssociation nouveauStatut, String nom, String prenom) throws FirebaseMessagingException {
        log.debug("Request to update status of Association : {}, {}, {}", associationId, nouveauStatut, nom);

        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Association not found with id " + associationId));

        if (nouveauStatut != StatutAssociation.ACCEPTED && nouveauStatut != StatutAssociation.CANCELED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Statut must be ACCEPTED or CANCELED");
        }

        association.setStatut(nouveauStatut);
        association = associationRepository.save(association);

        AuditAssociation audit = auditAssociationRepository.findFirstByAssociationIdOrderByTimestampAsc(associationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Audit record not found for association id " + associationId));

        String initiatorId = audit.getCompteId();

        // Prepare and send the notification
        String title = (nouveauStatut == StatutAssociation.ACCEPTED) ? "Association Acceptée" : "Association Refusée";
        String message = String.format("Votre demande d'association avec %s %s a été %s.",
                nom, prenom, (nouveauStatut == StatutAssociation.ACCEPTED) ? "acceptée" : "refusée");

        Map<String, String> data = new HashMap<>();
        data.put("associationId", association.getId().toString());
        data.put("emeteurId", association.getCompte().getId());
        data.put("nom", nom);
        data.put("prenom", prenom);
        data.put("typeNotification", nouveauStatut == StatutAssociation.ACCEPTED ? "acceptation" : "refus");

        sendAndPersistNotification(initiatorId, title, message, data);

        // Audit the association status update
        AuditAssociation newAudit = new AuditAssociation();
        newAudit.setCompteId(initiatorId);
        newAudit.setAssociationId(associationId);
        newAudit.setAction("STATUS_UPDATE");
        newAudit.setTimestamp(Instant.now());
        auditAssociationRepository.save(newAudit);

        return associationMapper.toDto(association);
    }
    public void deleteAssociation(UUID associationId, NomPrenomEntrepriseDTO nomPrenomEntrepriseDTO) throws FirebaseMessagingException {
        log.debug("Request to delete Association : {}, {}", associationId, nomPrenomEntrepriseDTO);

        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Association not found"));

        AuditAssociation audit = auditAssociationRepository.findFirstByAssociationIdOrderByTimestampAsc(associationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Audit record not found for association id " + associationId));

        String initiatorId = audit.getCompteId();

        String messageToInitiator = String.format("L'association avec %s %s dans l'entreprise %s a été annulée.",
                nomPrenomEntrepriseDTO.getNom(), nomPrenomEntrepriseDTO.getPrenom(), nomPrenomEntrepriseDTO.getNomEntreprise());

        Map<String, String> data = new HashMap<>();
        data.put("associationId", associationId.toString());
        data.put("emeteurId", association.getCompte().getId());
        data.put("nom", nomPrenomEntrepriseDTO.getNom());
        data.put("prenom", nomPrenomEntrepriseDTO.getPrenom());
        data.put("typeNotification", "annulation");

        sendAndPersistNotification(initiatorId, "Association annulée", messageToInitiator, data);

        AuditAssociation newAudit = new AuditAssociation();
        newAudit.setCompteId(initiatorId);
        newAudit.setAssociationId(associationId);
        newAudit.setAction("DELETE");
        newAudit.setTimestamp(Instant.now());
        auditAssociationRepository.save(newAudit);

        associationRepository.deleteById(associationId);
    }
    private void sendAndPersistNotification(String compteProId, String title, String message, Map<String, String> data) throws FirebaseMessagingException {
        Contact contact = contactRepository.findByCompteProId(compteProId);
        if (contact != null && contact.getDeviceToken() != null && !contact.getDeviceToken().isEmpty()) {
            firebaseNotificationService.sendNotificationWithData(contact.getDeviceToken(), title, message, data);

            // Persist notification
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setTitle(title);
            notificationDTO.setMessage(message);
            notificationDTO.setCompteProId(compteProId);
            notificationDTO.setCreatedAt(Instant.now());
            notificationService.createNotification(notificationDTO);
        } else {
            throw new RuntimeException("Aucun token de device trouvé pour l'ID de ComptePro : " + compteProId);
        }
    }
    public List<String> getRolesByCompteProAndEntreprise(String compteProId,String entrepriseId) {
        return associationRepository.findRoleNamesByCompteProIdAndEntrepriseId(compteProId, entrepriseId);
    }

    public List<AssociationDTO> getEntrepriseRole(String fs, String compteProId) {
        List<Association> associations = associationRepository.findAllByFsAndCompteID(fs, compteProId);
        return associations.stream()
                .map(associationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AssociationDTO> findAssociationsCreatedByAccountAndDelegates(String compteID) {

        List<String> delegateIds = procurationRepository.findAcceptedDelegatesByUtilisateurProId(compteID);

        List<String> managerIds = associationRepository.findAcceptedManagersByEntrepriseCompteId(compteID, StatutAssociation.ACCEPTED);

        Set<String> allRelatedIds = new HashSet<>(delegateIds);
        allRelatedIds.addAll(managerIds);
        allRelatedIds.add(compteID);

        List<Association> associations = associationRepository.findByCompteInitiateurIDIn(allRelatedIds);

        return associations.stream()
                .map(associationMapper::toDto)
                .collect(Collectors.toList());
    }



}