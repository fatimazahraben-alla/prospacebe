package ma.digital.prospace.service;

import java.util.*;
import java.util.stream.Collectors;

import ma.digital.prospace.domain.*;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.SessionRepository;
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
import ma.digital.prospace.service.dto.CompteFSAssociation;
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


    /**
     *
     * @param compteID
     * @param fs
     * @return
     */
    public List<Association> processAuthenticationStep2(Long compteID, Long fs) {
        List<Association> associations = associationRepository.findAllByFsAndCompteID(fs, compteID);

        List<Association> responses = new ArrayList<>();

        if (associations != null && !associations.isEmpty()) {
            for (Association association : associations) {
                Session session = new Session();
                session.setTransactionId(UUID.fromString(UUID.randomUUID().toString()).getMostSignificantBits());
                session.setCreatedAt(new Date());
                session.setStatus(Session.Status.IN_PROGRESS);
                sessionRepository.save(session);

                Contact contact = contactRepository.findByCompteProId(compteID);
                String deviceToken = contact.getDeviceToken();
                List<Entreprise> entreprises = associationRepository.findAllDistinctByCompteIdAndRoleFsId(compteID, fs);
                List<String> entrepriseStrings = entreprises.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());

                CompteFSAssociation responseDTO = new CompteFSAssociation();
                responseDTO.setCompteID(compteID);
                responseDTO.setFs(fs);
                responseDTO.setEntreprises(entrepriseStrings);

                sendMobileNotification(deviceToken, session.getTransactionId(), fs, compteID, entreprises);

                responses.add(association);
            }
        }

        return responses;
    }


    private void sendMobileNotification(String deviceToken, Long transactionId, Long fs, Long compteID, List<Entreprise> entreprises) {
    }

}
