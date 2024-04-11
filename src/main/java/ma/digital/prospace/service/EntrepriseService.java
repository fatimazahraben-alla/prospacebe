package ma.digital.prospace.service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.enumeration.typeidentifiant;
import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.service.mapper.EntrepriseMapper;
import ma.digital.prospace.service.dto.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


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

    @Autowired
    private JwtDecoder jwtDecoder;

    private final UserService userService;
    @Autowired
    private EntrepriseWSMJService entrepriseWSMJService;

    public EntrepriseService(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper, CompteProRepository CompteProRepository, ProcurationRepository procurationRepository, EntrepriseWSMJService entrepriseWSMJService, AssociationRepository associationRepository, UserService userService) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.CompteProRepository = CompteProRepository;
        this.procurationRepository = procurationRepository;
        this.entrepriseWSMJService = entrepriseWSMJService;
        this.associationRepository = associationRepository;
        this.userService = userService;
    }

    /**
     * Save a entreprise.
     *
     * @param entreprise the entity to save.
     * @return the persisted entity.
     */
    public EntrepriseDTO save(EntrepriseDTO entrepriseDTO) {
        log.debug("Request to save Entreprise : {}", entrepriseDTO);
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDTO);
        entreprise = entrepriseRepository.save(entreprise);
        return entrepriseMapper.toDto(entreprise);
    }

    /**
     * Update a entreprise.
     *
     * @param entreprise the entity to save.
     * @return the persisted entity.
     */
    public EntrepriseDTO update(EntrepriseDTO entrepriseDTO) {
        log.debug("Request to update Entreprise : {}", entrepriseDTO);
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDTO);
        entreprise = entrepriseRepository.save(entreprise);
        return entrepriseMapper.toDto(entreprise);
    }

    /**
     * Partially update a entreprise.
     *
     * @param entreprise the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntrepriseDTO> partialUpdate(EntrepriseDTO entreprise) {
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
    public Page<EntrepriseDTO> findAll(Pageable pageable) {
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
    public Optional<EntrepriseDTO> findOne(Long id) {
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


    // Méthode pour vérifier si l'utilisateur actuellement connecté correspond à un ID spécifique
    public boolean isCurrentUser(String accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Impossible de récupérer l'objet d'authentification à partir du contexte de sécurité.");
        }

        // Vérifie si l'objet Principal est un Jwt
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            // Récupère l'identifiant utilisateur (sub) depuis le token JWT
            String userId = jwt.getSubject();
            // Compare l'identifiant utilisateur avec l'accountId fourni
            return userId.equals(accountId);
        }
        // Vérifie si l'objet Principal est un OidcUser
        else if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            // Récupère l'identifiant utilisateur (sub) depuis le token OIDC
            String userId = oidcUser.getSubject();
            // Compare l'identifiant utilisateur avec l'accountId fourni
            return userId.equals(accountId);
        } else {
            throw new IllegalStateException("Le type de l'objet Principal n'est ni Jwt ni OidcUser.");
        }
    }

    private String UserId(String accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Impossible de récupérer l'objet d'authentification à partir du contexte de sécurité.");
        }

        // Vérifie si l'objet Principal est un Jwt
        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getSubject();
            return userId;
        }
        // Vérifie si l'objet Principal est un OidcUser
        else if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String userId = oidcUser.getSubject();
            return userId;
        } else {
            throw new IllegalStateException("Le type de l'objet Principal n'est ni Jwt ni OidcUser.");
        }
    }

    public boolean isUserIdMatchingAccount(String accountId, AbstractAuthenticationToken authToken) {
        AdminUserDTO userDTO = userService.getUserFromAuthentication(authToken);
        String userId = userDTO.getId();
        return userId.equals(accountId);
    }


    private boolean checkCriteriaMatch(EntrepriseWSMJ entrepriseWS, EntrepriseDTO11 entrepriseRequest) {
        return entrepriseWS.getPersonneRc().getIdentification().getNumRC().equals(entrepriseRequest.getNumRC())
                ;

    }


    private boolean checkManager(ComptePro comptePro, EntrepriseWSMJ entreprise, UUID CompID) {
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

    private boolean checkManagerPp(ComptePro comptePro, PersonnephysiqueDTO entreprise) {
        if (entreprise != null && entreprise.getPersonneRc() != null && entreprise.getPersonneRc().getCommercant() != null) {
            CommercantDto commercant = entreprise.getPersonneRc().getCommercant();
            String nump = commercant.getNumPiece();
            String TypePiece = commercant.getTypePiece();
            String typePieceString = TypePiece.toUpperCase();
            String typePieceParsed = typePieceString.replace(" ", "");
            try {
                typeidentifiant parsedEnum = typeidentifiant.valueOf(typePieceParsed);
                if (comptePro.getIdentifiant().equals(nump) && comptePro.getTypeidentifiant().equals(parsedEnum)) {
                    return true;
                }
            } catch (IllegalArgumentException e) {
                // Gérer le cas où le type de pièce n'est pas une valeur valide de l'énumération
                log.info("Type de pièce d'identité non valide : " + typePieceParsed);
            }
        }
        return false; // Aucune correspondance trouvée
    }


    private boolean checkDirigeantsWS(EntrepriseWSMJ entrepriseWSMJ, DIRIGEANTDTO dirigeantdto, UUID accountid) {
        try {
            Optional<ComptePro> compteOptional = CompteProRepository.findByCustomIdQuery(accountid);
            ComptePro compte = compteOptional.orElse(null);
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

    private boolean checkPp(PersonnephysiqueDTO personnephysiqueDTO, UUID accountid) {
        Optional<ComptePro> compteOptional = CompteProRepository.findByCustomIdQuery(accountid);
        ComptePro compte = compteOptional.orElse(null);
        if (personnephysiqueDTO.getPersonneRc().getCommercant().getNumPiece().equals(compte.getIdentifiant())) {
            return true;
        } else {
            return false; // No match found
        }

    }


    public void createCompany(EntrepriseRequest2 entrepriseRequest, AbstractAuthenticationToken authToken) {
        switch (entrepriseRequest.getPerphysique_Permorale()) {
            case PHYSICAL_PERSON:
                handlePhysicalPerson(entrepriseRequest, authToken);
                break;
            case MORAL_PERSON:
                handleMoralPerson(entrepriseRequest);
                break;
            default:
                log.info("Statut non reconnu");
                break;
        }
    }

    private void handlePhysicalPerson(EntrepriseRequest2 entrepriseRequest, AbstractAuthenticationToken authToken) {
        PersonnephysiqueDTO personnephysiqueDTO = entrepriseWSMJService.getBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
        if (personnephysiqueDTO != null) {
            Optional<ComptePro> compteOptional = CompteProRepository.findByCustomIdQuery(entrepriseRequest.getCOMPID());
            ComptePro compte = compteOptional.orElse(null);
            UUID compIdUUID = entrepriseRequest.getCOMPID();
            String compIdString = compIdUUID.toString();
            if (isCurrentUser(compIdString)) {
                if (checkManagerPp(compte, personnephysiqueDTO)) {
                    Entreprise newEntreprise = new Entreprise();
                    newEntreprise.setNumeroRC(entrepriseRequest.getNumeroRC());
                    newEntreprise.setTribunal(entrepriseRequest.getTribunal());
                    newEntreprise.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                    newEntreprise.setIce(entrepriseRequest.getIce());
                    try {
                        entrepriseRepository.save(newEntreprise);
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
                String accountConnected = UserId(compIdString);
                UUID accountConnectedId = UUID.fromString(accountConnected);
                if (procurationRepository.checkProcurationForCompteAndGestionnaire(entrepriseRequest.getCOMPID(), accountConnectedId) && checkPp(personnephysiqueDTO, entrepriseRequest.getCOMPID())) {
                    Entreprise newEntreprise2 = new Entreprise();
                    newEntreprise2.setNumeroRC(entrepriseRequest.getNumeroRC());
                    newEntreprise2.setTribunal(entrepriseRequest.getTribunal());
                    newEntreprise2.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                    newEntreprise2.setIce(entrepriseRequest.getIce());
                    try {
                        entrepriseRepository.save(newEntreprise2);
                        Set<ComptePro> gerants = new HashSet<>();
                        gerants.add(compte);
                        newEntreprise2.setGerants(gerants);
                        compte.setEntrepriseGeree(newEntreprise2);
                        CompteProRepository.save(compte);
                        log.info("Enregistrement réussi");
                    } catch (Exception e) {
                        log.error("Erreur lors de l'enregistrement de l'entreprise : " + e.getMessage());
                    }
                } else {
                    log.info("");
                }

            }
        } else {
            log.info("PersonnephysiqueDTO est vide. Impossible de continuer le traitement.");
        }
    }

    private void handleMoralPerson(EntrepriseRequest2 entrepriseRequest) {
        EntrepriseWSMJ entrepriseWS = entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
        Optional<ComptePro> compteOptional = CompteProRepository.findByCustomIdQuery(entrepriseRequest.getCOMPID());
        ComptePro compte = compteOptional.orElse(null);
        UUID compIdUUID = entrepriseRequest.getCOMPID();
        String compIdString = compIdUUID.toString();
        if (isCurrentUser(compIdString)) {
            if (checkManager(compte, entrepriseWS, entrepriseRequest.getCOMPID())) {
                Entreprise newEntreprise = new Entreprise();
                newEntreprise.setNumeroRC(entrepriseRequest.getNumeroRC());
                newEntreprise.setTribunal(entrepriseRequest.getTribunal());
                newEntreprise.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                newEntreprise.setIce(entrepriseRequest.getIce());
                try {
                    entrepriseRepository.save(newEntreprise);
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
            String accountConnected = UserId(compIdString);
            UUID accountConnectedId = UUID.fromString(accountConnected);
            DIRIGEANTDTO  dirigeants = entrepriseWSMJService.getDirigeantBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
            if (procurationRepository.checkProcurationForCompteAndGestionnaire(entrepriseRequest.getCOMPID(), accountConnectedId) && checkDirigeantsWS(entrepriseWS, dirigeants, entrepriseRequest.getCOMPID())) {
                Entreprise newEntreprise2 = new Entreprise();
                newEntreprise2.setNumeroRC(entrepriseRequest.getNumeroRC());
                newEntreprise2.setTribunal(entrepriseRequest.getTribunal());
                newEntreprise2.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                newEntreprise2.setIce(entrepriseRequest.getIce());
                try {
                    entrepriseRepository.save(newEntreprise2);
                    Set<ComptePro> gerants = new HashSet<>();
                    gerants.add(compte);
                    newEntreprise2.setGerants(gerants);
                    compte.setEntrepriseGeree(newEntreprise2);
                    CompteProRepository.save(compte);
                    log.info("Enregistrement réussi");
                } catch (Exception e) {
                    log.error("Erreur lors de l'enregistrement de l'entreprise : " + e.getMessage());
                }

            } else {
                log.info("condition non vérifié");
            }
        }

    }
}





