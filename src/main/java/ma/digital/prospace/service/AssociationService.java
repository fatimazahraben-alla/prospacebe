package ma.digital.prospace.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.enumeration.StatutAssociation;
import ma.digital.prospace.service.mapper.AssociationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

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
    private AssociationMapper associationMapper;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CompteProRepository compteProRepository;
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Autowired
    private RoleeRepository roleeRepository;
    @Autowired
    private final FirebaseMessaging firebaseMessaging;
    private Logger logger = LoggerFactory.getLogger(AssociationService.class);

    private final ObjectMapper objectMapper;

    // Constructor
    public AssociationService(AssociationRepository associationRepository,
                              AssociationMapper associationMapper,
                              SessionRepository sessionRepository,
                              CompteProRepository compteProRepository,
                              EntrepriseRepository entrepriseRepository,
                              RoleeRepository roleeRepository,
                              ObjectMapper objectMapper,
                              FirebaseMessaging firebaseMessaging) { // Add ObjectMapper to the constructor
        this.associationRepository = associationRepository;
        this.associationMapper = associationMapper;
        this.sessionRepository = sessionRepository;
        this.compteProRepository = compteProRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.roleeRepository = roleeRepository;
        this.objectMapper = objectMapper; // Initialize ObjectMapper*
        this.firebaseMessaging = firebaseMessaging;
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
    public Optional<AssociationDTO> findOne(Long id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id).map(associationMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Association : {}", id);
        associationRepository.deleteById(id);
    }

    /**
     *  check that there is an association between the FS and the account and create a session for the current transaction (see header) and create a new session object (transactionID, status = IN_PROGRESS).
     */
    private String constructAndSendPushNotification(String deviceToken, String transactionID, String Title, String Body) {

        Notification notification = Notification.builder()
                .setTitle(Title)
                .setBody(Body)
                .build();
        Map<String, String> data = new HashMap<>();
        data.put("transactionID", transactionID);

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
    private class NotificationSendingException extends RuntimeException {
        public NotificationSendingException(String message) {
            super(message);
        }

        public NotificationSendingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public CompteFSAssociationDTO processAuthenticationStep2(UUID compteID, Long fs, String transactionID) {
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
                String entrepriseString = Objects.toString(entreprise, null);
                entrepriseList.add(entrepriseString);
            }
            CompteFSAssociationDTO responseDTO = new CompteFSAssociationDTO();
            responseDTO.setCompteID(compteID);
            responseDTO.setFs(fs);
            responseDTO.setEntreprises(entrepriseList);
            //   String devicetoken =  "fGe7ud_0RceyA-GZyBXJV2:APA91bEacIJWRhkniNtGOc73zMV-KlC3sSojMh6pitdNOnxf-sA_qWs3ThABFOOHd9jtwGcBulXcn9bCsVgfwjHUII43_IdmCEjQWk-q1iuVShaDc5D_xaE-0MMX1A24uuDFXHpzwdH3";
            try {
                Contact contact = contactRepository.findByCompteProId(compteID);
                String deviceToken = contact.getDeviceToken();
                if (deviceToken != null) {
                    logger.info("Device token récupéré avec succès : {}", deviceToken);
                    constructAndSendPushNotification(deviceToken, transactionID, "Notification process auth", "contenu");
                    return responseDTO;
                }
            } catch (Exception e) {
                logger.info("******verifier device token*******");
                logger.info("Une erreur est survenue lors de la récupération du device token");

            }
            logger.info("**acunne device token n'est trouvé*");
            return null;
        } else {
            return null; // Retourne null si aucune association n'est trouvée
        }

    }

    /**
     * push CompteEntreprise object and update session object (transactionID, jsonData).
     */
    public void pushCompteEntreprise(CompteEntrepriseDTO compteEntrepriseDTO) throws JsonProcessingException {
        CompteProDTO compteProDTO = compteEntrepriseDTO.getComptePro();
        EntrepriseDTO entrepriseDTO = compteEntrepriseDTO.getEntreprise();
        List<String> roleNames = associationRepository.findRoleNamesByCompteProIdAndEntrepriseId(
                compteProDTO.getId(),
                entrepriseDTO.getId());

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
    public CompteEntrepriseDTO checkAuthenticationStep2(String transactionId) throws JsonProcessingException {
        Session session = sessionRepository.findByTransactionId(transactionId)
                .filter(s -> s.getStatus() == Session.Status.COMPLETED)
                .orElseThrow(() -> new EntityNotFoundException("Session not completed or not found."));

        // Convertir la chaîne JSON en objet Map<String, Object>
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> dataMap = objectMapper.readValue(session.getJsonData(), new TypeReference<Map<String, Object>>() {});
        // Extraire les données de la map et les convertir en objets DTO
        EntrepriseDTO entrepriseDTO = objectMapper.convertValue(dataMap.get("entreprise"), EntrepriseDTO.class);
        CompteProDTO compteProDTO = objectMapper.convertValue(dataMap.get("comptePro"), CompteProDTO.class);
        List<String> roles = objectMapper.convertValue(dataMap.get("roles"), new TypeReference<List<String>>() {});
        // Créer un objet CompteEntrepriseDTO à partir des objets DTO obtenus
        CompteEntrepriseDTO compteEntrepriseDTO = new CompteEntrepriseDTO();
        compteEntrepriseDTO.setEntreprise(entrepriseDTO);
        compteEntrepriseDTO.setComptePro(compteProDTO);
        compteEntrepriseDTO.setRoles(roles);

        return compteEntrepriseDTO;
    }
    /**
     * create an association
     */
    public AssociationDTO createAssociation(AssociationDTO dto) {
        Association association = associationMapper.toEntity(dto);
        association.setCompte(compteProRepository.findById(dto.getCompteID())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compte not found")));
        association.setEntreprise(entrepriseRepository.findById(dto.getEntrepriseID())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entreprise not found")));
        association.setStatut(StatutAssociation.PENDING);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }
    /**
     * update an association accepter ou annuler association
     */
    public AssociationDTO updateStatut(Long associationId, StatutAssociation nouveauStatut) {
        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Association not found with id " + associationId));

        if (nouveauStatut != StatutAssociation.ACCEPTED && nouveauStatut != StatutAssociation.CANCELED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Statut must be ACCEPTED or CANCELED");
        }

        association.setStatut(nouveauStatut);
        association = associationRepository.save(association);

        return associationMapper.toDto(association);
    }


}
