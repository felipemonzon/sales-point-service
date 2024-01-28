package com.moontech.salespoint.infrastructure.security.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utilería para seguridad.
 *
 * @author Felipe Monzón
 * @since 22 jun., 2023
 */
public class SecurityUtilities {
  /**
   * Encripta la contraseña.
   *
   * @param password contraseña a encriptar
   * @return contraseña encriptada
   */
  public static String passwordEncoder(final String password) {
    return new BCryptPasswordEncoder().encode(password);
  }

  /** Constructor privado. */
  private SecurityUtilities() {}
}
