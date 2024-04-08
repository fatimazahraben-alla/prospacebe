package ma.digital.prospace.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.FournisseurService;
import ma.digital.prospace.repository.FournisseurServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.Rolee;
import ma.digital.prospace.repository.RoleeRepository;
import ma.digital.prospace.service.mapper.RoleeMapper;
import ma.digital.prospace.service.dto.RoleeDTO;
import org.springframework.web.server.ResponseStatusException;


/**
 * Service Implementation for managing {@link Rolee}.
 */
@Service
@Transactional
public class RoleeService {

    private final Logger log = LoggerFactory.getLogger(RoleeService.class);

    private final RoleeRepository roleeRepository;
    private final FournisseurServiceRepository fournisseurServiceRepository;
    private final RoleeMapper roleeMapper;

    public RoleeService(RoleeRepository roleeRepository, RoleeMapper roleeMapper, FournisseurServiceRepository fournisseurServiceRepository) {
        this.roleeRepository = roleeRepository;
        this.roleeMapper = roleeMapper;
        this.fournisseurServiceRepository = fournisseurServiceRepository;
    }

    /**
     * Save a rolee.
     *
     * @param roleeDTO the entity to save.
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
     * @param roleeDTO the entity to update.
     * @return the updated entity.
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
     * @param roleeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RoleeDTO> partialUpdate(RoleeDTO roleeDTO) {
        log.debug("Request to partially update Rolee : {}", roleeDTO);

        return roleeRepository.findById(roleeDTO.getId())
                .map(existingRolee -> {
                    roleeMapper.partialUpdate(existingRolee, roleeDTO);
                    Rolee updatedRolee = roleeRepository.save(existingRolee);
                    return roleeMapper.toDto(updatedRolee);
                });
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
    public RoleeDTO createRolee(RoleeDTO roleeDTO) {
        log.debug("Request to create Rolee : {}", roleeDTO);
        Rolee rolee = roleeMapper.toEntity(roleeDTO);
        rolee = roleeRepository.save(rolee);
        return roleeMapper.toDto(rolee);
    }

    public RoleeDTO updateRolee(RoleeDTO roleeDTO) {
        log.debug("Request to update Rolee : {}", roleeDTO);
        Rolee rolee = roleeMapper.toEntity(roleeDTO);
        rolee = roleeRepository.save(rolee);
        return roleeMapper.toDto(rolee);
    }
    @Transactional(readOnly = true)
    public List<RoleeDTO> findAll() {
        return roleeRepository.findAll().stream()
                .map(roleeMapper::toDto)
                .collect(Collectors.toList());
    }
}
