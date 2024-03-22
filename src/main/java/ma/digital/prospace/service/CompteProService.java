package ma.digital.prospace.service;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.domain.enumeration.StatutCompte;
import ma.digital.prospace.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.service.mapper.CompteProMapper;
import ma.digital.prospace.service.dto.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service Implementation for managing {@link ComptePro}.
 */
@Service
@Transactional
public class CompteProService {

    private final Logger log = LoggerFactory.getLogger(CompteProService.class);

    private final CompteProRepository compteProRepository;

    private final CompteProMapper compteProMapper;

    private final ContactRepository contactRepository;

    public CompteProService(CompteProRepository compteProRepository, CompteProMapper compteProMapper,ContactRepository contactRepository) {
        this.compteProRepository = compteProRepository;
        this.compteProMapper = compteProMapper;
        this.contactRepository = contactRepository;
    }

    /**
     * Save a comptePro.
     *
     * @param compteProDTO the entity to save.
     * @return the persisted entity.
     */
    public CompteProDTO save(CompteProDTO compteProDTO) {
        log.debug("Request to save ComptePro : {}", compteProDTO);
        ComptePro comptePro = compteProMapper.toEntity(compteProDTO);
        comptePro = compteProRepository.save(comptePro);
        return compteProMapper.toDto(comptePro);
    }

    /**
     * Update a comptePro.
     *
     * @param compteProDTO the entity to save.
     * @return the persisted entity.
     */
    public CompteProDTO update(CompteProDTO compteProDTO) {
        log.debug("Request to update ComptePro : {}", compteProDTO);
        ComptePro comptePro = compteProMapper.toEntity(compteProDTO);
        comptePro = compteProRepository.save(comptePro);
        return compteProMapper.toDto(comptePro);
    }

    /**
     * Partially update a comptePro.
     *
     * @param comptePro the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompteProDTO> partialUpdate(CompteProDTO comptePro) {
        log.debug("Request to partially update ComptePro : {}", comptePro);

        return compteProRepository
            .findById(comptePro.getId())
            .map(existingComptePro -> {
                compteProMapper.partialUpdate(existingComptePro, comptePro);

                return existingComptePro;
            })
            .map(compteProRepository::save)
            .map(compteProMapper::toDto);
    }





    /**
     * Get all the comptePros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompteProDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComptePros");
        return compteProRepository.findAll(pageable).map(compteProMapper::toDto);
    }

    /**
     * Get one comptePro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompteProDTO> findOne(Long id) {
        log.debug("Request to get ComptePro : {}", id);
        return compteProRepository.findById(id).map(compteProMapper::toDto);
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

    public void registerContactDTO(MobileRegistrationDTO requestDTO) {

        ComptePro comptePro = compteProRepository.findById(requestDTO.getCompteId())

                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compte not found"));

        Contact contact = contactRepository.findByCompteProId(requestDTO.getCompteId());

        if (contact == null) {

            // Contact doesn't exist, create a new one

            contact = new Contact();

            contact.setComptePro(comptePro);

        }
        // Update contact fields

        contact.setDeviceToken(requestDTO.getDeviceToken());

        contact.setDeviceOS(requestDTO.getDeviceOS());

        contact.setDeviceVersion(requestDTO.getDeviceVersion());
        // Save or update the contact

        contactRepository.save(contact);

    }
    /**
     * create an account
     */
    public CompteProDTO createCompte(CompteProDTO compteDTO) {
        ComptePro comptePro = compteProMapper.toEntity(compteDTO);
        comptePro.setStatut(StatutCompte.VALIDE);
        comptePro = compteProRepository.save(comptePro);
        return compteProMapper.toDto(comptePro);
    }

}
