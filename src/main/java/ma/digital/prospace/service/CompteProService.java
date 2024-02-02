package ma.digital.prospace.service;

import java.util.List;
import java.util.Optional;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.repository.CompteProRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComptePro}.
 */
@Service
@Transactional
public class CompteProService {

    private final Logger log = LoggerFactory.getLogger(CompteProService.class);

    private final CompteProRepository compteProRepository;

    public CompteProService(CompteProRepository compteProRepository) {
        this.compteProRepository = compteProRepository;
    }

    /**
     * Save a comptePro.
     *
     * @param comptePro the entity to save.
     * @return the persisted entity.
     */
    public ComptePro save(ComptePro comptePro) {
        log.debug("Request to save ComptePro : {}", comptePro);
        return compteProRepository.save(comptePro);
    }

    /**
     * Update a comptePro.
     *
     * @param comptePro the entity to save.
     * @return the persisted entity.
     */
    public ComptePro update(ComptePro comptePro) {
        log.debug("Request to update ComptePro : {}", comptePro);
        return compteProRepository.save(comptePro);
    }

    /**
     * Partially update a comptePro.
     *
     * @param comptePro the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComptePro> partialUpdate(ComptePro comptePro) {
        log.debug("Request to partially update ComptePro : {}", comptePro);

        return compteProRepository
            .findById(comptePro.getId())
            .map(existingComptePro -> {
                if (comptePro.getIdentifiant() != null) {
                    existingComptePro.setIdentifiant(comptePro.getIdentifiant());
                }
                if (comptePro.getTypeIdentifiant() != null) {
                    existingComptePro.setTypeIdentifiant(comptePro.getTypeIdentifiant());
                }
                if (comptePro.getNomAr() != null) {
                    existingComptePro.setNomAr(comptePro.getNomAr());
                }
                if (comptePro.getNomFr() != null) {
                    existingComptePro.setNomFr(comptePro.getNomFr());
                }
                if (comptePro.getPrenomAr() != null) {
                    existingComptePro.setPrenomAr(comptePro.getPrenomAr());
                }
                if (comptePro.getPrenomFr() != null) {
                    existingComptePro.setPrenomFr(comptePro.getPrenomFr());
                }
                if (comptePro.getAdresse() != null) {
                    existingComptePro.setAdresse(comptePro.getAdresse());
                }
                if (comptePro.getPhoto() != null) {
                    existingComptePro.setPhoto(comptePro.getPhoto());
                }
                if (comptePro.getPhotoContentType() != null) {
                    existingComptePro.setPhotoContentType(comptePro.getPhotoContentType());
                }
                if (comptePro.getMail() != null) {
                    existingComptePro.setMail(comptePro.getMail());
                }
                if (comptePro.getTelephone() != null) {
                    existingComptePro.setTelephone(comptePro.getTelephone());
                }
                if (comptePro.getStatut() != null) {
                    existingComptePro.setStatut(comptePro.getStatut());
                }

                return existingComptePro;
            })
            .map(compteProRepository::save);
    }

    /**
     * Get all the comptePros.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ComptePro> findAll() {
        log.debug("Request to get all ComptePros");
        return compteProRepository.findAll();
    }

    /**
     * Get one comptePro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComptePro> findOne(Long id) {
        log.debug("Request to get ComptePro : {}", id);
        return compteProRepository.findById(id);
    }

    /**
     * Delete the comptePro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComptePro : {}", id);
        compteProRepository.deleteById(id);
    }
}
