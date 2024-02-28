package com.moontech.salespoint.infrastructure.property;

import java.util.List;
import lombok.*;

/**
 * Propiedades externalizadas de seguridad.
 *
 * @author Felipe Monzón
 * @since May 30, 2023
 */
@Getter
@Builder
public class SecurityProperties {
  /** Llave del JWT. */
  private final String jwtKey;

  /** Tiempo de vida del token. */
  private final long jwtLifeTime;

  /** Ruta para la autenticación de usuarios. */
  private final String userAuthenticationPath;

  /** Ruta para la confirmación de usuarios. */
  private String userConfirmTokenPath;

  /** Ruta para olvidar contraseña. */
  private String forgotPasswordPath;

  /** Lista de dominios permitidos. */
  private List<String> cors;
}
