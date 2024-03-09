package ma.digital.prospace.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import ma.digital.prospace.domain.*;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ContactRepository;
import ma.digital.prospace.repository.SessionRepository;
import ma.digital.prospace.service.dto.ContactDTO;
import ma.digital.prospace.service.dto.SessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.service.mapper.AssociationMapper;
import ma.digital.prospace.service.dto.AssociationDTO;
import ma.digital.prospace.service.dto.ResponseauthenticationDTO;
/**
 * Service Implementation for managing {@link Association}.
 */
@Service
@Transactional
public class AssociationService {

    private final Logger log = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;

    private final AssociationMapper associationMapper;
    @Autowired
    private CompteProRepository compteproRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ContactRepository contactRepository;

    public AssociationService(AssociationRepository associationRepository, AssociationMapper associationMapper) {
        this.associationRepository = associationRepository;
        this.associationMapper = associationMapper;
    }


    public AssociationDTO save(AssociationDTO associationDTO) {
        log.debug("Request to save Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }

    public AssociationDTO update(AssociationDTO associationDTO) {
        log.debug("Request to update Association : {}", associationDTO);
        Association association = associationMapper.toEntity(associationDTO);
        association = associationRepository.save(association);
        return associationMapper.toDto(association);
    }

    /**
     * Partially update a association.
     *
     * @param association the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssociationDTO> partialUpdate(AssociationDTO association) {
        log.debug("Request to partially update Association : {}", association);

        return associationRepository
                .findById(association.getId())
                .map(existingAssociation -> {
                    associationMapper.partialUpdate(existingAssociation, association);

                    return existingAssociation;
                })
                .map(associationRepository::save)
                .map(associationMapper::toDto);
    }

    /**
     * Get all the associations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssociationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Associations");
        return associationRepository.findAll(pageable).map(associationMapper::toDto);
    }

    /**
     * Get one association by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssociationDTO> findOne(Long id) {
        log.debug("Request to get Association : {}", id);
        return associationRepository.findById(id).map(associationMapper::toDto);
    }

    /**
     * Delete the association by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Association : {}", id);
        associationRepository.deleteById(id);
    }

    public ResponseEntity<ResponseauthenticationDTO> processAuthenticationStep2(Long compteID, Long fs) {

        Association association = associationRepository.findByFsAndCompteID(fs, compteID);

        if (association != null) {

            Session session = new Session();
            session.setTransactionId(UUID.fromString(UUID.randomUUID().toString()).getMostSignificantBits());
            session.setCreatedAt(new Date());
            session.setJsonData("IN_PROGRESS");
            sessionRepository.save(session);
            ComptePro compte = compteproRepository.getById(compteID);
            Contact contact = compte.getContact();
            String deviceToken = contact.getDeviceToken();
            List<Entreprise> entreprises = associationRepository.getListEntreprisesByCompteAndFs(compteID, fs);
            List<String> entrepriseStrings = convertEntreprisesToStrings(entreprises);
            ResponseauthenticationDTO responseDTO = new ResponseauthenticationDTO();
            responseDTO.setCompteID(compteID);
            responseDTO.setFs(fs);
            responseDTO.setEntreprises(entrepriseStrings);
            //////PUSH NOTIFICATION
            sendMobileNotification(deviceToken, session.getTransactionId(), fs, compteID, entreprises);
            return ResponseEntity.ok().body(responseDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private void sendMobileNotification(String deviceToken, Long transactionId, Long fs, Long
            compteID, List<Entreprise> entreprises) {
        Message message = Message.builder()
                .setToken(deviceToken)
                .putData("transactionId", String.valueOf(transactionId))
                .putData("fs", String.valueOf(fs))
                .putData("compteID", String.valueOf(compteID))
                .putData("entreprises", convertEntreprisesListToString(entreprises))
                .build();

        try {

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Message sent to Firebase: " + response);
        } catch (FirebaseMessagingException e) {
            // Gérez les erreurs lors de l'envoi de la notification
            e.printStackTrace();
        }
    }

    public String convertEntreprisesListToString(List<Entreprise> entreprises) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entreprise entreprise : entreprises) {
            stringBuilder.append(entreprise.getId()).append(", "); // Ajoutez les détails de l'entreprise que vous souhaitez inclure
        }
        return stringBuilder.toString();
    }

    public List<String> convertEntreprisesToStrings(List<Entreprise> entreprises) {
        return entreprises.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

    }



}
