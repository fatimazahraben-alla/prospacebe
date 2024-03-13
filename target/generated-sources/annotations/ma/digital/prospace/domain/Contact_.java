package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Contact.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Contact_ {

	
	/**
	 * @see ma.digital.prospace.domain.Contact#mail
	 **/
	public static volatile SingularAttribute<Contact, String> mail;
	
	/**
	 * @see ma.digital.prospace.domain.Contact#deviceOS
	 **/
	public static volatile SingularAttribute<Contact, String> deviceOS;
	
	/**
	 * @see ma.digital.prospace.domain.Contact#telephone
	 **/
	public static volatile SingularAttribute<Contact, String> telephone;
	
	/**
	 * @see ma.digital.prospace.domain.Contact#id
	 **/
	public static volatile SingularAttribute<Contact, Long> id;
	
	/**
	 * @see ma.digital.prospace.domain.Contact#deviceVersion
	 **/
	public static volatile SingularAttribute<Contact, String> deviceVersion;
	
	/**
	 * @see ma.digital.prospace.domain.Contact#comptePro
	 **/
	public static volatile SingularAttribute<Contact, ComptePro> comptePro;
	
	/**
	 * @see ma.digital.prospace.domain.Contact
	 **/
	public static volatile EntityType<Contact> class_;
	
	/**
	 * @see ma.digital.prospace.domain.Contact#deviceToken
	 **/
	public static volatile SingularAttribute<Contact, String> deviceToken;

	public static final String MAIL = "mail";
	public static final String DEVICE_OS = "deviceOS";
	public static final String TELEPHONE = "telephone";
	public static final String ID = "id";
	public static final String DEVICE_VERSION = "deviceVersion";
	public static final String COMPTE_PRO = "comptePro";
	public static final String DEVICE_TOKEN = "deviceToken";

}

