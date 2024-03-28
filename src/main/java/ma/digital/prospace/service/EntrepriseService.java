package ma.digital.prospace.service;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.Authentication;
import ma.digital.prospace.domain.ComptePro;
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

import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.service.mapper.EntrepriseMapper;
import ma.digital.prospace.service.dto.*;
import org.springframework.web.client.RestTemplate;


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


    private CompteProRepository CompteProRepository;
    private final RestTemplate restTemplate;

    public EntrepriseService(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper, CompteProRepository CompteProRepository, RestTemplate restTemplate, ProcurationRepository procurationRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.CompteProRepository = CompteProRepository;
        this.restTemplate = restTemplate;
        this.procurationRepository = procurationRepository;
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

    private boolean checkManager(ComptePro comptePro, EntrepriseWSMJ entreprise, Long CompID) {
        List<DirigeantPMDTO> dirigeantsPM = entreprise.getPersonneRc().getDirigeantsPM();
        for (DirigeantPMDTO dirigeant : dirigeantsPM) {
            List<RepresentantDTO> representants = dirigeant.getRepresentants();
            for (RepresentantDTO representant : representants) {
                String typePiece = representant.getTypePiece();
                String nom = representant.getNom();
                String prenom = representant.getPrenom();
                if (comptePro.getIdentifiant().equals(typePiece) &&
                        comptePro.getPrenomFr().equals(prenom) &&
                        comptePro.getNomFr().equals(nom)) {
                    return true;
                }
            }
        }
        return false; // No match found
    }


    public void createCompany(EntrepriseRequest entrepriseRequest) {
        // Trouver l'entreprise, appeler le WS du ministère de la justice
        EntrepriseWSMJService entrepriseWSMJService = new EntrepriseWSMJService(restTemplate);
        EntrepriseWSMJ entrepriseWS = entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());

        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            String currentUsername = currentUserLogin.get();
            ComptePro compte = CompteProRepository.getById(entrepriseRequest.getCOMPID());
            // Récupérer le compte correspondant à l'ID
            Optional<ComptePro> compteProOptional = CompteProRepository.findById(entrepriseRequest.getCOMPID());
            if (compteProOptional.isPresent()) {
                ComptePro comptePro = compteProOptional.get();
                // Vérifier si l'ID du compte correspond à l'utilisateur connecté
                if (comptePro.getIdentifiant().equals(currentUsername)) {
                    boolean isManager = checkManager(compte, entrepriseWS, entrepriseRequest.getCOMPID());
                    if (isManager) {
                        Entreprise newEntreprise = new Entreprise();
                        // Définir les attributs de l'entreprise à partir de entrepriseRequest
                        newEntreprise.setDenomination(entrepriseRequest.getDenomination());
                        newEntreprise.setStatutJuridique(entrepriseRequest.getStatutJuridique());
                        newEntreprise.setTribunal(entrepriseRequest.getTribunal());
                        newEntreprise.setNumeroRC(entrepriseRequest.getNumeroRC());
                        newEntreprise.setIce(entrepriseRequest.getIce());
                        newEntreprise.setActivite(entrepriseRequest.getActivite());
                        newEntreprise.setFormeJuridique(entrepriseRequest.getFormeJuridique());
                        newEntreprise.setDateImmatriculation(entrepriseRequest.getDateImmatriculation());
                        newEntreprise.setEtat(entrepriseRequest.getEtat());
                        if (compte != null) {
                            // Associer l'entreprise avec le compte trouvé
                            Set<ComptePro> gerants = new HashSet<>();
                            gerants.add(comptePro);
                            newEntreprise.setGerants(gerants);
                            entrepriseRepository.save(newEntreprise);
                        } else {
                            log.info("Vous n'êtes pas le manager");
                        }
                    } else {
                        EntrepriseWSMJService dirigeantService = new EntrepriseWSMJService(restTemplate);
                        DIRIGEANTDTO dirigeants = dirigeantService.getDirigeantBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC(), "ADD");
                        boolean check = procurationRepository.checkProcurationForCompteAndGestionnaire(entrepriseRequest.getCOMPID(),currentUsername);
                        if (check) {
                            Entreprise newEntreprise = new Entreprise();
                            newEntreprise.setDenomination(entrepriseRequest.getDenomination());
                            newEntreprise.setStatutJuridique(entrepriseRequest.getStatutJuridique());
                            newEntreprise.setTribunal(entrepriseRequest.getTribunal());
                            newEntreprise.setNumeroRC(entrepriseRequest.getNumeroRC());
                            newEntreprise.setIce(entrepriseRequest.getIce());
                            newEntreprise.setActivite(entrepriseRequest.getActivite());
                            newEntreprise.setFormeJuridique(entrepriseRequest.getFormeJuridique());
                            newEntreprise.setDateImmatriculation(entrepriseRequest.getDateImmatriculation());
                            newEntreprise.setEtat(entrepriseRequest.getEtat());
                            if (compte != null) {
                                Set<ComptePro> gerants = new HashSet<>();
                                gerants.add(comptePro);
                                newEntreprise.setGerants(gerants);
                                entrepriseRepository.save(newEntreprise);
                            } else {
                                log.info("Vous n'êtes pas le manager");
                            }
                        } else {
                            log.info("Vous n'avez pas l'autorisation de création d'entreprise");
                        }
                    }
                } else {
                    log.info("L'ID du compte ne correspond pas à l'utilisateur connecté");
                }
            } else {
                log.info("Le compte n'existe pas");
            }
        } else {
            log.info("Aucun utilisateur n'est connecté");
        }
    }

}
















