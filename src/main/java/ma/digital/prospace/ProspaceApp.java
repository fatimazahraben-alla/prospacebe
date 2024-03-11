package ma.digital.prospace;

import jakarta.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import ma.digital.prospace.config.ApplicationProperties;
import ma.digital.prospace.config.CRLFLogConverter;
import ma.digital.prospace.domain.*;
import ma.digital.prospace.repository.*;
import ma.digital.prospace.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import tech.jhipster.config.DefaultProfileUtil;
import tech.jhipster.config.JHipsterConstants;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
public class ProspaceApp {

    private static final Logger log = LoggerFactory.getLogger(ProspaceApp.class);

    private final Environment env;

    public ProspaceApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes prospace.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
        ) {
            log.error(
                "You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
            );
        }
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)
        ) {
            log.error(
                "You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
            );
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProspaceApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);

    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String applicationName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
            .ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            """

            ----------------------------------------------------------
            \tApplication '{}' is running! Access URLs:
            \tLocal: \t\t{}://localhost:{}{}
            \tExternal: \t{}://{}:{}{}
            \tProfile(s): \t{}
            ----------------------------------------------------------""",
            applicationName,
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );
    }

    private static void insertTestData(ApplicationContext ctx) {
        // Création des instances
        Entreprise entreprise = new Entreprise();
        entreprise.setDenomination("Nom de l'entreprise");
        entreprise.setStatutJuridique("SARL");
        entreprise.setTribunal("Tribunal de commerce");
        entreprise.setNumeroRC("1234567890");
        entreprise.setIce("ICE123456");
        entreprise.setActivite("Activité de l'entreprise");
        entreprise.setFormeJuridique("Forme juridique de l'entreprise");
        entreprise.setDateImmatriculation(Instant.now());
        entreprise.setEtat("Actif");
        // Définir les autres attributs...

        Contact contact = new Contact();
        contact.setMail("contact@mail.com");
        contact.setTelephone("0123456789");
        contact.setDeviceToken("token123");
        contact.setDeviceOS("Android");
        contact.setDeviceVersion("10.0");
        // Définir les autres attributs...

        ComptePro comptePro = new ComptePro();
        comptePro.setCreatedAt(new Date());
        comptePro.setUpdatedAt(new Date());
        comptePro.setDeleted(false);
        // Définir les autres attributs...

        Association association = new Association();
        association.setDateEffet(Instant.now());
        association.setDateFin(Instant.now().plus(Duration.ofDays(30)));
        association.setMail("association@mail.com");
        association.setTelephone("9876543210");
        // Définir les autres attributs...

        Rolee rolee = new Rolee();
        rolee.setNom("Nom du rôle");
        rolee.setDescription("Description du rôle");
        // Définir les autres attributs...

        Session session = new Session();
        session.setTransactionId(12345L);
        session.setCreatedAt(new Date());
        session.setJsonData("{'key': 'value'}");
        // Définir les autres attributs...

        Procuration procuration = new Procuration();
        procuration.setDateEffet(Instant.now());
        procuration.setDateFin(Instant.now().plus(Duration.ofDays(30)));
        // Définir les autres attributs...

        // Utilisation des services pour sauvegarder les instances
        EntrepriseRepository entrepriseService = ctx.getBean(EntrepriseRepository.class);
        entrepriseService.save(entreprise);

        ContactRepository contactService = ctx.getBean(ContactRepository.class);
        contactService.save(contact);

        CompteProRepository compteProService = ctx.getBean(CompteProRepository.class);
        compteProService.save(comptePro);

        AssociationRepository associationService = ctx.getBean(AssociationRepository.class);
        associationService.save(association);

        RoleeRepository roleeService = ctx.getBean(RoleeRepository.class);
        roleeService.save(rolee);

        SessionRepository sessionService = ctx.getBean(SessionRepository.class);
        sessionService.save(session);

        ProcurationRepository procurationService = ctx.getBean(ProcurationRepository.class);
        procurationService.save(procuration);
    }

}
