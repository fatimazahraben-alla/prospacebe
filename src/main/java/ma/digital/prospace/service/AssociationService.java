package ma.digital.prospace.service;

import java.util.*;
import java.util.stream.Collectors;

import ma.digital.prospace.domain.*;
import ma.digital.prospace.repository.*;
import ma.digital.prospace.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.service.mapper.AssociationMapper;

@Service
@Transactional
public class AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;
    private final AssociationMapper associationMapper;
    private final SessionRepository sessionRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final CompteProRepository compteProRepository;
    private final RoleeRepository roleeRepository;

    // Constructor
    public AssociationService(SessionRepository sessionRepository, CompteProRepository compteProRepository,
                              EntrepriseRepository entrepriseRepository, RoleeRepository roleeRepository,
                              AssociationRepository associationRepository) {
        this.sessionRepository = sessionRepository;
        this.compteProRepository = compteProRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.roleeRepository = roleeRepository;
        this.associationRepository = associationRepository;
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

    public ResponseEntity<?> pushCompteEntreprise(CompteEntrepriseDTO compteEntrepriseDTO, String transactionId) {
        // Get roles associated with compte, FS, and selected enterprise
        List<Rolee> rolees = roleeRepository.findByFournisseurIDAndSelectedEntreprise(compteEntrepriseDTO.getCompteID(), compteEntrepriseDTO.getFs());

        // Initialize jsonData
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("compte", compteEntrepriseDTO.getCompte());
        jsonData.put("entreprise", compteEntrepriseDTO.getEntreprise());
        jsonData.put("roles", rolees.stream().map(Rolee::getName).collect(Collectors.toList()));

        // Update session with transactionId and jsonData
        Optional<Session> optionalSession = sessionRepository.findByTransactionId(transactionId);
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            session.setTransactionId(transactionId);
            session.setJsonData(jsonData.toString()); // Convert map to JSON string
            session.setStatus("TERMINATED");
            sessionRepository.save(session);
            return ResponseEntity.ok().body("Session updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Session not found.");
        }
    }

    public SessionDTO checkAuthenticationStep2(String transactionId) {
        Optional<Session> optionalSession = sessionRepository.findByTransactionId(transactionId);
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            if ("TERMINATED".equals(session.getStatus())) {
                throw new IllegalStateException("Session already terminated.");
            }

            // Retrieve compte data
            Optional<ComptePro> optionalComptePro = compteProRepository.findById(session.getCompteId());
            ComptePro comptePro = optionalComptePro.orElseThrow(() -> new IllegalStateException("Failed to retrieve account data."));

            // Retrieve entreprise data
            Optional<Entreprise> optionalEntreprise = entrepriseRepository.findById(session.getEntrepriseId());
            Entreprise entreprise = optionalEntreprise.orElseThrow(() -> new IllegalStateException("Failed to retrieve company data."));

            // Retrieve associated roles
            List<Rolee> rolees = roleeRepository.findByFournisseurIDAndSelectedEntreprise(session.getCompteId(), session.getEntrepriseId());
            List<String> roles = rolees.stream().map(Rolee::getName).collect(Collectors.toList());

            // Prepare SessionDTO
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setCompte(mapCompteProToDTO(comptePro));
            sessionDTO.setEntreprise(mapEntrepriseToDTO(entreprise));
            sessionDTO.setRoles(roles);

            // session terminated
            session.setStatus("TERMINATED");
            sessionRepository.save(session);

            return sessionDTO;
        } else {
            throw new IllegalArgumentException("Session not found.");
        }
    }

    private CompteProDTO mapCompteProToDTO(ComptePro comptePro) {
        CompteProDTO compteProDTO = new CompteProDTO();
        return compteProDTO;
    }

    private EntrepriseDTO mapEntrepriseToDTO(Entreprise entreprise) {
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        return entrepriseDTO;
    }


}
