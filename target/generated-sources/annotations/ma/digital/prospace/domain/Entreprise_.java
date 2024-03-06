package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

@StaticMetamodel(Entreprise.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Entreprise_ {

	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#dateImmatriculation
	 **/
	public static volatile SingularAttribute<Entreprise, Instant> dateImmatriculation;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#associations
	 **/
	public static volatile SetAttribute<Entreprise, Association> associations;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#formeJuridique
	 **/
	public static volatile SingularAttribute<Entreprise, String> formeJuridique;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#ice
	 **/
	public static volatile SingularAttribute<Entreprise, String> ice;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#tribunal
	 **/
	public static volatile SingularAttribute<Entreprise, String> tribunal;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#etat
	 **/
	public static volatile SingularAttribute<Entreprise, String> etat;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#denomination
	 **/
	public static volatile SingularAttribute<Entreprise, String> denomination;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#gerants
	 **/
	public static volatile SetAttribute<Entreprise, ComptePro> gerants;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#activite
	 **/
	public static volatile SingularAttribute<Entreprise, String> activite;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#numeroRC
	 **/
	public static volatile SingularAttribute<Entreprise, String> numeroRC;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#id
	 **/
	public static volatile SingularAttribute<Entreprise, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise
	 **/
	public static volatile EntityType<Entreprise> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#statutJuridique
	 **/
	public static volatile SingularAttribute<Entreprise, String> statutJuridique;

	public static final String DATE_IMMATRICULATION = "dateImmatriculation";
	public static final String ASSOCIATIONS = "associations";
	public static final String FORME_JURIDIQUE = "formeJuridique";
	public static final String ICE = "ice";
	public static final String TRIBUNAL = "tribunal";
	public static final String ETAT = "etat";
	public static final String DENOMINATION = "denomination";
	public static final String GERANTS = "gerants";
	public static final String ACTIVITE = "activite";
	public static final String NUMERO_RC = "numeroRC";
	public static final String ID = "id";
	public static final String STATUT_JURIDIQUE = "statutJuridique";

}

