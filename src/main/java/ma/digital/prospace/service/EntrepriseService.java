package ma.digital.prospace.service;
import jakarta.servlet.http.HttpServletRequest;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import ma.digital.prospace.web.rest.errors.EntrepriseBadRequestException;
import ma.digital.prospace.web.rest.errors.EntrepriseCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private static final Logger auditLogger1 = LoggerFactory.getLogger("ma.digital.prospace.audit");

    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseMapper entrepriseMapper;

    private final TribunalWSMJService tribunalWSMJService;
    private final ProcurationRepository procurationRepository;
    private final AssociationRepository associationRepository;
    private CompteProRepository CompteProRepository;

    //@Autowired
    //private JwtDecoder jwtDecoder;

    private final UserService userService;
    @Autowired
    private EntrepriseWSMJService entrepriseWSMJService;

    public EntrepriseService(TribunalWSMJService tribunalWSMJService,EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper, CompteProRepository CompteProRepository, ProcurationRepository procurationRepository, EntrepriseWSMJService entrepriseWSMJService, AssociationRepository associationRepository, UserService userService) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.CompteProRepository = CompteProRepository;
        this.procurationRepository = procurationRepository;
        this.entrepriseWSMJService = entrepriseWSMJService;
        this.associationRepository = associationRepository;
        this.userService = userService;
        this.tribunalWSMJService =tribunalWSMJService;
    }

    /**
     * Save an entreprise.
     *
     * @param entrepriseDTO the entity to save.
     * @return the persisted entity DTO.
     */
    public EntrepriseDTO save(EntrepriseDTO entrepriseDTO) {
        log.debug("Request to save Entreprise: {}", entrepriseDTO);
        // Convert DTO to entity
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDTO);
        // Save entity to the database
        entreprise = entrepriseRepository.save(entreprise);
        // Audit log to record the saving action
        auditLogger1.info("Audit: Saved entreprise with ID {}", entreprise.getId());
        // Convert entity back to DTO
        return entrepriseMapper.toDto(entreprise);
    }

    /**
     * Update an entreprise.
     *
     * @param entrepriseDTO the entity to save.
     * @return the persisted entity DTO.
     */
    public EntrepriseDTO update(EntrepriseDTO entrepriseDTO) {
        log.debug("Request to update Entreprise : {}", entrepriseDTO);
        auditLogger1.info("Début de la mise à jour de l'entreprise avec ID {}", entrepriseDTO.getId());

        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDTO);

        try {
            entreprise = entrepriseRepository.save(entreprise);
            log.debug("Entreprise mise à jour avec succès : {}", entreprise);
            auditLogger1.info("Mise à jour réussie de l'entreprise avec ID {}", entrepriseDTO.getId());
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'entreprise : {}", e.getMessage());
            auditLogger1.error("Échec de la mise à jour de l'entreprise avec ID {}. Erreur : {}", entrepriseDTO.getId(), e.getMessage());
            throw new RuntimeException("La mise à jour de l'entreprise a échoué", e);
        }

        return entrepriseMapper.toDto(entreprise);
    }

    /**
     * Partially an entreprise.
     *
     * @param entrepriseDTO the entity to save.
     * @return the persisted entity DTO.
     */

    public Optional<EntrepriseDTO> partialUpdate(EntrepriseDTO entrepriseDTO) {
        log.debug("Request to partially update Entreprise : {}", entrepriseDTO);
        auditLogger1.info("Tentative de mise à jour partielle de l'entreprise avec ID {}", entrepriseDTO.getId());

        return entrepriseRepository.findById(entrepriseDTO.getId())
                .map(existingEntreprise -> {
                    auditLogger1.info("Entreprise trouvée pour mise à jour partielle : ID {}", existingEntreprise.getId());
                    entrepriseMapper.partialUpdate(existingEntreprise, entrepriseDTO);

                    try {
                        Entreprise updatedEntreprise = entrepriseRepository.save(existingEntreprise);
                        auditLogger1.info("Mise à jour partielle réussie de l'entreprise ID {}", updatedEntreprise.getId());
                        return entrepriseMapper.toDto(updatedEntreprise);
                    } catch (Exception e) {
                        log.error("Erreur lors de la mise à jour partielle de l'entreprise : {}", e.getMessage());
                        auditLogger1.error("Échec de la mise à jour partielle de l'entreprise ID {}. Erreur : {}", existingEntreprise.getId(), e.getMessage());
                        throw new RuntimeException("La mise à jour partielle de l'entreprise a échoué", e);
                    }
                });
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
    public Optional<EntrepriseDTO> findOne(UUID id) {
        log.debug("Request to get Entreprise : {}", id);
        return entrepriseRepository.findById(id).map(entrepriseMapper::toDto);
    }

    /**
     * Delete the entreprise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
    }


    // Méthode pour vérifier si l'utilisateur actuellement connecté correspond à un ID spécifique
    private boolean isCurrentUser(String accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            auditLogger1.warn("Aucun objet d'authentification trouvé dans le contexte de sécurité.");
            throw new IllegalStateException("Impossible de récupérer l'objet d'authentification à partir du contexte de sécurité.");
        }

        auditLogger1.debug("Vérification du type de l'objet Principal pour l'authentification.");

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getSubject();
            auditLogger1.info("Comparaison de l'identifiant utilisateur JWT avec accountId fourni.");
            return userId.equals(accountId);
        } else if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String userId = oidcUser.getSubject();
            auditLogger1.info("Comparaison de l'identifiant utilisateur OIDC avec accountId fourni.");
            return userId.equals(accountId);
        } else {
            auditLogger1.error("Le type de l'objet Principal n'est ni Jwt ni OidcUser.");
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


    private boolean checkManager(EntrepriseRequest2 entrepriseRequest2, EntrepriseWSMJ entreprise, String CompID) {
        List<DirigeantPMDTO> dirigeantsPM = entreprise.getPersonneRc().getDirigeantsPM();
        for (DirigeantPMDTO dirigeant : dirigeantsPM) {
            List<RepresentantDTO> representants = dirigeant.getRepresentants();
            for (RepresentantDTO representant : representants) {
                String nump = representant.getNumPiece();
                String TypePiece = representant.getTypePiece();
                String typePieceString = TypePiece.toUpperCase();
                String typePieceParsed = typePieceString.replace(" ", "");
                typeidentifiant parsedEnum = typeidentifiant.valueOf(typePieceParsed);
                if (entrepriseRequest2.getCIN().equals(nump) && entrepriseRequest2.getIndentifianttype().equals(parsedEnum)) {
                    return true;

                }
            }
        }
        return false; // No match found
    }

    private boolean checkManagerPp(EntrepriseRequest2 entrepriseRequest2, PersonnephysiqueDTO entreprise) {
        if (entreprise != null && entreprise.getPersonneRc() != null && entreprise.getPersonneRc().getCommercant() != null) {
            CommercantDto commercant = entreprise.getPersonneRc().getCommercant();
            String nump = commercant.getNumPiece();
            String TypePiece = commercant.getTypePiece();
            String typePieceString = TypePiece.toUpperCase();
            String typePieceParsed = typePieceString.replace(" ", "");
            try {
                typeidentifiant parsedEnum = typeidentifiant.valueOf(typePieceParsed);
                if (entrepriseRequest2.getCIN().equals(nump) && entrepriseRequest2.getIndentifianttype().equals(parsedEnum)) {
                    return true;
                }
            } catch (IllegalArgumentException e) {
                // Gérer le cas où le type de pièce n'est pas une valeur valide de l'énumération
                log.info("Type de pièce d'identité non valide : " + typePieceParsed);
                auditLogger1.info("Type de pièce d'identité non valide : " + typePieceParsed);
            }
        }
        auditLogger1.info("Aucune correspondance trouvée");
        return false; // Aucune correspondance trouvée

    }


    private boolean checkDirigeantsWS(EntrepriseRequest2 entrepriseRequest2,EntrepriseWSMJ entrepriseWSMJ, DIRIGEANTDTO dirigeantdto) {
        try {
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
                        if (entrepriseRequest2.getCIN().equals(nump) && entrepriseRequest2.getIndentifianttype().equals(parsedEnum)) {
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

    private boolean checkPp(PersonnephysiqueDTO personnephysiqueDTO, String accountid) {
        Optional<ComptePro> compteOptional = CompteProRepository.findByCustomIdQuery(accountid);
        ComptePro compte = compteOptional.orElse(null);
        if (personnephysiqueDTO.getPersonneRc().getCommercant().getNumPiece().equals(compte.getIdentifiant())) {
            return true;
        } else {
            return false; // No match found
        }

    }

    public Entreprise createCompany(EntrepriseRequest2 entrepriseRequest) throws BadRequestAlertException {
        switch (entrepriseRequest.getPerphysique_Permorale()) {
            case PHYSICAL_PERSON:
                return handlePhysicalPerson(entrepriseRequest);
            case MORAL_PERSON:
                return handleMoralPerson(entrepriseRequest);
            default:
                auditLogger1.warn("Type de personne non reconnu: " + entrepriseRequest.getPerphysique_Permorale());
                throw new BadRequestAlertException("Type de personne non reconnu: ","Entreprise","perphysique_Permoraleerror");

        }
    }


    private Entreprise handlePhysicalPerson(EntrepriseRequest2 entrepriseRequest) {
        PersonnephysiqueDTO personnephysiqueDTO = entrepriseWSMJService.getBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
        List<Juridiction> juridictions = tribunalWSMJService.getListeTribunaux();
        if ((personnephysiqueDTO != null) && (checktribunal(juridictions,entrepriseRequest))) {
            Optional<ComptePro> compteOptional = CompteProRepository.findByCustomIdQuery(entrepriseRequest.getCOMPID());
            ComptePro compte = compteOptional.orElse(null);
            String compIdUUID = entrepriseRequest.getCOMPID();
            String compIdString = compIdUUID.toString();
            boolean isCurrentUser = true;
            if (isCurrentUser) {
                if (checkManagerPp(entrepriseRequest, personnephysiqueDTO)) {
                    Entreprise newEntreprise = new Entreprise();
                    newEntreprise.setEtat(personnephysiqueDTO.getPersonneRc().getEtat());
                    newEntreprise.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());

                    try {
                        entrepriseRepository.save(newEntreprise);
                        Set<ComptePro> gerants = new HashSet<>();
                        gerants.add(compte);
                        newEntreprise.setGerants(gerants);
                        compte.setEntrepriseGeree(newEntreprise);
                        CompteProRepository.save(compte);
                        log.info("Enregistrement réussi de l'entreprise.");
                        auditLogger1.info("Nouvelle entreprise créée avec succès par {} pour le tribunal {} et le numéro RC {}", compIdString, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                        auditLogger1.info("Successfully created company with NumRC: {}", entrepriseRequest.getNumeroRC());
                        return newEntreprise;
                    } catch (Exception e) {
                        log.error("Erreur lors de l'enregistrement de l'entreprise : " + e.getMessage());
                        auditLogger1.error("Échec de la création de l'entreprise par {} pour le tribunal {} et le numéro RC {}", compIdString, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC(), e);
                    }
                } else {
                    log.info("Vous n'êtes pas le manager.");
                    auditLogger1.warn("Tentative de création d'entreprise échouée pour {} - non manager.", compIdString);
                }

            } else {
                String accountConnectedId = UserId(compIdString);
                Procuration procuration = procurationRepository.findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(entrepriseRequest.getCOMPID(), accountConnectedId);
                if ( procuration.getStatut() == StatutInvitation.ACCEPTED  && checkPp(personnephysiqueDTO, entrepriseRequest.getCOMPID())) {
                    Entreprise newEntreprise2 = new Entreprise();
                    newEntreprise2.setEtat(personnephysiqueDTO.getPersonneRc().getEtat());
                    newEntreprise2.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());

                    try {
                        entrepriseRepository.save(newEntreprise2);
                        Set<ComptePro> gerants = new HashSet<>();
                        gerants.add(compte);
                        newEntreprise2.setGerants(gerants);
                        compte.setEntrepriseGeree(newEntreprise2);
                        CompteProRepository.save(compte);
                        log.info("Enregistrement réussi de l'entreprise.");
                        auditLogger1.info("Nouvelle entreprise créée avec succès via procuration par {} pour le tribunal {} et le numéro RC {}", accountConnectedId, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                        auditLogger1.info("Successfully created company with NumRC: {}", entrepriseRequest.getNumeroRC());
                        return newEntreprise2;
                    } catch (Exception e) {
                        log.error("Erreur lors de l'enregistrement de l'entreprise : " + e.getMessage());
                        auditLogger1.error("Échec de la création de l'entreprise via procuration par {} pour le tribunal {} et le numéro RC {}", accountConnectedId, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC(), e);
                    }
                } else {
                    log.info("Procuration non valide ou conditions de création non remplies.");
                    auditLogger1.warn("Tentative de création d'entreprise échouée via procuration pour {} - conditions non remplies.", accountConnectedId);
                    throw new BadRequestAlertException(
                            "Les conditions pour l'autorisation par procuration ne sont pas remplies.", // defaultMessage
                            "procuration", // entityName
                            "autorisationNonAccordee" // errorKey
                    );
                }
            }
        } else {
            log.info("PersonnephysiqueDTO est vide. Impossible de continuer le traitement.");
            auditLogger1.warn("Échec de la création de l'entreprise : aucune entreprise trouvée avec le tribunal {} et le numéro RC {}", entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
            throw new BadRequestAlertException(
                    "Échec de la création de l'entreprise : aucune entreprise trouvée avec le tribunal {} et le numéro RC {}", // defaultMessage
                    "entrepirseWS", // entityName
                    "tribunal ou NRC incorrectes" // errorKey
            );

        }
        return null;

    }

    private boolean checktribunal( List<Juridiction> juridictions,EntrepriseRequest2 entrepriseRequest2) {
        for (Juridiction tribunal : juridictions) {
            if (tribunal.getCode().equals(entrepriseRequest2.getTribunal())) {
                return true;
            }
        }
        return false;
    }

    private Entreprise handleMoralPerson(EntrepriseRequest2 entrepriseRequest) throws BadRequestAlertException {
        EntrepriseWSMJ entrepriseWS = entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
        List<Juridiction> juridictions = tribunalWSMJService.getListeTribunaux();
        if ((!checktribunal(juridictions,entrepriseRequest)) && (entrepriseWS!=null)) {
            auditLogger1.warn("Échec de la création de l'entreprise : aucune entreprise trouvée avec le tribunal {} et le numéro RC {}", entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
            return null;
        }
        else {
            Optional<ComptePro> compteOptional = CompteProRepository.findByCustomIdQuery(entrepriseRequest.getCOMPID());
            ComptePro compte = compteOptional.orElse(null);
            String compIdUUID = entrepriseRequest.getCOMPID();
            String compIdString = compIdUUID.toString();
            boolean isCurrentUser = true;
            if (isCurrentUser) {
                if (checkManager(entrepriseRequest, entrepriseWS, entrepriseRequest.getCOMPID())) {
                    Entreprise newEntreprise2 = new Entreprise();

                    newEntreprise2.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                    newEntreprise2.setEtat(entrepriseWS.getPersonneRc().getIdentification().getEtat());
                    if (newEntreprise2!=null){
                        entrepriseRepository.save(newEntreprise2);
                        Set<ComptePro> gerants = new HashSet<>();
                        gerants.add(compte);
                        newEntreprise2.setGerants(gerants);
                        compte.setEntrepriseGeree(newEntreprise2);
                        CompteProRepository.save(compte);
                        auditLogger1.info("Nouvelle entreprise morale créée avec succès par {} pour le tribunal {} et le numéro RC {}", compIdString, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                        log.info("Enregistrement réussi de l'entreprise morale.");
                        return newEntreprise2;

                    } else{
                        log.error("Erreur lors de l'enregistrement de l'entreprise : "+ newEntreprise2);
                        auditLogger1.error("Échec de la création de l'entreprise morale par {} pour le tribunal {} et le numéro RC {}", compIdString, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC(), "");
                        return null;
                    }
                } else {
                    auditLogger1.info("Vous n'êtes pas le manager.");
                    auditLogger1.warn("Tentative de création d'entreprise morale échouée pour {} - non manager.", compIdString);
                    throw new BadRequestAlertException("Tentative de création d'entreprise morale échouée pour {} - non manager.","Entreprise","autorisationNonAccordee");
                }
            } else {
                String accountConnectedId = UserId(compIdString);
                DIRIGEANTDTO dirigeants = entrepriseWSMJService.getDirigeantBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                Procuration procuration = procurationRepository.findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(entrepriseRequest.getCOMPID(), accountConnectedId);
                if ( procuration.getStatut() == StatutInvitation.ACCEPTED && checkDirigeantsWS(entrepriseRequest,entrepriseWS, dirigeants)) {
                    Entreprise newEntreprise2 = new Entreprise();
                    newEntreprise2.setStatus_Perphysique_Permorale(entrepriseRequest.getPerphysique_Permorale());
                    newEntreprise2.setEtat(entrepriseWS.getPersonneRc().getIdentification().getEtat());
                    try {
                        entrepriseRepository.save(newEntreprise2);
                        Set<ComptePro> gerants = new HashSet<>();
                        gerants.add(compte);
                        newEntreprise2.setGerants(gerants);
                        compte.setEntrepriseGeree(newEntreprise2);
                        CompteProRepository.save(compte);
                        log.info("Enregistrement réussi de l'entreprise morale via procuration.");
                        auditLogger1.info("Nouvelle entreprise morale créée avec succès via procuration par {} pour le tribunal {} et le numéro RC {}", accountConnectedId, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                        return newEntreprise2;
                    } catch (Exception e) {
                        log.error("Erreur lors de l'enregistrement de l'entreprise : " + e.getMessage());
                        auditLogger1.error("Échec de la création de l'entreprise morale via procuration par {} pour le tribunal {} et le numéro RC {}", accountConnectedId, entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC(), e);
                        return newEntreprise2;
                    }
                } else {
                    log.info("Condition non vérifiée.");
                    auditLogger1.warn("Tentative de création d'entreprise morale échouée via procuration pour {} - conditions non remplies.", accountConnectedId);
                    throw new BadRequestAlertException(
                            "Les conditions pour l'autorisation par procuration ne sont pas remplies.", // defaultMessage
                            "procuration", // entityName
                            "autorisationNonAccordee" // errorKey
                    );
                }
            }
        }
    }
}








