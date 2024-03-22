package ma.digital.prospace.service;

import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.ComptePro;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import ma.digital.prospace.domain.Invitation;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.InvitationRepository;
import ma.digital.prospace.service.dto.InvitationDTO;
import ma.digital.prospace.service.mapper.InvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;

    private final CompteProRepository compteProRepository;
    @Autowired
    public InvitationService(InvitationRepository invitationRepository, InvitationMapper invitationMapper, CompteProRepository compteProRepository) {
        this.invitationRepository = invitationRepository;
        this.invitationMapper = invitationMapper;
        this.compteProRepository = compteProRepository;
    }
    /**
     * create an invitation and set statut = PENDING
     */
    public InvitationDTO createInvitation(InvitationDTO invitationDTO) {
        if (invitationDTO.getCompteProId() != null && !compteProRepository.existsById(invitationDTO.getCompteProId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ComptePro with id " + invitationDTO.getCompteProId() + " not found");
        }

        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        invitation.setStatut(StatutInvitation.PENDING);
        invitation = invitationRepository.save(invitation);
        return invitationMapper.toDto(invitation);
    }

    public void acceptInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitation not found with id: " + invitationId));
        invitation.setStatut(StatutInvitation.ACCEPTED);
        invitationRepository.save(invitation);
    }
}
