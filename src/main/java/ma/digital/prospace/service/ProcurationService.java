package ma.digital.prospace.service;

import java.util.List;
import java.util.Optional;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.repository.ProcurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Procuration}.
 */
@Service
@Transactional
public class ProcurationService {

    private final Logger log = LoggerFactory.getLogger(ProcurationService.class);

    private final ProcurationRepository procurationRepository;

    public ProcurationService(ProcurationRepository procurationRepository) {
        this.procurationRepository = procurationRepository;
    }

    /**
     * Save a procuration.
     *
     * @param procuration the entity to save.
     * @return the persisted entity.
     */
    public Procuration save(Procuration procuration) {
        log.debug("Request to save Procuration : {}", procuration);
        return procurationRepository.save(procuration);
    }

    /**
     * Update a procuration.
     *
     * @param procuration the entity to save.
     * @return the persisted entity.
     */
    public Procuration update(Procuration procuration) {
        log.debug("Request to update Procuration : {}", procuration);
        return procurationRepository.save(procuration);
    }

    /**
     * Partially update a procuration.
     *
     * @param procuration the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Procuration> partialUpdate(Procuration procuration) {
        log.debug("Request to partially update Procuration : {}", procuration);

        return procurationRepository
            .findById(procuration.getId())
            .map(existingProcuration -> {
                if (procuration.getDateEffet() != null) {
                    existingProcuration.setDateEffet(procuration.getDateEffet());
                }
                if (procuration.getDateFin() != null) {
                    existingProcuration.setDateFin(procuration.getDateFin());
                }

                return existingProcuration;
            })
            .map(procurationRepository::save);
    }

    /**
     * Get all the procurations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Procuration> findAll() {
        log.debug("Request to get all Procurations");
        return procurationRepository.findAll();
    }

    /**
     * Get one procuration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Procuration> findOne(Long id) {
        log.debug("Request to get Procuration : {}", id);
        return procurationRepository.findById(id);
    }

    /**
     * Delete the procuration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Procuration : {}", id);
        procurationRepository.deleteById(id);
    }
}
