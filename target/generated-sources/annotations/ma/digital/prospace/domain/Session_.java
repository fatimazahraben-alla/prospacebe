package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import ma.digital.prospace.domain.Session.Status;

@StaticMetamodel(Session.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Session_ {

	
	/**
	 * @see ma.digital.prospace.domain.Session#createdAt
	 **/
	public static volatile SingularAttribute<Session, Date> createdAt;
	
	/**
	 * @see ma.digital.prospace.domain.Session#jsonData
	 **/
	public static volatile SingularAttribute<Session, String> jsonData;
	
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
	 * @see ma.digital.prospace.domain.Session#transactionId
	 **/
	public static volatile SingularAttribute<Session, String> transactionId;
	
	/**
	 * @see ma.digital.prospace.domain.Session#status
	 **/
	public static volatile SingularAttribute<Session, Status> status;

	public static final String CREATED_AT = "createdAt";
	public static final String JSON_DATA = "jsonData";
	public static final String ASSOCIATION = "association";
	public static final String ID = "id";
	public static final String TRANSACTION_ID = "transactionId";
	public static final String STATUS = "status";

}

