package ma.digital.prospace.service;

import java.util.List;
import java.util.Optional;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.repository.EntrepriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entreprise}.
 */
@Service
@Transactional
public class EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseService.class);

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save.
     * @return the persisted entity.
     */
    public Entreprise save(Entreprise entreprise) {
        log.debug("Request to save Entreprise : {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    /**
     * Update a entreprise.
     *
     * @param entreprise the entity to save.
     * @return the persisted entity.
     */
    public Entreprise update(Entreprise entreprise) {
        log.debug("Request to update Entreprise : {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    /**
     * Partially update a entreprise.
     *
     * @param entreprise the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Entreprise> partialUpdate(Entreprise entreprise) {
        log.debug("Request to partially update Entreprise : {}", entreprise);

        return entrepriseRepository
            .findById(entreprise.getId())
            .map(existingEntreprise -> {
                if (entreprise.getDenomination() != null) {
                    existingEntreprise.setDenomination(entreprise.getDenomination());
                }
                if (entreprise.getStatutJuridique() != null) {
                    existingEntreprise.setStatutJuridique(entreprise.getStatutJuridique());
                }
                if (entreprise.getTribunal() != null) {
                    existingEntreprise.setTribunal(entreprise.getTribunal());
                }
                if (entreprise.getNumeroRC() != null) {
                    existingEntreprise.setNumeroRC(entreprise.getNumeroRC());
                }
                if (entreprise.getIce() != null) {
                    existingEntreprise.setIce(entreprise.getIce());
                }
                if (entreprise.getActivite() != null) {
                    existingEntreprise.setActivite(entreprise.getActivite());
                }
                if (entreprise.getFormeJuridique() != null) {
                    existingEntreprise.setFormeJuridique(entreprise.getFormeJuridique());
                }
                if (entreprise.getDateImmatriculation() != null) {
                    existingEntreprise.setDateImmatriculation(entreprise.getDateImmatriculation());
                }
                if (entreprise.getEtat() != null) {
                    existingEntreprise.setEtat(entreprise.getEtat());
                }

                return existingEntreprise;
            })
            .map(entrepriseRepository::save);
    }

    /**
     * Get all the entreprises.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Entreprise> findAll() {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll();
    }

    /**
     * Get one entreprise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Entreprise> findOne(Long id) {
        log.debug("Request to get Entreprise : {}", id);
        return entrepriseRepository.findById(id);
    }

    /**
     * Delete the entreprise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
    }
}
