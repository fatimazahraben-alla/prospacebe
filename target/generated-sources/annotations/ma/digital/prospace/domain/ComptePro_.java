package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import ma.digital.prospace.domain.enumeration.StatutCompte;

@StaticMetamodel(ComptePro.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ComptePro_ {

	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#associations
	 **/
	public static volatile SetAttribute<ComptePro, Association> associations;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#mail
	 **/
	public static volatile SingularAttribute<ComptePro, String> mail;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#identifiant
	 **/
	public static volatile SingularAttribute<ComptePro, String> identifiant;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#photo
	 **/
	public static volatile SingularAttribute<ComptePro, byte[]> photo;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#telephone
	 **/
	public static volatile SingularAttribute<ComptePro, String> telephone;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#prenomFr
	 **/
	public static volatile SingularAttribute<ComptePro, String> prenomFr;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#typeIdentifiant
	 **/
	public static volatile SingularAttribute<ComptePro, String> typeIdentifiant;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#mandataires
	 **/
	public static volatile SetAttribute<ComptePro, Procuration> mandataires;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#mandants
	 **/
	public static volatile SetAttribute<ComptePro, Procuration> mandants;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#contact
	 **/
	public static volatile SingularAttribute<ComptePro, Contact> contact;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#adresse
	 **/
	public static volatile SingularAttribute<ComptePro, String> adresse;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#nomFr
	 **/
	public static volatile SingularAttribute<ComptePro, String> nomFr;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#id
	 **/
	public static volatile SingularAttribute<ComptePro, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro
	 **/
	public static volatile EntityType<ComptePro> class_;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#photoContentType
	 **/
	public static volatile SingularAttribute<ComptePro, String> photoContentType;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#statut
	 **/
	public static volatile SingularAttribute<ComptePro, StatutCompte> statut;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#entrepriseGeree
	 **/
	public static volatile SingularAttribute<ComptePro, Entreprise> entrepriseGeree;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#nomAr
	 **/
	public static volatile SingularAttribute<ComptePro, String> nomAr;
	
	/**
	 * @see ma.digital.prospace.domain.ComptePro#prenomAr
	 **/
	public static volatile SingularAttribute<ComptePro, String> prenomAr;

	public static final String ASSOCIATIONS = "associations";
	public static final String MAIL = "mail";
	public static final String IDENTIFIANT = "identifiant";
	public static final String PHOTO = "photo";
	public static final String TELEPHONE = "telephone";
	public static final String PRENOM_FR = "prenomFr";
	public static final String TYPE_IDENTIFIANT = "typeIdentifiant";
	public static final String MANDATAIRES = "mandataires";
	public static final String MANDANTS = "mandants";
	public static final String CONTACT = "contact";
	public static final String ADRESSE = "adresse";
	public static final String NOM_FR = "nomFr";
	public static final String ID = "id";
	public static final String PHOTO_CONTENT_TYPE = "photoContentType";
	public static final String STATUT = "statut";
	public static final String ENTREPRISE_GEREE = "entrepriseGeree";
	public static final String NOM_AR = "nomAr";
	public static final String PRENOM_AR = "prenomAr";

}

