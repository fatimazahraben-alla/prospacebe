package ma.digital.prospace.service;

import java.util.List;
import java.util.Optional;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.repository.FournisseurServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FournisseurService}.
 */
@Service
@Transactional
public class FournisseurServiceService {

    private final Logger log = LoggerFactory.getLogger(FournisseurServiceService.class);

    private final FournisseurServiceRepository fournisseurServiceRepository;

    public FournisseurServiceService(FournisseurServiceRepository fournisseurServiceRepository) {
        this.fournisseurServiceRepository = fournisseurServiceRepository;
    }

    /**
     * Save a fournisseurService.
     *
     * @param fournisseurService the entity to save.
     * @return the persisted entity.
     */
    public FournisseurService save(FournisseurService fournisseurService) {
        log.debug("Request to save FournisseurService : {}", fournisseurService);
        return fournisseurServiceRepository.save(fournisseurService);
    }

    /**
     * Update a fournisseurService.
     *
     * @param fournisseurService the entity to save.
     * @return the persisted entity.
     */
    public FournisseurService update(FournisseurService fournisseurService) {
        log.debug("Request to update FournisseurService : {}", fournisseurService);
        return fournisseurServiceRepository.save(fournisseurService);
    }

    /**
     * Partially update a fournisseurService.
     *
     * @param fournisseurService the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FournisseurService> partialUpdate(FournisseurService fournisseurService) {
        log.debug("Request to partially update FournisseurService : {}", fournisseurService);

        return fournisseurServiceRepository
            .findById(fournisseurService.getId())
            .map(existingFournisseurService -> {
                if (fournisseurService.getNom() != null) {
                    existingFournisseurService.setNom(fournisseurService.getNom());
                }
                if (fournisseurService.getDescription() != null) {
                    existingFournisseurService.setDescription(fournisseurService.getDescription());
                }

                return existingFournisseurService;
            })
            .map(fournisseurServiceRepository::save);
    }

    /**
     * Get all the fournisseurServices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FournisseurService> findAll() {
        log.debug("Request to get all FournisseurServices");
        return fournisseurServiceRepository.findAll();
    }

    /**
     * Get one fournisseurService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FournisseurService> findOne(Long id) {
        log.debug("Request to get FournisseurService : {}", id);
        return fournisseurServiceRepository.findById(id);
    }

    /**
     * Delete the fournisseurService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FournisseurService : {}", id);
        fournisseurServiceRepository.deleteById(id);
    }
}