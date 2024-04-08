package ma.digital.prospace.service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.InvitationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.service.mapper.ProcurationMapper;
import ma.digital.prospace.service.dto.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * Service Implementation for managing {@link Procuration}.
 */
@Service
@Transactional
public class ProcurationService {

    private final Logger log = LoggerFactory.getLogger(ProcurationService.class);

    private final ProcurationRepository procurationRepository;

    private final ProcurationMapper procurationMapper;
    private final InvitationRepository invitationRepository;
    private final InvitationService invitationService;
    private final CompteProRepository compteProRepository;

    public ProcurationService(ProcurationRepository procurationRepository, ProcurationMapper procurationMapper, InvitationRepository invitationRepository, InvitationService invitationService, CompteProRepository compteProRepository) {
        this.procurationRepository = procurationRepository;
        this.procurationMapper = procurationMapper;
        this.invitationRepository = invitationRepository;
        this.compteProRepository = compteProRepository;
        this.invitationService = invitationService;
    }

    /**
     * Save a procuration.
     *
     * @param procurationDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcurationDTO save(ProcurationDTO procurationDTO) {
        log.debug("Request to save Procuration : {}", procurationDTO);
        Procuration procuration = procurationMapper.toEntity(procurationDTO);
        procuration = procurationRepository.save(procuration);
        return procurationMapper.toDto(procuration);
    }

    /**
     * Update a procuration.
     *
     * @param procurationDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcurationDTO update(ProcurationDTO procurationDTO) {
        log.debug("Request to update Procuration : {}", procurationDTO);
        Procuration procuration = procurationMapper.toEntity(procurationDTO);
        procuration = procurationRepository.save(procuration);
        return procurationMapper.toDto(procuration);
    }

    /**
     * Partially update a procuration.
     *
     * @param procuration the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcurationDTO> partialUpdate(ProcurationDTO procuration) {
        log.debug("Request to partially update Procuration : {}", procuration);

        return procurationRepository
            .findById(procuration.getId())
            .map(existingProcuration -> {
                procurationMapper.partialUpdate(existingProcuration, procuration);

                return existingProcuration;
            })
            .map(procurationRepository::save)
            .map(procurationMapper::toDto);
    }

    /**
     * Get all the procurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Procurations");
        return procurationRepository.findAll(pageable).map(procurationMapper::toDto);
    }

    /**
     * Get one procuration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcurationDTO> findOne(Long id) {
        log.debug("Request to get Procuration : {}", id);
        return procurationRepository.findById(id).map(procurationMapper::toDto);
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
    /**
     * create an procuration and accepte invitation
     */
   /* public ProcurationDTO createProcuration(ProcurationDTO procurationDTO, Long invitationId) {
        ComptePro gestionnaire = compteProRepository.findById(procurationDTO.getGestionnaireEspacePro())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gestionnaire Espace Pro not found"));
        ComptePro utilisateur = compteProRepository.findById(procurationDTO.getUtilisateurPro())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Utilisateur Pro not found"));

        Procuration procuration = procurationMapper.toEntity(procurationDTO);
        procuration = procurationRepository.save(procuration);

        // Accepter l'invitation
        invitationService.acceptInvitation(invitationId);

        return procurationMapper.toDto(procuration);
    }*/
    private void updateInvitationStatus(Long invitationId, StatutInvitation statut) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitation not found with id: " + invitationId));
        invitation.setStatut(statut);
        invitationRepository.save(invitation);
    }



}
