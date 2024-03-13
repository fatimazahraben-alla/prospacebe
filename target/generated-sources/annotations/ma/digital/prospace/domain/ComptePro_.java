package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import ma.digital.prospace.domain.enumeration.StatutCompte;

@StaticMetamodel(ComptePro.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ComptePro_ {

	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#associations
	 **/
	public static volatile SetAttribute<ComptePro, Association> associations;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#createdAt
	 **/
	public static volatile SingularAttribute<ComptePro, Instant> createdAt;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#deleted
	 **/
	public static volatile SingularAttribute<ComptePro, Boolean> deleted;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#mandataires
	 **/
	public static volatile SetAttribute<ComptePro, Procuration> mandataires;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#mandants
	 **/
	public static volatile SetAttribute<ComptePro, Procuration> mandants;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#identifiant
	 **/
	public static volatile SingularAttribute<ComptePro, String> identifiant;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#id
	 **/
	public static volatile SingularAttribute<ComptePro, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro
	 **/
	public static volatile EntityType<ComptePro> class_;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#statut
	 **/
	public static volatile SingularAttribute<ComptePro, StatutCompte> statut;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#entrepriseGeree
	 **/
	public static volatile SingularAttribute<ComptePro, Entreprise> entrepriseGeree;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#updatedAt
	 **/
	public static volatile SingularAttribute<ComptePro, Instant> updatedAt;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#deviceToken
	 **/
	public static volatile SingularAttribute<ComptePro, String> deviceToken;

	public static final String ASSOCIATIONS = "associations";
	public static final String CREATED_AT = "createdAt";
	public static final String DELETED = "deleted";
	public static final String MANDATAIRES = "mandataires";
	public static final String MANDANTS = "mandants";
	public static final String IDENTIFIANT = "identifiant";
	public static final String ID = "id";
	public static final String STATUT = "statut";
	public static final String ENTREPRISE_GEREE = "entrepriseGeree";
	public static final String UPDATED_AT = "updatedAt";
	public static final String DEVICE_TOKEN = "deviceToken";

}

