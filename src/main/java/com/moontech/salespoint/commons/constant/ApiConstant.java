package com.moontech.salespoint.commons.constant;

/**
 * Constantes para la aplicación.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 dec., 2023
 */
public abstract class ApiConstant {
  /** Constante para el símbolo de la coma. */
  public static final String COMMA = ",";

  /** Constante para el header uuid. */
  public static final String HEADER_UUID = "uuid";

  /** Constante para mostrar el inicio de la petición. */
  public static final String START_REQUEST = "Inicia Llamado [{}]";

  /** Constante para la llave req.time. */
  public static final String TIME_REQ_ATTRIBUTE = "req.time";

  /** Constante usada como llave para el atributo uuid header. */
  public static final String UUID_MDC_LABEL = "mdc.uuid";

  /** Constante para mostrar el tiempo de petición y respuesta. */
  public static final String TIME_ELAPSED_MESSAGE =
      "Time elapsed for request round trip [{}]: {} ms";

  /** Prefijo para las propiedades de la aplicación. */
  public static final String PROPERTY_PREFIX = "api";

  /** Prefijo para las propiedades de swagger. */
  public static final String SWAGGER_PROPERTIES = "swagger";

  /** Espacio en blanco. */
  public static final String WHITE_SPACE = " ";

  /** Usuario de sistema. */
  public static final String USER_SYSTEM = "System";

  /** Propiedades para el correo. */
  public static final String PROPERTIES_MAIL = "api.mail";

  /** Propiedad para la url de confirmación vía email. */
  public static final String MAIL_URL_PROPERTY = "url";

  /** Teléfono del destinatario del correo. */
  public static final String MAIL_PHONE_PROPERTY = "phone";

  /** Usuario del destinatario del correo. */
  public static final String MAIL_USER_PROPERTY = "user";

  /** Nombre de la empresa. */
  public static final String MAIL_ENTERPRISE_NAME_PROPERTY = "enterprise";

  /** propiedad paras primer nombre. */
  public static final String MAIL_FIRSTNAME_PROPERTY = "firstname";

  /** Propiedad para segundo nombre. */
  public static final String MAIL_LASTNAME_PROPERTY = "lastname";

  /** Constructor privado. */
  private ApiConstant() {}
}
