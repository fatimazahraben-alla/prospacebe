package ma.digital.prospace.service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AssociationRepository associationRepository;
    private CompteProRepository CompteProRepository;
    private final RestTemplate restTemplate;

    private final UserService userService;

    private EntrepriseWSMJService entrepriseWSMJService;

    public EntrepriseService(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper, CompteProRepository CompteProRepository, RestTemplate restTemplate, ProcurationRepository procurationRepository, EntrepriseWSMJService entrepriseWSMJService, AssociationRepository associationRepository, UserService userService) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.CompteProRepository = CompteProRepository;
        this.restTemplate = restTemplate;
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
    private boolean isCurrentUser(UUID accountId) {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = userService.getUserIdFromToken(authenticationToken);
        return currentUserId != null && accountId.toString().equals(currentUserId);
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

    private boolean checkPp(PersonnephysiqueDTO personnephysiqueDTO, UUID accountid) {
        ComptePro compte = CompteProRepository.getById(accountid);
        if (personnephysiqueDTO.getPersonneRc().getCommercant().getNumPiece().equals(compte.getIdentifiant())) {
            return true;
        } else {
            return false; // No match found
        }

    }


    public void createCompany(EntrepriseRequest2 entrepriseRequest) {
        switch (entrepriseRequest.getPerphysique_Permorale()) {
            case PHYSICAL_PERSON:
                handlePhysicalPerson(entrepriseRequest);
                break;
            case MORAL_PERSON:
                handleMoralPerson(entrepriseRequest);
                break;
            default:
                log.info("Statut non reconnu");
                break;
        }
    }

    private void handlePhysicalPerson(EntrepriseRequest2 entrepriseRequest) {
        PersonnephysiqueDTO personnephysiqueDTO = entrepriseWSMJService.getBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        ComptePro compte = CompteProRepository.getById(entrepriseRequest.getCOMPID());
        String currentUsername = null;
        if (currentUserLogin.isPresent()) {
            if (isCurrentUser(entrepriseRequest.getCOMPID())) {
                boolean isManager = checkManagerPp(compte, personnephysiqueDTO);
                if (isManager) {
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
                // Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
                // String currentUsername = currentUserLogin.get();
                JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                String userIdString = userService.getUserIdFromToken(authenticationToken);
                UUID accountConnectedId = UUID.fromString(userIdString);
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
                }

            }
        }
    }

    private void handleMoralPerson(EntrepriseRequest2 entrepriseRequest) {
        EntrepriseWSMJ entrepriseWS = null;
        try {
            entrepriseWS = entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
        } catch (NullPointerException e) {
            log.error("EntrepriseWS is null: " + e.getMessage());
        }
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        ComptePro compte = CompteProRepository.getById(entrepriseRequest.getCOMPID());
        String currentUsername = null;
        if (currentUserLogin.isPresent()) {
            if (isCurrentUser(entrepriseRequest.getCOMPID())) {
                boolean isManager = checkManager(compte, entrepriseWS, entrepriseRequest.getCOMPID());
                if (isManager) {
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
                // Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
                // String currentUsername = currentUserLogin.get();
                JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                String userIdString = userService.getUserIdFromToken(authenticationToken);
                UUID accountConnectedId = UUID.fromString(userIdString);
                EntrepriseWSMJService dirigeantService = null;
                DIRIGEANTDTO dirigeants = null;
                try {
                    dirigeants = entrepriseWSMJService.getDirigeantBycodeJuridictionAndnumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                } catch (Exception e) {
                    // Gérer l'exception spécifique ici, par exemple, logguer l'erreur ou prendre d'autres mesures appropriées
                    log.info("Une erreur est survenue lors de l'appel à EntrepriseWSMJService : " + e.getMessage());
                }
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

                }
            }
        }
    }
}




