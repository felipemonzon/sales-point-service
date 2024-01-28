package com.moontech.salespoint.infrastructure.security.config;

import com.moontech.salespoint.infrastructure.property.SecurityProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Propiedades externalizadas de seguridad.
 *
 * @author Felipe Monzón
 * @since May 30, 2023
 */
@Configuration
public class SecurityPropertyConfiguration {
  /** Llave del JWT. */
  @Value("${security.jwt.key}")
  private String signingKey;
  /** Tiempo de vida del token. */
  @Value("${security.jwt.lifeTime}")
  private int validity;
  /** Ruta para la autenticación de usuarios. */
  @Value("${api.uri.data.authentication}")
  private String userAuthenticationPath;
  /** Ruta para la confirmación de usuarios. */
  @Value("${api.uri.data.confirm}")
  private String userConfirmTokenPath;
  /** Ruta para resetear la contraseña del usuario. */
  @Value("${api.uri.data.passwordForgot}")
  private String passwordForgotPath;

  /**
   * Carga las variables externalizadas en modelo.
   *
   * @return {@code SecurityProperties}
   */
  @Bean
  public SecurityProperties loadSecurityProperties() {
    return SecurityProperties.builder()
        .userAuthenticationPath(this.userAuthenticationPath)
        .jwtKey(this.signingKey)
        .jwtLifeTime(this.validity)
        .userConfirmTokenPath(this.userConfirmTokenPath)
        .forgotPasswordPath(this.passwordForgotPath)
        .build();
  }
}
