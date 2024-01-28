package com.moontech.salespoint.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuración para el encriptado de la contraseña.
 *
 * @author Felipe Monzón
 * @since May. 30, 2023
 */
@Configuration
public class PasswordEncoder {
  /**
   * Encriptado de la contraseña.
   *
   * @return {@code BCryptPasswordEncoder}
   */
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
