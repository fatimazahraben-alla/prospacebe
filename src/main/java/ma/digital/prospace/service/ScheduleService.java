package ma.digital.prospace.service;

import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.service.dto.*;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ScheduleService {

    EntrepriseService entrepriseService;
    private final CompteProRepository compteProRepository;
    private final TribunalWSMJService tribunalWSMJService;

    private final CompteProService compteProService;
    private final EntrepriseRepository entrepriseRepository;
    private final EntrepriseWSMJService entrepriseWSMJService;


    private static final Logger auditLogger1 = LoggerFactory.getLogger("ma.digital.prospace.audit");

    public ScheduleService(CompteProService compteProService, TribunalWSMJService tribunalWSMJService, EntrepriseService entrepriseService, CompteProRepository compteProRepository, EntrepriseRepository entrepriseRepository, EntrepriseWSMJService entrepriseWSMJService) {
        this.entrepriseService = entrepriseService;
        this.compteProRepository = compteProRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseWSMJService = entrepriseWSMJService;
        this.tribunalWSMJService = tribunalWSMJService;
        this.compteProService = compteProService;

    }

    @Scheduled(cron = "0 0 12 ? * MON")
    public void validateManagerinMJ(EntrepriseRequest2 entrepriseRequest) {
/*
            List<CompteProDTO> comptes = compteProService.findAll();
            for (CompteProDTO compte : comptes) {
                // Récupérer les informations nécessaires pour valider le manager dans MJ
                String compteid = compte.getId();
                entrepriseRequest.setIndentifianttype(null); // Assurez-vous que ces valeurs sont correctement initialisées
                entrepriseRequest.setPerphysique_Permorale();
                // Récupérer les informations de l'entreprise depuis MJ
                EntrepriseWSMJ entrepriseWS = entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                List<Juridiction> juridictions = tribunalWSMJService.getListeTribunaux();
                // Vérifier si l'entreprise est active
                if ((entrepriseWS == null) || !entrepriseService.checktribunal(juridictions, entrepriseRequest)) {
                    auditLogger1.warn("Entreprise inactive : {} {}", entrepriseRequest.getTribunal(), entrepriseRequest.getNumeroRC());
                    // Log ou action appropriée en cas d'entreprise inactive
                } else {
                    Optional<ComptePro> compteOptional = compteProRepository.findByCustomIdQuery(entrepriseRequest.getCOMPID());
                    ComptePro comptePro = compteOptional.orElse(null);

                    // Vérifier si le compte est un gestionnaire et effectuer d'autres validations nécessaires
                    if (entrepriseRequest.getCompteInitiateur().equals(entrepriseRequest.getCOMPID())) {
                        if (entrepriseService.checkManager(entrepriseRequest, entrepriseWS, entrepriseRequest.getCOMPID())) {
                            // Actions à effectuer si le compte est un gestionnaire valide
                        } else {
                            // Actions à effectuer si le compte n'est pas un gestionnaire valide
                        }
                    } else {
                        // Actions à effectuer si les conditions ne sont pas remplies pour le compte
                    }
                }
            }


    */
    }
}
