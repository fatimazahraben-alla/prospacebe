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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Contact;
import ma.digital.prospace.repository.ContactRepository;

import java.util.List;
import java.util.UUID;


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

    public void acceptInvitation(UUID invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitation not found with id: " + invitationId));
        invitation.setStatut(StatutInvitation.ACCEPTED);
        invitationRepository.save(invitation);

        //comptePro associated with this invitation

        String compIdUUID = invitation.getComptePro().getId();
        Contact contact = contactRepository.findByCompteProId(compIdUUID);

        if (contact == null) {
            throw new EntityNotFoundException("Contact not found for ComptePro ID: " + compIdUUID);
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
                System.out.println("No device token available for contact with ComptePro ID: " + compIdUUID);
            }
        } catch (Exception e) {
        }
    }


    public InvitationDTO createInvitation(InvitationDTO invitationDTO) {
        // Vérifier l'existence du compte pro destinataire
        ComptePro comptePro = compteProRepository.findById(invitationDTO.getCompteProDestinataireId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le compte pro destinataire n'existe pas : " + invitationDTO.getCompteProDestinataireId()));

        if (!isValidEmail(invitationDTO.getMail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format de l'adresse e-mail invalide.");
        }

        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        invitation.setStatut(StatutInvitation.PENDING);
        invitation.setComptePro(comptePro);

        invitation = invitationRepository.save(invitation);

        Contact contact = contactRepository.findByCompteProId(invitationDTO.getCompteProDestinataireId());
        if (contact != null && contact.getDeviceToken() != null && !contact.getDeviceToken().isEmpty()) {
            sendFirebaseNotification(contact.getDeviceToken(), "Nouvelle Invitation", "Vous avez reçu une nouvelle invitation.");
        } else {
            //absence de token
        }

        return invitationMapper.toDto(invitation);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private void sendFirebaseNotification(String deviceToken, String title, String body) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(deviceToken)
                    .setNotification(notification)
                    .build();

            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            // Handle the exception (e.g. log it)
            e.printStackTrace();
        }
    }
    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }
}
