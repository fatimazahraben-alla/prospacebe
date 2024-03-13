package ma.digital.prospace.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class User_ extends ma.digital.prospace.domain.AbstractAuditingEntity_ {

	
	/**
	 * @see ma.digital.prospace.domain.User#firstName
	 **/
	public static volatile SingularAttribute<User, String> firstName;
	
	/**
	 * @see ma.digital.prospace.domain.User#lastName
	 **/
	public static volatile SingularAttribute<User, String> lastName;
	
	/**
	 * @see ma.digital.prospace.domain.User#langKey
	 **/
	public static volatile SingularAttribute<User, String> langKey;
	
	/**
	 * @see ma.digital.prospace.domain.User#imageUrl
	 **/
	public static volatile SingularAttribute<User, String> imageUrl;
	
	/**
	 * @see ma.digital.prospace.domain.User#id
	 **/
	public static volatile SingularAttribute<User, String> id;
	
	/**
	 * @see ma.digital.prospace.domain.User#login
	 **/
	public static volatile SingularAttribute<User, String> login;
	
	/**
	 * @see ma.digital.prospace.domain.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see ma.digital.prospace.domain.User#email
	 **/
	public static volatile SingularAttribute<User, String> email;
	
	/**
	 * @see ma.digital.prospace.domain.User#authorities
	 **/
	public static volatile SetAttribute<User, Authority> authorities;
	
	/**
	 * @see ma.digital.prospace.domain.User#activated
	 **/
	public static volatile SingularAttribute<User, Boolean> activated;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String LANG_KEY = "langKey";
	public static final String IMAGE_URL = "imageUrl";
	public static final String ID = "id";
	public static final String LOGIN = "login";
	public static final String EMAIL = "email";
	public static final String AUTHORITIES = "authorities";
	public static final String ACTIVATED = "activated";

}

