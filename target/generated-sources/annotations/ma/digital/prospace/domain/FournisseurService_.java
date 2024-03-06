package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FournisseurService.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class FournisseurService_ {

	
	/**
	 * @see ma.digital.prospace.domain.FournisseurService#roles
	 **/
	public static volatile SetAttribute<FournisseurService, Rolee> roles;
	
	/**
	 * @see ma.digital.prospace.domain.FournisseurService#description
	 **/
	public static volatile SingularAttribute<FournisseurService, String> description;
	
	/**
	 * @see ma.digital.prospace.domain.FournisseurService#id
	 **/
	public static volatile SingularAttribute<FournisseurService, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.FournisseurService
	 **/
	public static volatile EntityType<FournisseurService> class_;
	
	/**
	 * @see ma.digital.prospace.domain.FournisseurService#nom
	 **/
	public static volatile SingularAttribute<FournisseurService, String> nom;

	public static final String ROLES = "roles";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String NOM = "nom";

}

