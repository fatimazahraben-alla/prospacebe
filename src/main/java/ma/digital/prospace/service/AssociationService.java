package ma.digital.prospace.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ma.digital.prospace.domain.*;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.SessionRepository;
import ma.digital.prospace.service.dto.ContactDTO;
import ma.digital.prospace.service.dto.SessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.service.mapper.AssociationMapper;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.ResponseauthenticationDTO;
/**
 * Service Implementation for managing {@link Association}.
 */
@Service
@Transactional
public class AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;

    private final AssociationMapper associationMapper;
    @Autowired
    private CompteProRepository compteproRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ContactRepository contactRepository;

    public AssociationService(AssociationRepository associationRepository, AssociationMapper associationMapper) {
        this.associationRepository = associationRepository;
        this.associationMapper = associationMapper;
    }

    /**
     * Save a association.
     *
     * @param association the entity to save.
     * @return the persisted entity.
     */
    public AssociationDTO save(AssociationDTO associationDTO) {
        log.debug("Request to save Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }

    /**
     * Update a association.
     *
     * @param association the entity to save.
     * @return the persisted entity.
     */
    public AssociationDTO update(AssociationDTO associationDTO) {
        log.debug("Request to update Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }

    /**
     * Partially update a association.
     *
     * @param association the entity to update partially.
     * @return the persisted entity.
     */
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

    /**
     * Get all the associations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssociationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Associations");
        return associationRepository.findAll(pageable).map(associationMapper::toDto);
    }

    /**
     * Get one association by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssociationDTO> findOne(Long id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id).map(associationMapper::toDto);
    }

    /**
     * Delete the association by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Association : {}", id);
        associationRepository.deleteById(id);
    }

    public ResponseEntity<?> processAuthenticationStep2(Long compteID, Long fs) {

        Association association = associationRepository.findByFsAndCompteID(fs, compteID);

        if (association != null) {

            Session session = new Session();
            session.setTransactionId(UUID.randomUUID().toString());
            session.setCreatedAt(new Date());
            session.setJsonData("IN_PROGRESS");
            sessionRepository.save(session);
            ComptePro compte = compteproRepository.getOne(compteID);
            Contact contact = compte.getContact();
            String deviceToken = contact.getDeviceToken();
            List<Entreprise> entreprises = association.getEntreprise();

                // Envoyer la notification mobile
                sendMobileNotification(deviceToken, session.getTransactionId(), fs, compteID, entreprises);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }



    private void sendMobileNotification(String deviceToken, String transactionId, String fs, String compteID, List<Entreprise> entreprises) {

    }


}
