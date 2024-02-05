package ma.digital.prospace.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.repository.FournisseurServiceRepository;
import ma.digital.prospace.service.mapper.FournisseurServiceMapper;
import ma.digital.prospace.service.dto.*;


/**
 * Service Implementation for managing {@link FournisseurService}.
 */
@Service
@Transactional
public class FournisseurServiceService {

    private final Logger log = LoggerFactory.getLogger(FournisseurServiceService.class);

    private final FournisseurServiceRepository fournisseurServiceRepository;

    private final FournisseurServiceMapper fournisseurServiceMapper;

    public FournisseurServiceService(
        FournisseurServiceRepository fournisseurServiceRepository,
        FournisseurServiceMapper fournisseurServiceMapper
    ) {
        this.fournisseurServiceRepository = fournisseurServiceRepository;
        this.fournisseurServiceMapper = fournisseurServiceMapper;
    }

    /**
     * Save a fournisseurService.
     *
     * @param fournisseurService the entity to save.
     * @return the persisted entity.
     */
    public FournisseurServiceDTO save(FournisseurServiceDTO fournisseurServiceDTO) {
        log.debug("Request to save FournisseurService : {}", fournisseurServiceDTO);
        FournisseurService fournisseurService = fournisseurServiceMapper.toEntity(fournisseurServiceDTO);
        fournisseurService = fournisseurServiceRepository.save(fournisseurService);
        return fournisseurServiceMapper.toDto(fournisseurService);
    }

    /**
     * Update a fournisseurService.
     *
     * @param fournisseurService the entity to save.
     * @return the persisted entity.
     */
    public FournisseurServiceDTO update(FournisseurServiceDTO fournisseurServiceDTO) {
        log.debug("Request to update FournisseurService : {}", fournisseurServiceDTO);
        FournisseurService fournisseurService = fournisseurServiceMapper.toEntity(fournisseurServiceDTO);
        fournisseurService = fournisseurServiceRepository.save(fournisseurService);
        return fournisseurServiceMapper.toDto(fournisseurService);
    }

    /**
     * Partially update a fournisseurService.
     *
     * @param fournisseurService the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FournisseurServiceDTO> partialUpdate(FournisseurServiceDTO fournisseurService) {
        log.debug("Request to partially update FournisseurService : {}", fournisseurService);

        return fournisseurServiceRepository
            .findById(fournisseurService.getId())
            .map(existingFournisseurService -> {
                fournisseurServiceMapper.partialUpdate(existingFournisseurService, fournisseurService);

                return existingFournisseurService;
            })
            .map(fournisseurServiceRepository::save)
            .map(fournisseurServiceMapper::toDto);
    }

    /**
     * Get all the fournisseurServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FournisseurServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FournisseurServices");
        return fournisseurServiceRepository.findAll(pageable).map(fournisseurServiceMapper::toDto);
    }

    /**
     * Get one fournisseurService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FournisseurServiceDTO> findOne(Long id) {
        log.debug("Request to get FournisseurService : {}", id);
        return fournisseurServiceRepository.findById(id).map(fournisseurServiceMapper::toDto);
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
