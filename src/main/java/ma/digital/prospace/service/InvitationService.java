package ma.digital.prospace.service;

import ma.digital.prospace.repository.InvitationRepository;
import ma.digital.prospace.service.mapper.InvitationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private InvitationMapper invitationMapper;

}

