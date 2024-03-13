package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Rolee.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Rolee_ {

	
	/**
	 * @see ma.digital.prospace.domain.Rolee#description
	 **/
	public static volatile SingularAttribute<Rolee, String> description;
	
	/**
	 * @see ma.digital.prospace.domain.Rolee#id
	 **/
	public static volatile SingularAttribute<Rolee, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Rolee
	 **/
	public static volatile EntityType<Rolee> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Rolee#nom
	 **/
	public static volatile SingularAttribute<Rolee, String> nom;
	
	/**
	 * @see ma.digital.prospace.domain.Rolee#fs
	 **/
	public static volatile SingularAttribute<Rolee, FournisseurService> fs;

	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String NOM = "nom";
	public static final String FS = "fs";

}

