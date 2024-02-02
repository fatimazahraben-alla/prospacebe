package ma.digital.prospace.service;

import java.util.List;
import java.util.Optional;
import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.repository.RoleeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rolee}.
 */
@Service
@Transactional
public class RoleeService {

    private final Logger log = LoggerFactory.getLogger(RoleeService.class);

    private final RoleeRepository roleeRepository;

    public RoleeService(RoleeRepository roleeRepository) {
        this.roleeRepository = roleeRepository;
    }

    /**
     * Save a rolee.
     *
     * @param rolee the entity to save.
     * @return the persisted entity.
     */
    public Rolee save(Rolee rolee) {
        log.debug("Request to save Rolee : {}", rolee);
        return roleeRepository.save(rolee);
    }

    /**
     * Update a rolee.
     *
     * @param rolee the entity to save.
     * @return the persisted entity.
     */
    public Rolee update(Rolee rolee) {
        log.debug("Request to update Rolee : {}", rolee);
        return roleeRepository.save(rolee);
    }

    /**
     * Partially update a rolee.
     *
     * @param rolee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Rolee> partialUpdate(Rolee rolee) {
        log.debug("Request to partially update Rolee : {}", rolee);

        return roleeRepository
            .findById(rolee.getId())
            .map(existingRolee -> {
                if (rolee.getNom() != null) {
                    existingRolee.setNom(rolee.getNom());
                }
                if (rolee.getDescription() != null) {
                    existingRolee.setDescription(rolee.getDescription());
                }

                return existingRolee;
            })
            .map(roleeRepository::save);
    }

    /**
     * Get all the rolees.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Rolee> findAll() {
        log.debug("Request to get all Rolees");
        return roleeRepository.findAll();
    }

    /**
     * Get one rolee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Rolee> findOne(Long id) {
        log.debug("Request to get Rolee : {}", id);
        return roleeRepository.findById(id);
    }

    /**
     * Delete the rolee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rolee : {}", id);
        roleeRepository.deleteById(id);
    }
}
