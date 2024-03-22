package ma.digital.prospace.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.service.mapper.AssociationMapper;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
   private NotificationService notificationService;

    private final ObjectMapper objectMapper;

    @Autowired
    private final FirebaseMessaging firebaseMessaging;

    // Constructor
    public AssociationService(AssociationRepository associationRepository,
                              AssociationMapper associationMapper,
                              SessionRepository sessionRepository,
                              CompteProRepository compteProRepository,
                              EntrepriseRepository entrepriseRepository,
                              RoleeRepository roleeRepository,
                              ObjectMapper objectMapper,
                              NotificationService notificationService,
                              FirebaseMessaging firebaseMessaging
                             ) { // Add ObjectMapper to the constructor
        this.associationRepository = associationRepository;
        this.associationMapper = associationMapper;
        this.sessionRepository = sessionRepository;
        this.compteProRepository = compteProRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.notificationService = notificationService;
        this.roleeRepository = roleeRepository;
        this.objectMapper = objectMapper;
        this.firebaseMessaging = firebaseMessaging;
        // Initialize ObjectMapper

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
   public String constructAndSendPushNotification(String deviceToken,List<String> entrepriseList, String transactionID, Long fs, Long compteID,String Title,String Body) {
       // Construire la notification

       Notification notification = Notification.builder()
               .setTitle(Title)
               .setBody(Body)
               .build();

       // Construire les données supplémentaires (si nécessaire)
       Map<String, String> data = new HashMap<>();
       data.put("transactionID", transactionID);
       data.put("fs", String.valueOf(fs));
       data.put("compteID", String.valueOf(compteID));
       String entreprises = String.join(",", entrepriseList);
       data.put("entrepriseList", entreprises);


       // Construire le message FCM
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

    public CompteFSAssociationDTO processAuthenticationStep2(Long compteID, Long fs, String transactionID) {
        List<Association> associations = associationRepository.findAllByFsAndCompteID(fs, compteID);
        if (associations != null && !associations.isEmpty()) {
            Session session = new Session();
            session.setTransactionId(transactionID);
            session.setCreatedAt(new Date());
            session.setStatus(Session.Status.IN_PROGRESS);
            sessionRepository.save(session);
            Contact contact = contactRepository.findByCompteProId(compteID);
            String deviceToken = contact.getDeviceToken();
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
            constructAndSendPushNotification(deviceToken,entrepriseList,transactionID, fs, compteID,"Notification process auth","contenu");
            return responseDTO;
        } else {
            return null; // Retourne null si aucune association n'est trouvée
        }
    }


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


    public CompteEntrepriseDTO checkAuthenticationStep2(String transactionId) throws JsonProcessingException {
        Session session = sessionRepository.findByTransactionId(transactionId)
                .filter(s -> s.getStatus() == Session.Status.COMPLETED)
                .orElseThrow(() -> new EntityNotFoundException("Session not completed or not found."));

        // Convertir la chaîne JSON en objet Map<String, Object>
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> dataMap = objectMapper.readValue(session.getJsonData(), new TypeReference<Map<String, Object>>() {
        });
        // Extraire les données de la map et les convertir en objets DTO
        EntrepriseDTO entrepriseDTO = objectMapper.convertValue(dataMap.get("entreprise"), EntrepriseDTO.class);
        CompteProDTO compteProDTO = objectMapper.convertValue(dataMap.get("comptePro"), CompteProDTO.class);
        List<String> roles = objectMapper.convertValue(dataMap.get("roles"), new TypeReference<List<String>>() {
        });
        // Créer un objet CompteEntrepriseDTO à partir des objets DTO obtenus
        CompteEntrepriseDTO compteEntrepriseDTO = new CompteEntrepriseDTO();
        compteEntrepriseDTO.setEntreprise(entrepriseDTO);
        compteEntrepriseDTO.setComptePro(compteProDTO);
        compteEntrepriseDTO.setRoles(roles);

        return compteEntrepriseDTO;
    }


}
 /* try {
                NotificationMessage notificationMessage = new NotificationMessage();
                notificationMessage.setDeviceToken(deviceToken);
                notificationMessage.setTransactionID(transactionID);
                notificationMessage.setFs(fs);
                notificationMessage.setCompteID(compteID);
                notificationMessage.setTitle("Notification prospace");
                notificationMessage.setBody("Contenu");
                notificationService.sendNotificationByToken(notificationMessage);
            } catch (Exception e) {
                throw new NotificationSendingException("Erreur lors de l'envoi de la notification", e);
            }

            return responseDTO;
        } else {
            return null; // Retourner null si aucune association n'est trouvée
        }

            */