package ma.digital.prospace.service;

import java.util.*;
import java.util.logging.Level;

import ma.digital.prospace.domain.*;
import ma.digital.prospace.domain.enumeration.Statut;
import ma.digital.prospace.domain.enumeration.typeidentifiant;
import ma.digital.prospace.repository.AssociationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.Authentication;
import ma.digital.prospace.repository.CompteProRepository;

import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.service.mapper.EntrepriseMapper;
import ma.digital.prospace.service.dto.*;
import org.springframework.web.client.RestTemplate;

import javax.management.relation.Role;
import java.util.logging.Level;



/**
 * Service Implementation for managing {@link Entreprise}.
 */
@Service
@Transactional
public class EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseService.class);


    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final ProcurationRepository procurationRepository;
    private final AssociationRepository associationRepository;
    private CompteProRepository CompteProRepository;
    private final RestTemplate restTemplate;

    private EntrepriseWSMJService entrepriseWSMJService;

    public EntrepriseService(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper, CompteProRepository CompteProRepository, RestTemplate restTemplate, ProcurationRepository procurationRepository, EntrepriseWSMJService entrepriseWSMJService,AssociationRepository associationRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.CompteProRepository = CompteProRepository;
        this.restTemplate = restTemplate;
        this.procurationRepository = procurationRepository;
        this.entrepriseWSMJService = entrepriseWSMJService;
        this.associationRepository = associationRepository;
    }

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save.
     * @return the persisted entity.
     */
    public EntrepriseRequest save(EntrepriseRequest entrepriseRequest) {
        log.debug("Request to save Entreprise : {}", entrepriseRequest);
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseRequest);
        entreprise = entrepriseRepository.save(entreprise);
        return entrepriseMapper.toDto(entreprise);
    }

    /**
     * Update a entreprise.
     *
     * @param entreprise the entity to save.
     * @return the persisted entity.
     */
    public EntrepriseRequest update(EntrepriseRequest entrepriseRequest) {
        log.debug("Request to update Entreprise : {}", entrepriseRequest);
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseRequest);
        entreprise = entrepriseRepository.save(entreprise);
        return entrepriseMapper.toDto(entreprise);
    }

    /**
     * Partially update a entreprise.
     *
     * @param entreprise the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntrepriseRequest> partialUpdate(EntrepriseRequest entreprise) {
        log.debug("Request to partially update Entreprise : {}", entreprise);

        return entrepriseRepository
                .findById(entreprise.getId())
                .map(existingEntreprise -> {
                    entrepriseMapper.partialUpdate(existingEntreprise, entreprise);

                    return existingEntreprise;
                })
                .map(entrepriseRepository::save)
                .map(entrepriseMapper::toDto);
    }

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntrepriseRequest> findAll(Pageable pageable) {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll(pageable).map(entrepriseMapper::toDto);
    }

    /**
     * Get one entreprise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntrepriseRequest> findOne(Long id) {
        log.debug("Request to get Entreprise : {}", id);
        return entrepriseRepository.findById(id).map(entrepriseMapper::toDto);
    }

    /**
     * Delete the entreprise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
    }

    private Optional<Long> getCurrentUserId() {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            //  implémenter la logique pour récupérer l'ID de l'utilisateur à partir de son login
            Long userId = retrieveUserIdFromLogin(currentUserLogin.get());
            return Optional.of(userId);
        } else {
            return Optional.empty();
        }
    }

    private Long retrieveUserIdFromLogin(String login) {
        switch (login) {
            case "john_doe":
                return 1001L;
            case "jane_smith":
                return 1002L;
            case "bob_jackson":
                return 1003L;
            default:
                return null;
        }
    }

    // Méthode pour vérifier si l'utilisateur actuellement connecté correspond à un ID spécifique
    private boolean isCurrentUser(Long accountId) {
        Optional<Long> currentUserId = getCurrentUserId();
        return currentUserId.isPresent() && currentUserId.get().equals(accountId);
    }

    private boolean checkCriteriaMatch(EntrepriseWSMJ entrepriseWS, EntrepriseRequest entrepriseRequest) {
        return entrepriseWS.getPersonneRc().getIdentification().getNumRC().equals(entrepriseRequest.getNumeroRC())
                ;

    }



    private boolean checkManager(ComptePro comptePro, EntrepriseWSMJ entreprise, Long CompID)
    {
        List<DirigeantPMDTO> dirigeantsPM = entreprise.getPersonneRc().getDirigeantsPM();
        for (DirigeantPMDTO dirigeant : dirigeantsPM) {
            List<RepresentantDTO> representants = dirigeant.getRepresentants();
            for (RepresentantDTO representant : representants) {
                String nump = representant.getNumPiece();
                String TypePiece = representant.getTypePiece();
                String typePieceString = TypePiece.toUpperCase();
                String typePieceParsed = typePieceString.replace(" ", "");
                typeidentifiant parsedEnum = typeidentifiant.valueOf(typePieceParsed);
                if (comptePro.getIdentifiant().equals(nump) && comptePro.getTypeidentifiant().equals(parsedEnum)) {
                    return true;

                }
            }
        }
        return false; // No match found
    }
    private boolean checkDirigeantsWS(EntrepriseWSMJ entrepriseWSMJ, DIRIGEANTDTO dirigeantdto, Long accountid) {
        try {
            ComptePro compte = CompteProRepository.getById(accountid);
            if (entrepriseWSMJ.getPersonneRc().getIdentification().getNumRC().equals(dirigeantdto.getPersonneRc().getIdentification().getNumRC())) {
                List<DirigeantPMDTO2> dirigeantsPM = dirigeantdto.getPersonneRc().getDirigeantsPM();
                for (DirigeantPMDTO2 dirigeant : dirigeantsPM) {
                    List<RepresentantDTO> representants = dirigeant.getRepresentant();
                    for (RepresentantDTO representant : representants) {
                        String nump = representant.getNumPiece();
                        String TypePiece = representant.getTypePiece();
                        String typePieceString = TypePiece.toUpperCase();
                        String typePieceParsed = typePieceString.replace(" ", "");
                        typeidentifiant parsedEnum = typeidentifiant.valueOf(typePieceParsed);
                        if (compte.getIdentifiant().equals(nump) && compte.getTypeidentifiant().equals(parsedEnum)) {
                            return true;
                        }
                    }
                }
                return false; // No match found
            }
        } catch (Exception e) {
            log.info("Une exception s'est produite lors de la vérification des dirigeants WS.", e);
        }
        return false;
    }

    public void createCompany(EntrepriseRequest2 entrepriseRequest) {
        if (entrepriseRequest.getPerphysique_Permorale() == Statut.MORAL_PERSON) {
            EntrepriseWSMJ entrepriseWS = null;
            try {
                entrepriseWS = entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
            } catch (NullPointerException e) {
                // Handle the case where entrepriseWS is null
                // Log the error or take appropriate action
                log.error("EntrepriseWS is null: " + e.getMessage());
            }            // !!Attention : --> Pour le testing ,on est obligé de commenter tout ce qui en relation avec la sécurité et trvailler statiquement .
        // Pour que le test je suppose que User connecté est TARIK
            Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
            ComptePro compte = CompteProRepository.getById(entrepriseRequest.getCOMPID());
           // if (currentUserLogin.isPresent() ) {
                //String currentUsername = currentUserLogin.get();
                String currentUsername = "TARIK";
                    if (compte.getPrenomFr().equals(currentUsername)) {
                        boolean isManager = checkManager(compte, entrepriseWS, entrepriseRequest.getCOMPID());
                        if (isManager) {
                            Entreprise newEntreprise = new Entreprise();
                            newEntreprise.setNumeroRC(entrepriseRequest.getNumeroRC());
                            newEntreprise.setTribunal(entrepriseRequest.getTribunal());
                            newEntreprise.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                            newEntreprise.setIce(entrepriseRequest.getIce());
                            try {
                                //creation d'entreprise et enregistrement
                                entrepriseRepository.save(newEntreprise);
                                ///Association compte avec entreprise
                                Set<ComptePro> gerants = new HashSet<>();
                                gerants.add(compte);
                                newEntreprise.setGerants(gerants);
                                compte.setEntrepriseGeree(newEntreprise);
                                CompteProRepository.save(compte);
                                log.info("Enregistrement réussi");
                            } catch (Exception e) {
                                log.error("Erreur lors de l'enregistrement de l'entreprise : " + e.getMessage());
                            }
                        } else {
                            log.info("Vous n'êtes pas le manager");
                        }
                    } else {
                        log.info("Vous n'êtes pas le manager");
                    }
                } else {
                    log.info("Vous n'avez pas l'autorisation de création d'entreprise");
                }
           // } else {
           //     log.info("L'ID du compte ne correspond pas à l'utilisateur connecté");
           // }
         {
           // Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
           // String currentUsername = currentUserLogin.get();
             String currentUsername = "TARIK";
             ComptePro accountconnected = CompteProRepository.findByAndPrenomFr(currentUsername);
             //Long acountconnectedid = accountconnected.getId();
             Long acountconnectedid = 2L;
             ComptePro compte = CompteProRepository.getById(entrepriseRequest.getCOMPID());
             EntrepriseWSMJ entrepriseWS = entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
             EntrepriseWSMJService dirigeantService = new EntrepriseWSMJService(restTemplate);
             DIRIGEANTDTO dirigeants = dirigeantService.getDirigeantBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
             if (procurationRepository.checkProcurationForCompteAndGestionnaire(entrepriseRequest.getCOMPID(),acountconnectedid) && checkDirigeantsWS(entrepriseWS,dirigeants,entrepriseRequest.getCOMPID())) {
                Entreprise newEntreprise = new Entreprise();
                newEntreprise.setDenomination(entrepriseRequest.getNumeroRC());
                newEntreprise.setTribunal(entrepriseRequest.getTribunal());
                newEntreprise.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                if (compte != null) {
                    Set<ComptePro> gerants = new HashSet<>();
                    gerants.add(compte);
                    newEntreprise.setGerants(gerants);
                    entrepriseRepository.save(newEntreprise);
                }
            } else {
                log.info("Le compte n'existe pas");
            }
        }
    }

}
















