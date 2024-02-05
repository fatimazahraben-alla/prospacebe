package ma.digital.prospace.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.Association;
import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.service.mapper.AssociationMapper;
import ma.digital.prospace.service.dto.AssociationDTO;

/**
 * Service Implementation for managing {@link Association}.
 */
@Service
@Transactional
public class AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;

    private final AssociationMapper associationMapper;

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
}
