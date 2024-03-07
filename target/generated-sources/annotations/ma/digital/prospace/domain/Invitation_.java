package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import ma.digital.prospace.domain.Invitation.StatutInvitation;

@StaticMetamodel(Invitation.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Invitation_ {

	
	/**
	 * @see ma.digital.prospace.domain.Invitation#dateCreation
	 **/
	public static volatile SingularAttribute<Invitation, Date> dateCreation;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation#typeIdentifiantTo
	 **/
	public static volatile SingularAttribute<Invitation, String> typeIdentifiantTo;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation#identifiantTo
	 **/
	public static volatile SingularAttribute<Invitation, String> identifiantTo;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation#id
	 **/
	public static volatile SingularAttribute<Invitation, String> id;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation#dateFin
	 **/
	public static volatile SingularAttribute<Invitation, Date> dateFin;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation
	 **/
	public static volatile EntityType<Invitation> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation#token
	 **/
	public static volatile SingularAttribute<Invitation, String> token;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation#object
	 **/
	public static volatile SingularAttribute<Invitation, String> object;
	
	/**
	 * @see ma.digital.prospace.domain.Invitation#status
	 **/
	public static volatile SingularAttribute<Invitation, StatutInvitation> status;

	public static final String DATE_CREATION = "dateCreation";
	public static final String TYPE_IDENTIFIANT_TO = "typeIdentifiantTo";
	public static final String IDENTIFIANT_TO = "identifiantTo";
	public static final String ID = "id";
	public static final String DATE_FIN = "dateFin";
	public static final String TOKEN = "token";
	public static final String OBJECT = "object";
	public static final String STATUS = "status";

}

