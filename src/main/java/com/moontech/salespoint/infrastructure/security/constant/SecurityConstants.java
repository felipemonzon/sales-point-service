package com.moontech.salespoint.infrastructure.security.constant;

/**
 * Constantes para la configuración de seguridad del servicio.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 25 nov., 2023
 */
public abstract class SecurityConstants {
  /** LLava e de autorización. */
  public static final String AUTHORITIES_KEY = "CLAIM_TOKEN";

  /** Token del propietario. */
  public static final String ISSUER_TOKEN = "ISSUER";

  /** Token bearer. */
  public static final String TOKEN_BEARER_PREFIX = "Bearer";

  /** Permite solo perfil administrador. */
  public static final String ADMIN_ALLOWED = "hasRole('ADMIN')";

  /** Permite perfiles de administrador y cliente. */
  public static final String ADMIN_OR_CUSTOMER_ALLOWED = "hasRole('ADMIN') or hasRole('CUSTOMER')";

  /** Prefijo para guardar un rol. */
  public static final String ROLE_PREFIX = "ROLE_";

  private SecurityConstants() {}
}
