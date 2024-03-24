package ma.digital.prospace.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.repository.CompteProRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.service.mapper.EntrepriseMapper;
import ma.digital.prospace.service.dto.*;


/**
 * Service Implementation for managing {@link Entreprise}.
 */
@Service
@Transactional
public class EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseService.class);

    private final EntrepriseRepository entrepriseRepository;

    private final EntrepriseMapper entrepriseMapper;


    private CompteProRepository CompteProRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper, CompteProRepository CompteProRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.CompteProRepository = CompteProRepository;
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

    public String getCurrentUserId(AbstractAuthenticationToken authToken) {
        Map<String, Object> attributes;
        if (authToken instanceof OAuth2AuthenticationToken) {
            attributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();
        } else if (authToken instanceof JwtAuthenticationToken) {
            attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
        } else {
            throw new IllegalArgumentException("AuthenticationToken is not OAuth2 or JWT!");
        }
        String userId;
        // Logic to extract user ID from attributes
        if (attributes.containsKey("sub")) {
            userId = (String) attributes.get("sub");
        } else if (attributes.containsKey("uid")) {
            userId = (String) attributes.get("uid");
        } else {
            // Handle case where user ID is not found
            throw new IllegalStateException("User ID not found in authentication attributes");
        }
        return userId;
    }

    private boolean isCurrentUser(AbstractAuthenticationToken authToken, Long accountId) {
        String currentUserId = getCurrentUserId(authToken);
        return accountId.toString().equals(currentUserId);
    }

    /*public EntrepriseDTO findCompanyById(Long id) {
            EntrepriseDTO entrepriseDTO = ministryOfJusticeClient.findEntrepriseById(id);
            return entrepriseDTO;
        }*/

/*    public List<CompanyManagement> getCompanyManagements() {
        // Assuming your MinistryOfJusticeClient has a method to retrieve company managements
        return ministryOfJusticeClient.getCompanyManagements();
    }
*/
    // Method to retrieve the list of company managements from Ministry of Justice
    // public List<CompanyManagementDTO> retrieveCompanyManagements() {
    //         return ministryOfJusticeClient.retrieveCompanyManagements();
    // Example of logging
    // Handle the exception according to your application's requirements
    //  return Collections.emptyList(); // Return an empty list if the retrieval fails

    //  }
       /* public List<CompanyManagementDTO> retrieveCompanyManagements() {
        try {
            return ministryOfJusticeClient.retrieveCompanyManagements();
        } catch (MinistryOfJusticeServiceException ex) {
            ex.printStackTrace(); // Example of logging
            // Handle the exception according to your application's requirements
            return Collections.emptyList(); // Return an empty list if the retrieval fails
        }
    } */


    // Method to create a company and associate it with an account
  /*  public void createCompany(Long accountId, EntrepriseDTO entrepriseDTO,Long entrepriseid) {
        // find the company, call the ws of the ministry of justice
        EntrepriseDTO company = findCompanyById(entrepriseid);
         //if the accountID = person connected,
        if (isCurrentUser(accountId)) {
          //check that he is the manager of the company
            if (CompteProRepository.isManagerOfCompany(accountId,entrepriseid))
            {
                // Create the Entreprise object
                Entreprise entreprise = new Entreprise();
                entreprise.setId(entrepriseDTO.getId());
                entreprise.setDenomination(entrepriseDTO.getDenomination());
                entreprise.setStatutJuridique(entrepriseDTO.getStatutJuridique());
                entreprise.setTribunal(entrepriseDTO.getTribunal());
                entreprise.setNumeroRC(entrepriseDTO.getNumeroRC());
                entreprise.setIce(entrepriseDTO.getIce());
                entreprise.setActivite(entrepriseDTO.getActivite());
                entreprise.setFormeJuridique(entrepriseDTO.getFormeJuridique());
                entreprise.setDateImmatriculation(entrepriseDTO.getDateImmatriculation());
                entreprise.setEtat(entrepriseDTO.getEtat());

                // Save the Entreprise object
                entreprise = entrepriseRepository.save(entreprise);
                ComptePro account = CompteProRepository.getById(accountId);
                if (account != null) {
                    // Assuming you have a method to set the association between the account and the company
                    account.setEntrepriseGeree(entreprise);
                    // Optionally, you can also add the account to the company's set of managers
                    entreprise.getGerants().add(account);
                }
                 if (!account.getMandants().isEmpty())
                 {

                 }


        } else {
            // If the user is not the manager of the company, handle the situation accordingly
            // For example, you might return an error message or perform another action
        }
    } else {
        // Step 3: Handle the case where the accountID does not match the ID of the currently logged-in user
        // For example, you might log an error, return an error message, or perform another action
    }
}

            // Retrieve the list of company managements
            //  List<CompanyManagementDTO> companyManagements = retrieveCompanyManagements();
            // Handle the company managements according to your application's requirements
            // For example, you might display them to the user or perform some other action
*/
}



        // Check if the accountID matches the ID of the currently logged-in user



