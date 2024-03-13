package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Entreprise.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Entreprise_ {

	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#associations
	 **/
	public static volatile SetAttribute<Entreprise, Association> associations;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#id
	 **/
	public static volatile SingularAttribute<Entreprise, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise
	 **/
	public static volatile EntityType<Entreprise> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#etat
	 **/
	public static volatile SingularAttribute<Entreprise, String> etat;
	
	/**
	 * @see ma.digital.prospace.domain.Entreprise#gerants
	 **/
	public static volatile SetAttribute<Entreprise, ComptePro> gerants;

	public static final String ASSOCIATIONS = "associations";
	public static final String ID = "id";
	public static final String ETAT = "etat";
	public static final String GERANTS = "gerants";

}

