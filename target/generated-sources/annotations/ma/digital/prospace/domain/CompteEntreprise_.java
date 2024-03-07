package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CompteEntreprise.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class CompteEntreprise_ {

	
	/**
	 * @see ma.digital.prospace.domain.CompteEntreprise#entreprise
	 **/
	public static volatile SingularAttribute<CompteEntreprise, Entreprise> entreprise;
	
	/**
	 * @see ma.digital.prospace.domain.CompteEntreprise#roles
	 **/
	public static volatile SetAttribute<CompteEntreprise, String> roles;
	
	/**
	 * @see ma.digital.prospace.domain.CompteEntreprise#id
	 **/
	public static volatile SingularAttribute<CompteEntreprise, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.CompteEntreprise
	 **/
	public static volatile EntityType<CompteEntreprise> class_;
	
	/**
	 * @see ma.digital.prospace.domain.CompteEntreprise#compte
	 **/
	public static volatile SingularAttribute<CompteEntreprise, ComptePro> compte;

	public static final String ENTREPRISE = "entreprise";
	public static final String ROLES = "roles";
	public static final String ID = "id";
	public static final String COMPTE = "compte";

}

