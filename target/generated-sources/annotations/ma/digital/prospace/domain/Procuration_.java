package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

@StaticMetamodel(Procuration.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Procuration_ {

	
	/**
	 * @see ma.digital.prospace.domain.Procuration#dateEffet
	 **/
	public static volatile SingularAttribute<Procuration, Instant> dateEffet;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration#id
	 **/
	public static volatile SingularAttribute<Procuration, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration#dateFin
	 **/
	public static volatile SingularAttribute<Procuration, Instant> dateFin;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration#gestionnaireEspacePro
	 **/
	public static volatile SingularAttribute<Procuration, ComptePro> gestionnaireEspacePro;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration
	 **/
	public static volatile EntityType<Procuration> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration#utilisateurPro
	 **/
	public static volatile SingularAttribute<Procuration, ComptePro> utilisateurPro;

	public static final String DATE_EFFET = "dateEffet";
	public static final String ID = "id";
	public static final String DATE_FIN = "dateFin";
	public static final String GESTIONNAIRE_ESPACE_PRO = "gestionnaireEspacePro";
	public static final String UTILISATEUR_PRO = "utilisateurPro";

}

