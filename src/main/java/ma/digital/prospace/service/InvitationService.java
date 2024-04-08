package ma.digital.prospace.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.repository.ContactRepository;
import org.springframework.beans.BeanUtils;
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
import java.util.List;

@Service
@Transactional
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final InvitationMapper invitationMapper;
    private final ContactRepository contactRepository;
    private final CompteProRepository compteProRepository;
    private final FirebaseMessaging firebaseMessaging;
    @Autowired
    public InvitationService(InvitationRepository invitationRepository, InvitationMapper invitationMapper, CompteProRepository compteProRepository, ContactRepository contactRepository, FirebaseMessaging firebaseMessaging) {
        this.invitationRepository = invitationRepository;
        this.invitationMapper = invitationMapper;
        this.compteProRepository = compteProRepository;
        this.contactRepository = contactRepository;
        this.firebaseMessaging = firebaseMessaging;
    }

    public void acceptInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitation not found with id: " + invitationId));
        invitation.setStatut(StatutInvitation.ACCEPTED);
        invitationRepository.save(invitation);

        //comptePro associated with this invitation
        Long compteProId = invitation.getComptePro().getId();
        Contact contact = contactRepository.findByCompteProId(compteProId);

        if (contact == null) {
            throw new EntityNotFoundException("Contact not found for ComptePro ID: " + compteProId);
        }

        try {
            if (contact.getDeviceToken() != null && !contact.getDeviceToken().isEmpty()) {
                sendFirebaseNotification(
                        contact.getDeviceToken(),
                        "Invitation Accepted",
                        "Your invitation has been accepted."
                );
            } else {
                //no device token
                System.out.println("No device token available for contact with ComptePro ID: " + compteProId);
            }
        } catch (Exception e) {
        }
    }


    public InvitationDTO createInvitation(InvitationDTO invitationDTO) {

        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        invitation.setStatut(StatutInvitation.PENDING);

        invitationRepository.save(invitation);

        Contact contact = contactRepository.findByCompteProId(invitationDTO.getCompteProDestinataireId());
        if (contact == null) {
            //contact is not found
            throw new EntityNotFoundException("Contact not found for the provided compteProId");
        }

        //notification
        String deviceToken = contact.getDeviceToken();
        if (deviceToken != null && !deviceToken.isEmpty()) {
            //send notification
            sendFirebaseNotification(deviceToken, "Nouvelle Invitation", "Vous avez reçu une nouvelle invitation.");
        } else {
            //email if token not available
        }

        //return dto invitation
        return invitationMapper.toDto(invitation);
    }



    //Notification push
    private void sendFirebaseNotification(String deviceToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();

        firebaseMessaging.sendAsync(message);
    }
    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }
}
