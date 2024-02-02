package ma.digital.prospace.service;

import java.util.List;
import java.util.Optional;
import ma.digital.prospace.domain.Association;
import ma.digital.prospace.repository.AssociationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Association}.
 */
@Service
@Transactional
public class AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;

    public AssociationService(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    /**
     * Save a association.
     *
     * @param association the entity to save.
     * @return the persisted entity.
     */
    public Association save(Association association) {
        log.debug("Request to save Association : {}", association);
        return associationRepository.save(association);
    }

    /**
     * Update a association.
     *
     * @param association the entity to save.
     * @return the persisted entity.
     */
    public Association update(Association association) {
        log.debug("Request to update Association : {}", association);
        return associationRepository.save(association);
    }

    /**
     * Partially update a association.
     *
     * @param association the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Association> partialUpdate(Association association) {
        log.debug("Request to partially update Association : {}", association);

        return associationRepository
            .findById(association.getId())
            .map(existingAssociation -> {
                if (association.getDateEffet() != null) {
                    existingAssociation.setDateEffet(association.getDateEffet());
                }
                if (association.getDateFin() != null) {
                    existingAssociation.setDateFin(association.getDateFin());
                }
                if (association.getMail() != null) {
                    existingAssociation.setMail(association.getMail());
                }
                if (association.getTelephone() != null) {
                    existingAssociation.setTelephone(association.getTelephone());
                }
                if (association.getStatut() != null) {
                    existingAssociation.setStatut(association.getStatut());
                }

                return existingAssociation;
            })
            .map(associationRepository::save);
    }

    /**
     * Get all the associations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Association> findAll() {
        log.debug("Request to get all Associations");
        return associationRepository.findAll();
    }

    /**
     * Get one association by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Association> findOne(Long id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id);
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
