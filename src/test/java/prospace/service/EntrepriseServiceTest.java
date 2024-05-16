package prospace.service;
import ma.digital.prospace.domain.enumeration.Statut;
import ma.digital.prospace.domain.enumeration.StatutInvitation;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ma.digital.prospace.domain.ComptePro;
import ma.digital.prospace.domain.Entreprise;
import ma.digital.prospace.domain.Procuration;
import ma.digital.prospace.domain.enumeration.typeidentifiant;
import ma.digital.prospace.repository.AssociationRepository;
import ma.digital.prospace.repository.CompteProRepository;
import ma.digital.prospace.repository.EntrepriseRepository;
import ma.digital.prospace.repository.ProcurationRepository;
import ma.digital.prospace.security.SpringSecurityAuditorAware;
import ma.digital.prospace.service.EntrepriseService;
import ma.digital.prospace.service.EntrepriseWSMJService;
import ma.digital.prospace.service.TribunalWSMJService;
import ma.digital.prospace.service.UserService;
import ma.digital.prospace.service.dto.*;
import ma.digital.prospace.service.mapper.EntrepriseMapper;
import ma.digital.prospace.web.rest.errors.BadRequestAlertException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;

import ma.digital.prospace.domain.enumeration.typeidentifiant;

import static ma.digital.prospace.domain.enumeration.Statut.MORAL_PERSON;
import static ma.digital.prospace.domain.enumeration.Statut.PHYSICAL_PERSON;
import static ma.digital.prospace.domain.enumeration.StatutAssociation.PENDING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.data.domain.Sort;

import org.springframework.web.client.RestTemplate;
import org.testcontainers.utility.AuditLogger;

import java.util.UUID;

public class EntrepriseServiceTest {
    @Mock
    private EntrepriseRepository entrepriseRepository;
    @Mock
    private AuditorAware<String> auditorAware;
    @Mock
    private EntrepriseMapper entrepriseMapper;
    @Mock
    Authentication authentication;


    @Mock
    private CompteProRepository compteProRepository;
    private MockMvc mockMvc;
    @Mock
    private ProcurationRepository procurationRepository;
    @Mock
    private static  Logger auditLogger1 = LoggerFactory.getLogger("ma.digital.prospace.audit");

    @Mock
    private EntrepriseWSMJService entrepriseWSMJService;

    @Mock
    private AssociationRepository associationRepository;
    @Mock
    private TribunalWSMJService tribunalWSMJService;
    @Mock
    private UserService userService;

