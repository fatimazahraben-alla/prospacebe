package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Procuration.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Procuration_ {

	
	/**
	 * @see ma.digital.prospace.domain.Procuration#dateEffet
	 **/
	public static volatile SingularAttribute<Procuration, Date> dateEffet;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration#id
	 **/
	public static volatile SingularAttribute<Procuration, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration#dateFin
	 **/
	public static volatile SingularAttribute<Procuration, Date> dateFin;
	
	/**
	 * @see ma.digital.prospace.domain.Procuration
	 **/
	public static volatile EntityType<Procuration> class_;

	public static final String DATE_EFFET = "dateEffet";
	public static final String ID = "id";
	public static final String DATE_FIN = "dateFin";

}

