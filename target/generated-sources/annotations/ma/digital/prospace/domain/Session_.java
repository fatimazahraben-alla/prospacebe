package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Session.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Session_ {

	
	/**
	 * @see ma.digital.prospace.domain.Session#CreatedAt
	 **/
	public static volatile SingularAttribute<Session, Date> CreatedAt;
	
	/**
	 * @see ma.digital.prospace.domain.Session#association
	 **/
	public static volatile SingularAttribute<Session, Association> association;
	
	/**
	 * @see ma.digital.prospace.domain.Session#id
	 **/
	public static volatile SingularAttribute<Session, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Session
	 **/
	public static volatile EntityType<Session> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Session#JsonData
	 **/
	public static volatile SingularAttribute<Session, String> JsonData;
	
	/**
	 * @see ma.digital.prospace.domain.Session#TransactionId
	 **/
	public static volatile SingularAttribute<Session, Long> TransactionId;

	public static final String CREATED_AT = "CreatedAt";
	public static final String ASSOCIATION = "association";
	public static final String ID = "id";
	public static final String JSON_DATA = "JsonData";
	public static final String TRANSACTION_ID = "TransactionId";

}

