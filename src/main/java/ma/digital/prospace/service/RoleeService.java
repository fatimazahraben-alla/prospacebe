package ma.digital.prospace.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.repository.RoleeRepository;
import ma.digital.prospace.service.mapper.RoleeMapper;
import ma.digital.prospace.service.dto.*;


/**
 * Service Implementation for managing {@link Rolee}.
 */
@Service
@Transactional
public class RoleeService {

    private final Logger log = LoggerFactory.getLogger(RoleeService.class);

    private final RoleeRepository roleeRepository;

    private final RoleeMapper roleeMapper;

    public RoleeService(RoleeRepository roleeRepository, RoleeMapper roleeMapper) {
        this.roleeRepository = roleeRepository;
        this.roleeMapper = roleeMapper;
    }

    /**
     * Save a rolee.
     *
     * @param rolee the entity to save.
     * @return the persisted entity.
     */
    public RoleeDTO save(RoleeDTO roleeDTO) {
        log.debug("Request to save Rolee : {}", roleeDTO);
        Rolee rolee = roleeMapper.toEntity(roleeDTO);
        rolee = roleeRepository.save(rolee);
        return roleeMapper.toDto(rolee);
    }

    /**
     * Update a rolee.
     *
     * @param rolee the entity to save.
     * @return the persisted entity.
     */
    public RoleeDTO update(RoleeDTO roleeDTO) {
        log.debug("Request to update Rolee : {}", roleeDTO);
        Rolee rolee = roleeMapper.toEntity(roleeDTO);
        rolee = roleeRepository.save(rolee);
        return roleeMapper.toDto(rolee);
    }

    /**
     * Partially update a rolee.
     *
     * @param rolee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RoleeDTO> partialUpdate(RoleeDTO rolee) {
        log.debug("Request to partially update Rolee : {}", rolee);

        return roleeRepository
            .findById(rolee.getId())
            .map(existingRolee -> {
                roleeMapper.partialUpdate(existingRolee, rolee);

                return existingRolee;
            })
            .map(roleeRepository::save)
            .map(roleeMapper::toDto);
    }

    /**
     * Get all the rolees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rolees");
        return roleeRepository.findAll(pageable).map(roleeMapper::toDto);
    }

    /**
     * Get one rolee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RoleeDTO> findOne(Long id) {
        log.debug("Request to get Rolee : {}", id);
        return roleeRepository.findById(id).map(roleeMapper::toDto);
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
