package com.moontech.salesPoint.commons.constant;

/**
 * Constantes para la base de datos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 dic, 2023
 */
public abstract class DatabaseConstant {
  /** Propiedad para el nombre de la tabla "users". */
  public static final String USERS_TABLE = "users";

  /** Propiedad para el campo "create_user". */
  public static final String CREATE_USER = "created_user";

  /** Propiedad para el campo "create_date". */
  public static final String CREATE_DATE = "created_date";

  /** Propiedad para el campo "last modified_user". */
  public static final String LAST_MODIFIED_USER = "last_modified_user";

  /** Propiedad para el campo "last modified_date". */
  public static final String LAST_MODIFIED_DATE = "last_modified_date";

  /** Propiedad para primer nombre. */
  public static final String FIRST_NAME_PROPERTY = "first_name";

  /** Propiedad para el segundo nombre. */
  public static final String LAST_NAME_PROPERTY = "last_name";

  /** Propiedad para nombre de usuario. */
  public static final String USERNAME_PROPERTY = "username";

  /** Propiedad para contraseña. */
  public static final String PASSWORD_PROPERTY = "password";

  /** Propiedad para el correo. */
  public static final String EMAIL_PROPERTY = "email_address";

  /** Propiedad para el número de celular. */
  public static final String CELLPHONE_PROPERTY = "cellphone";

  /** Nombre de la tabla "roles". */
  public static final String TABLE_ROLES = "roles";

  /** Propiedad para el nombre del rol. */
  public static final String PROPERTY_ROLE_NAME = "name";

  /** Propiedad para el valor del rol. */
  public static final String PROPERTY_ROLE_VALUE = "value";

  /** Propiedad para la relación usuarios - roles. */
  public static final String RELATION_USER_ROLES_NAME = "user_roles";

  /** Propiedad para el identificador del rol. */
  public static final String PROPERTY_ROL_ID = "id_role";

  /** Propiedad para el identificador del usuario. */
  public static final String PROPERTY_USER_ID = "id_user";

  /** Propiedad para el status. */
  public static final String STATUS_PROPERTY = "status";

  /** Nombre de la tabla "empresas". */
  public static final String TABLE_ENTERPRISES = "enterprises";

  /** Propiedad para el identificador de la empresa. */
  public static final String PROPERTY_ENTERPRISE_ID = "id_enterprise";

  /** Propiedad para el nombre de la empresa. */
  public static final String PROPERTY_OFFICE_NAME = "name";

  /** Propiedad para el status de la empresa. */
  public static final String PROPERTY_OFFICE_STATUS = "status";

  /** Propiedad para la relación empresa - usuarios. */
  public static final String RELATION_ENTERPRISE_USERS_NAME = "enterprise";

  /** Propiedad para la dirección. */
  public static final String PROPERTY_ADDRESS = "address";

  /** Propiedad para el teléfono. */
  public static final String PROPERTY_PHONE = "phone";

  /** Propiedad para el encargado de la empresa. */
  public static final String PROPERTY_OFFICE_MANAGER = "manager";

  /** Propiedad para el identificador de la sucursal del usuario. */
  public static final String PROPERTY_USER_ENTERPRISE = "id";

  /** Constructor privado. */
  private DatabaseConstant() {}
}
