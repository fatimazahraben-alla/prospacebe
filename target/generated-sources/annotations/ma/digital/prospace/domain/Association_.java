package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import ma.digital.prospace.domain.enumeration.StatutAssociation;

@StaticMetamodel(Association.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Association_ {

	
	/**
	 * @see ma.digital.prospace.domain.Association#entreprise
	 **/
	public static volatile SingularAttribute<Association, Entreprise> entreprise;
	
	/**
	 * @see ma.digital.prospace.domain.Association#dateEffet
	 **/
	public static volatile SingularAttribute<Association, Instant> dateEffet;
	
	/**
	 * @see ma.digital.prospace.domain.Association#role
	 **/
	public static volatile SingularAttribute<Association, Rolee> role;
	
	/**
	 * @see ma.digital.prospace.domain.Association#mail
	 **/
	public static volatile SingularAttribute<Association, String> mail;
	
	/**
	 * @see ma.digital.prospace.domain.Association#telephone
	 **/
	public static volatile SingularAttribute<Association, String> telephone;
	
	/**
	 * @see ma.digital.prospace.domain.Association#id
	 **/
	public static volatile SingularAttribute<Association, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Association#dateFin
	 **/
	public static volatile SingularAttribute<Association, Instant> dateFin;
	
	/**
	 * @see ma.digital.prospace.domain.Association
	 **/
	public static volatile EntityType<Association> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Association#fs
	 **/
	public static volatile SingularAttribute<Association, Long> fs;
	
	/**
	 * @see ma.digital.prospace.domain.Association#statut
	 **/
	public static volatile SingularAttribute<Association, StatutAssociation> statut;
	
	/**
	 * @see ma.digital.prospace.domain.Association#compte
	 **/
	public static volatile SingularAttribute<Association, ComptePro> compte;

	public static final String ENTREPRISE = "entreprise";
	public static final String DATE_EFFET = "dateEffet";
	public static final String ROLE = "role";
	public static final String MAIL = "mail";
	public static final String TELEPHONE = "telephone";
	public static final String ID = "id";
	public static final String DATE_FIN = "dateFin";
	public static final String FS = "fs";
	public static final String STATUT = "statut";
	public static final String COMPTE = "compte";

}

