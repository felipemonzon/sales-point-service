package com.moontech.salespoint.infrastructure.security.utility;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
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

  /**
   * Obtiene la llave para el jwt.
   *
   * @param secretKey llave secreta
   * @return llave usada en el jwt
   */
  public static SecretKey getSigningKey(String secretKey) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /** Constructor privado. */
  private SecurityUtilities() {}
}