    @InjectMocks
    private EntrepriseService entrepriseService;
    @Timeout(30) // Augmente la limite de temps à 30 secondes
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(entrepriseWSMJService, compteProRepository, entrepriseRepository, entrepriseMapper);
    }

    @Test
    public void testSave() {
        // Create a mock entrepriseDTO
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();

        // Create a mock entreprise
        Entreprise entreprise = new Entreprise();

        // Set the ID of the entreprise
        entreprise.setId(UUID.randomUUID());

        // Convert the entrepriseDTO to the entreprise
        when(entrepriseMapper.toEntity(entrepriseDTO)).thenReturn(entreprise);

        // Save the entreprise
        when(entrepriseRepository.save(entreprise)).thenReturn(entreprise);

        // Convert the entreprise back to entrepriseDTO
        when(entrepriseMapper.toDto(entreprise)).thenReturn(entrepriseDTO);

        // Call the save method
        EntrepriseDTO savedEntrepriseDTO = entrepriseService.save(entrepriseDTO);

        // Verify that the entrepriseRepository.save() method was called
        verify(entrepriseRepository).save(entreprise);

        // Verify that the savedEntrepriseDTO is the same as the entrepriseDTO
        assertEquals(savedEntrepriseDTO, entrepriseDTO);
    }
    @Test
    void testIsCurrentUserWithJwt() {
        // Given
        String accountId = "testAccountId";
        Jwt jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getSubject()).thenReturn(accountId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        boolean result = entrepriseService.isCurrentUser(accountId);

        // Then
        assertTrue(result);
    }
    @Test
    void testIsCurrentUserWithOidcUser() {
        // Given
        String accountId = "testAccountId";
        OidcUser oidcUser = mock(OidcUser.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        when(oidcUser.getSubject()).thenReturn(accountId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        boolean result = entrepriseService.isCurrentUser(accountId);

        // Then
        assertTrue(result);
    }
    @Test
    void testIsCurrentUserWithNoAuthentication() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(null);

        // When, Then
        assertThrows(IllegalStateException.class, () -> entrepriseService.isCurrentUser("testAccountId"));
    }

    @Test
    void testIsCurrentUserWithUnknownPrincipalType() {
        // Given
        when(authentication.getPrincipal()).thenReturn("unknownPrincipalType");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When, Then
        assertThrows(IllegalStateException.class, () -> entrepriseService.isCurrentUser("testAccountId"));
    }
    @Test
    void testUserIdWithJwt() {
        // Given
        String accountId = "testAccountId";
        Jwt jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getSubject()).thenReturn(accountId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        String result = entrepriseService.UserId(accountId);

        // Then
        assertEquals(accountId, result);
    }
    @Test
    void testCheckManagerPp() {
        // Given
        EntrepriseRequest2 entrepriseRequest2 = new EntrepriseRequest2();
        entrepriseRequest2.setCIN("123456");
        entrepriseRequest2.setIndentifianttype(typeidentifiant.CNI);

        PersonnephysiqueDTO personnePhysiqueDTO = new PersonnephysiqueDTO();
        PersonneRcDto3 personneRcDTO = new PersonneRcDto3();
        CommercantDto commercantDto = new CommercantDto();

        String numPiece = "123456";
        String typePiece = "CNI";

        commercantDto.setNumPiece(numPiece);
        commercantDto.setTypePiece(typePiece);
        personneRcDTO.setCommercant(commercantDto);
        personnePhysiqueDTO.setPersonneRc(personneRcDTO);

        // Mocks
        EntrepriseMapper entrepriseMapper = Mockito.mock(EntrepriseMapper.class);

        // When
        EntrepriseService entrepriseService = new EntrepriseService(
                new SpringSecurityAuditorAware(),
                Mockito.mock(TribunalWSMJService.class),
                Mockito.mock(EntrepriseRepository.class),
                entrepriseMapper,
                Mockito.mock(CompteProRepository.class),
                Mockito.mock(ProcurationRepository.class),
                Mockito.mock(EntrepriseWSMJService.class),
                Mockito.mock(AssociationRepository.class),
                Mockito.mock(UserService.class)
        );

        // Then
        boolean result = entrepriseService.checkManagerPp(entrepriseRequest2, personnePhysiqueDTO);

        assertTrue(result, "Expected the manager to be found");

        // Additional test for when no match is found
        entrepriseRequest2.setCIN("999999");
        result = entrepriseService.checkManagerPp(entrepriseRequest2, personnePhysiqueDTO);
        assertFalse(result, "Expected no manager to be found");
    }
    @Test
    void testCheckDirigeantsWS() {
        // Given
        EntrepriseRequest2 entrepriseRequest2 = new EntrepriseRequest2();
        entrepriseRequest2.setCIN("123456");
        entrepriseRequest2.setIndentifianttype(typeidentifiant.CNI);

        EntrepriseWSMJ entrepriseWSMJ = new EntrepriseWSMJ();
        IdentificationDTO identification = new IdentificationDTO();
        identification.setNumRC("123456");
        entrepriseWSMJ.setPersonneRc(new PersonneRcDTO());
        entrepriseWSMJ.getPersonneRc().setIdentification(identification);

        DIRIGEANTDTO dirigeantDTO = new DIRIGEANTDTO();
        IdentificationDTO2 identificationDirigeant = new IdentificationDTO2();
        identificationDirigeant.setNumRC("123456"); // Fixing the identification number
        dirigeantDTO.setPersonneRc(new PersonneRcDTO2());
        dirigeantDTO.getPersonneRc().setIdentification(identificationDirigeant);

        List<DirigeantPMDTO2> dirigeantsPM = new ArrayList<>();
        DirigeantPMDTO2 dirigeantPM = new DirigeantPMDTO2();
        List<RepresentantDTO> representants = new ArrayList<>();
        RepresentantDTO representant = new RepresentantDTO();
        representant.setNumPiece("123456");
        representant.setTypePiece("CNI");
        representants.add(representant);
        dirigeantPM.setRepresentant(representants);
        dirigeantsPM.add(dirigeantPM);
        dirigeantDTO.getPersonneRc().setDirigeantsPM(dirigeantsPM);

        // Mocks
        EntrepriseMapper entrepriseMapper = Mockito.mock(EntrepriseMapper.class);

        // When
        EntrepriseService entrepriseService = new EntrepriseService(
                new SpringSecurityAuditorAware(),
                Mockito.mock(TribunalWSMJService.class),
                Mockito.mock(EntrepriseRepository.class),
                entrepriseMapper,
                Mockito.mock(CompteProRepository.class),
                Mockito.mock(ProcurationRepository.class),
                Mockito.mock(EntrepriseWSMJService.class),
                Mockito.mock(AssociationRepository.class),
                Mockito.mock(UserService.class)
        );

        // Then
        boolean result = entrepriseService.checkDirigeantsWS(entrepriseRequest2, entrepriseWSMJ, dirigeantDTO);

        assertTrue(result, "Expected the manager to be found");

        // Additional test for when no match is found
        entrepriseRequest2.setCIN("999999");
        result = entrepriseService.checkDirigeantsWS(entrepriseRequest2, entrepriseWSMJ, dirigeantDTO);
        assertFalse(result, "Expected no manager to be found");
    }
    @Test
    void testCheckPp() {
        // Given
        PersonnephysiqueDTO personnephysiqueDTO = new PersonnephysiqueDTO();
        CommercantDto commercantDto = new CommercantDto();
        commercantDto.setNumPiece("123456"); // Numéro de pièce du commercant
        personnephysiqueDTO.setPersonneRc(new PersonneRcDto3());
        personnephysiqueDTO.getPersonneRc().setCommercant(commercantDto);

        UUID accountId1 = UUID.randomUUID();
        String accountId = accountId1.toString();
        ComptePro comptePro = new ComptePro();
        comptePro.setIdentifiant("123456"); // Identifiant du compte

        Mockito.when(compteProRepository.findByCustomIdQuery(accountId)).thenReturn(Optional.of(comptePro));

        // When
        EntrepriseService entrepriseService = new EntrepriseService(
                Mockito.mock(SpringSecurityAuditorAware.class),
                Mockito.mock(TribunalWSMJService.class),
                Mockito.mock(EntrepriseRepository.class),
                Mockito.mock(EntrepriseMapper.class),
                compteProRepository,
                Mockito.mock(ProcurationRepository.class),
                Mockito.mock(EntrepriseWSMJService.class),
                Mockito.mock(AssociationRepository.class),
                Mockito.mock(UserService.class)
        );

        boolean result = entrepriseService.checkPp(personnephysiqueDTO, accountId);

        // Then
        assertTrue(result, "Expected match found");

        // Additional test for when no match is found
        commercantDto.setNumPiece("654321"); // Change the piece number to something different
        result = entrepriseService.checkPp(personnephysiqueDTO, accountId);
        assertFalse(result, "Expected no match found");
    }
    @Test
    void testEntreUpdatePhysiqueNormalProcuration() {
        // Given
        EntrepriseRequest2 entrepriseRequest = new EntrepriseRequest2();
        entrepriseRequest.setCOMPID("compt123");

        PersonnephysiqueDTO personnephysiqueDTO = new PersonnephysiqueDTO();
        Entreprise entreprise = new Entreprise();
        entreprise.setEtat("some state");
        personnephysiqueDTO.setPersonneRc(new PersonneRcDto3());
        personnephysiqueDTO.getPersonneRc().setEtat("some state");

        ComptePro compte = new ComptePro();

        // Mocking the repository and mapper
        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(entreprise);
        when(entrepriseMapper.toDto(any(Entreprise.class))).thenAnswer(invocation -> {
            Entreprise arg = invocation.getArgument(0);
            EntrepriseDTO dto = new EntrepriseDTO();
            dto.setEtat(arg.getEtat());
            return dto;
        });

        // When
        EntrepriseService entrepriseService = new EntrepriseService(
                Mockito.mock(SpringSecurityAuditorAware.class),
                Mockito.mock(TribunalWSMJService.class),
                entrepriseRepository,
                entrepriseMapper,
                compteProRepository,
                Mockito.mock(ProcurationRepository.class),
                Mockito.mock(EntrepriseWSMJService.class),
                Mockito.mock(AssociationRepository.class),
                Mockito.mock(UserService.class)
        );

        // Then
        EntrepriseDTO result = entrepriseService.entre_updatephysique_normalprocuration(entrepriseRequest, personnephysiqueDTO, compte);

        // Verify repository method calls
        verify(entrepriseRepository).save(any(Entreprise.class));
        verify(compteProRepository).save(compte);

        // Assert
        assertEquals("some state", result.getEtat());
    }
    @Test
    void testCheckManager() {
        // Given
        EntrepriseRequest2 entrepriseRequest2 = new EntrepriseRequest2();
        entrepriseRequest2.setCIN("123456");
        entrepriseRequest2.setIndentifianttype(typeidentifiant.CNI);

        EntrepriseWSMJ entreprise = new EntrepriseWSMJ();
        DirigeantPMDTO dirigeant1 = new DirigeantPMDTO();
        List<RepresentantDTO> representants1 = new ArrayList<>();

        String numPiece = "123456";
        String typePiece = "CNI";
        String numPiece1 = "123453";
        String typePiece1= "CNI";
        RepresentantDTO representant1 = new RepresentantDTO();
        representant1.setNumPiece(numPiece);
        representant1.setTypePiece(typePiece);
        representants1.add(representant1);
        dirigeant1.setRepresentants(representants1);
        DirigeantPMDTO dirigeant2 = new DirigeantPMDTO();
        RepresentantDTO representant2 = new RepresentantDTO();
        representant2.setNumPiece(numPiece1);
        representant2.setTypePiece(typePiece1);
        representants1.add(representant2);
        dirigeant2.setRepresentants(representants1);

        List<DirigeantPMDTO> dirigeantsPM = new ArrayList<>();
        dirigeantsPM.add(dirigeant1);
        dirigeantsPM.add(dirigeant2);
        entreprise.setPersonneRc(new PersonneRcDTO());
        entreprise.getPersonneRc().setDirigeantsPM(dirigeantsPM);

        String CompID = "compte123";

        // Mocks
        EntrepriseMapper entrepriseMapper = Mockito.mock(EntrepriseMapper.class);
        EntrepriseRepository entrepriseRepository = Mockito.mock(EntrepriseRepository.class);
        TribunalWSMJService tribunalWSMJService = Mockito.mock(TribunalWSMJService.class);
        CompteProRepository compteProRepository = Mockito.mock(CompteProRepository.class);
        ProcurationRepository procurationRepository = Mockito.mock(ProcurationRepository.class);
        EntrepriseWSMJService entrepriseWSMJService = Mockito.mock(EntrepriseWSMJService.class);
        AssociationRepository associationRepository = Mockito.mock(AssociationRepository.class);
        UserService userService = Mockito.mock(UserService.class);

        // When
        EntrepriseService entrepriseService = new EntrepriseService(
                new SpringSecurityAuditorAware(),
                tribunalWSMJService,
                entrepriseRepository,
                entrepriseMapper,
                compteProRepository,
                procurationRepository,
                entrepriseWSMJService,
                associationRepository,
                userService
        );
        Mockito.when(entrepriseMapper.toDto(Mockito.any(Entreprise.class))).thenReturn(new EntrepriseDTO());

        boolean result = entrepriseService.checkManager(entrepriseRequest2, entreprise, CompID);

        // Then
        assertTrue(result, "Expected the manager to be found");

        // Additional test for when no match is found
        entrepriseRequest2.setCIN("999999");
        result = entrepriseService.checkManager(entrepriseRequest2, entreprise, CompID);
        assertFalse(result, "Expected no manager to be found");
    }
    @Test
    public void testHandlePhysicalPerson_personnephysiqueDTOIsNull() {
        // Given
        EntrepriseRequest2 entrepriseRequest = new EntrepriseRequest2();
        entrepriseRequest.setTribunal("tribunal");
        entrepriseRequest.setNumeroRC("numeroRC");
        entrepriseRequest.setCOMPID("compte1234");

        // Mocking
        when(entrepriseWSMJService.getBycodeJuridictionAndnumRC(anyString(), anyString())).thenReturn(null);

        // When
        EntrepriseDTO result = entrepriseService.handlePhysicalPerson(entrepriseRequest);

        // Then
        assertNull(result);
    }

    @Test
    public void testHandlePhysicalPerson_procurationNonValide() {
        // Given
        EntrepriseRequest2 entrepriseRequest = new EntrepriseRequest2();
        entrepriseRequest.setTribunal("tribunal");
        entrepriseRequest.setNumeroRC("numeroRC");
        entrepriseRequest.setCOMPID("compte1243");

        PersonnephysiqueDTO personnephysiqueDTO = new PersonnephysiqueDTO();
        personnephysiqueDTO.setMessage("");
        personnephysiqueDTO.setSuccess(false);

        Juridiction juridiction1 = new Juridiction();
        juridiction1.setCode("code1");
        juridiction1.setLibelleAr("libelleAr1");
        juridiction1.setLibelleFr("libelleFr1");

        Juridiction juridiction2 = new Juridiction();
        juridiction2.setCode("code2");
        juridiction2.setLibelleAr("libelleAr2");
        juridiction2.setLibelleFr("libelleFr2");

        List<Juridiction> juridictions = new ArrayList<>();
        juridictions.add(juridiction1);
        juridictions.add(juridiction2);

        ComptePro compte = new ComptePro();
        compte.setEntrepriseGeree(null);

        Procuration procuration = new Procuration();
        procuration.setStatut(StatutInvitation.PENDING);

        // Mocking
        when(entrepriseWSMJService.getBycodeJuridictionAndnumRC(anyString(), anyString())).thenReturn(personnephysiqueDTO);
        when(tribunalWSMJService.getListeTribunaux()).thenReturn(juridictions);
        when(compteProRepository.findByCustomIdQuery(any(String.class))).thenReturn(Optional.of(compte));
        when(procurationRepository.findProcurationByUtilisateurProIdAndGestionnaireEspaceProId(any(String.class), any(String.class))).thenReturn(procuration);

        // When
        EntrepriseDTO result = entrepriseService.handlePhysicalPerson(entrepriseRequest);

        // Then
        assertNull(result);
    }



    @Test
    void testHandleMoralPerson_NoExistingEnterprise() throws BadRequestAlertException {
        // Given
        EntrepriseRequest2 entrepriseRequest = new EntrepriseRequest2();
        entrepriseRequest.setCOMPID("compte123");
        entrepriseRequest.setTribunal("TribunalTest");
        entrepriseRequest.setNumeroRC("12345");

        // Mock entrepriseWSMJService
        when(entrepriseWSMJService.getEntrepriseByJuridictionAndNumRC(any(), any())).thenReturn(null);

        // Mock tribunalWSMJService
        when(tribunalWSMJService.getListeTribunaux()).thenReturn(List.of(/* Add your mock Juridiction here */));

        // Mock CompteProRepository
        when(compteProRepository.findByCustomIdQuery(any())).thenReturn(Optional.empty());

        // When
        EntrepriseDTO result = entrepriseService.handleMoralPerson(entrepriseRequest);

        // Then
        assertEquals(null, result);
    }



    @Test
    void testUserIdWithOidcUser() {
        // Given
        String accountId = "testAccountId";
        OidcUser oidcUser = mock(OidcUser.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        when(oidcUser.getSubject()).thenReturn(accountId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        String result = entrepriseService.UserId(accountId);

        // Then
        assertEquals(accountId, result);
    }

    @Test
    void testUserIdWithNoAuthentication() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(null);

        // When, Then
        assertThrows(IllegalStateException.class, () -> entrepriseService.UserId("testAccountId"));
    }

    @Test
    void testUserIdWithUnknownPrincipalType() {
        // Given
        when(authentication.getPrincipal()).thenReturn("unknownPrincipalType");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When, Then
        assertThrows(IllegalStateException.class, () -> entrepriseService.UserId("testAccountId"));
    }

    @Test
    void testUpdate() {
        // Create a mock entrepriseDTO
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(UUID.randomUUID());
        entrepriseDTO.setEtat("Test Enterprise");

        // Create a mock entreprise
        Entreprise entreprise = new Entreprise();
        entreprise.setId(UUID.randomUUID());
        entreprise.setEtat("Test Enterprise");

        // Stubbing the mapper
        when(entrepriseMapper.toEntity(entrepriseDTO)).thenReturn(entreprise);
        when(entrepriseMapper.toDto(entreprise)).thenReturn(entrepriseDTO);

        // Stubbing the repository
        when(entrepriseRepository.save(entreprise)).thenReturn(entreprise);

        // Call the update method
        EntrepriseDTO updatedEntrepriseDTO = entrepriseService.update(entrepriseDTO);

        // Verify interactions
        verify(entrepriseMapper).toEntity(entrepriseDTO);
        verify(entrepriseRepository).save(entreprise);
        verify(entrepriseMapper).toDto(entreprise);

        // Verify the returned DTO
        assertNotNull(updatedEntrepriseDTO);
        assertEquals(entrepriseDTO.getId(), updatedEntrepriseDTO.getId());
        assertEquals(entrepriseDTO.getEtat(), updatedEntrepriseDTO.getEtat());
    }

    @Test
    void testFindOne() {
        // Given
        UUID id = UUID.randomUUID();
        Entreprise entreprise = new Entreprise();
        entreprise.setId(id);
        EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
        entrepriseDTO.setId(id);

        // Stubbing the repository to return the entreprise
        when(entrepriseRepository.findById(id)).thenReturn(Optional.of(entreprise));

        // Stubbing the mapper
        when(entrepriseMapper.toDto(entreprise)).thenReturn(entrepriseDTO);

        // When
        Optional<EntrepriseDTO> result = entrepriseService.findOne(id);

        // Then
        verify(entrepriseRepository).findById(id);
        assertEquals(entrepriseDTO, result.orElse(null));
    }
    @Test
    void testFindAll() {
        // Given
        List<Entreprise> entreprises = new ArrayList<>();
        Entreprise entreprise1 = new Entreprise();
        entreprise1.setId(UUID.randomUUID());
        entreprise1.setEtat("Entreprise 1");
        entreprises.add(entreprise1);

        Entreprise entreprise2 = new Entreprise();
        entreprise2.setId(UUID.randomUUID());
        entreprise2.setEtat("Entreprise 2");
        entreprises.add(entreprise2);
        Page<Entreprise> page = new PageImpl<>(entreprises);

        Pageable pageable = Pageable.unpaged();

        // Stubbing the repository to return the page of entreprises
        when(entrepriseRepository.findAll(pageable)).thenReturn(page);

        // Stubbing the mapper
        List<EntrepriseDTO> entrepriseDTOs = new ArrayList<>();
        // Créer les objets EntrepriseDTO
        EntrepriseDTO entrepriseDTO1 = new EntrepriseDTO();
        entrepriseDTO1.setId(entreprise1.getId());
        entrepriseDTO1.setEtat(entreprise1.getEtat());
        entrepriseDTOs.add(entrepriseDTO1);

        EntrepriseDTO entrepriseDTO2 = new EntrepriseDTO();
        entrepriseDTO2.setId(entreprise2.getId());
        entrepriseDTO2.setEtat(entreprise2.getEtat());
        entrepriseDTOs.add(entrepriseDTO2);

        when(entrepriseMapper.toDto(entreprise1)).thenReturn(entrepriseDTO1);
        when(entrepriseMapper.toDto(entreprise2)).thenReturn(entrepriseDTO2);

        // When
        Page<EntrepriseDTO> result = entrepriseService.findAll(pageable);

        // Then
        verify(entrepriseRepository).findAll(pageable);
        assertEquals(entrepriseDTOs, result.getContent());
    }

    @Test
    void testDelete() {
        // Given
        UUID idToDelete = UUID.randomUUID();

        // When
        entrepriseService.delete(idToDelete);

        // Then
        verify(entrepriseRepository).deleteById(idToDelete);
    }

    @Test
    public void testChecktribunal_True() {
        // Créer un objet mock pour EntrepriseRequest2
        EntrepriseRequest2 entrepriseRequest = mock(EntrepriseRequest2.class);
        when(entrepriseRequest.getTribunal()).thenReturn("tribunal");

        // Créer une liste de mock pour Juridiction
        List<Juridiction> juridictions = new ArrayList<>();
        Juridiction tribunal = mock(Juridiction.class);
        when(tribunal.getCode()).thenReturn("tribunal");
        juridictions.add(tribunal);

        // Appeler la méthode à tester
        boolean result = entrepriseService.checktribunal(juridictions, entrepriseRequest);

        // Vérifier le résultat
        assertTrue(result);
    }
}