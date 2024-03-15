package ma.digital.prospace.service;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.service.mapper.AssociationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Constructor
    public AssociationService(AssociationRepository associationRepository,
                              AssociationMapper associationMapper,
                              SessionRepository sessionRepository,
                              CompteProRepository compteProRepository,
                              EntrepriseRepository entrepriseRepository,
                              RoleeRepository roleeRepository,
                              NotificationService notificationService,
                              ObjectMapper objectMapper) { // Add ObjectMapper to the constructor
        this.associationRepository = associationRepository;
        this.associationMapper = associationMapper;
        this.sessionRepository = sessionRepository;
        this.compteProRepository = compteProRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.roleeRepository = roleeRepository;
        this.notificationService = notificationService;
        this.objectMapper = objectMapper; // Initialize ObjectMapper
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

    public List<CompteFSAssociationDTO> processAuthenticationStep2(Long compteID, Long fs) {
        List<Association> associations = associationRepository.findAllByFsAndCompteID(fs, compteID);

        List<CompteFSAssociationDTO> responses = new ArrayList<>();

        if (associations != null && !associations.isEmpty()) {
            // Créer une session pour toutes les associations qui correspondent aux critères de recherche
            Session session = new Session();
            session.setTransactionId(String.valueOf(UUID.fromString(UUID.randomUUID().toString()).getMostSignificantBits()));
            session.setCreatedAt(new Date());
            session.setStatus(Session.Status.IN_PROGRESS);
            sessionRepository.save(session);

            //Contact contact = contactRepository.findByCompteProId(compteID);
            //String deviceToken = contact.getDeviceToken();

            // Créer les DTOs pour toutes les associations
            for (Association association : associations) {
                Entreprise entreprise = association.getEntreprise();
                String entrepriseString = Objects.toString(entreprise, null);

                CompteFSAssociationDTO responseDTO = new CompteFSAssociationDTO();
                responseDTO.setCompteID(compteID);
                responseDTO.setFs(fs);
                responseDTO.setEntreprises(Collections.singletonList(entrepriseString));

                responses.add(responseDTO);
            }
        }

        return responses;
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
                .filter(s -> s.getStatus() != Session.Status.COMPLETED)
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

}
