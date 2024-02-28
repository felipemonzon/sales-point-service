package com.moontech.salespoint.infrastructure.security.utility;

import com.moontech.salespoint.commons.constant.ApiConstant;
import com.moontech.salespoint.infrastructure.security.constant.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
   * Genera el token con roles, issuer, fecha, expiración (8 h).
   *
   * @param authentication {@code Authentication}
   * @param signingKey llave para descifrar o cifrar token
   * @param validity time to expired token
   * @return token generado
   */
  public static String generateToken(
      Authentication authentication, String signingKey, long validity) {
    final String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    return Jwts.builder()
        .subject(authentication.getName())
        .claim(SecurityConstants.AUTHORITIES_KEY, authorities)
        .signWith(SecurityUtilities.getSigningKey(signingKey), Jwts.SIG.HS512)
        .issuedAt(new Date(System.currentTimeMillis()))
        .issuer(SecurityConstants.ISSUER_TOKEN)
        .expiration(new Date(System.currentTimeMillis() + validity))
        .compact();
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

  /**
   * Obtiene el token de la cabecera.
   *
   * @param authorizationHeader token proporcionado en la cabecera
   * @return token
   */
  public static String getTokenByHeader(final String authorizationHeader) {
    return authorizationHeader.replace(
        SecurityConstants.TOKEN_BEARER_PREFIX + ApiConstant.WHITE_SPACE, StringUtils.EMPTY);
  }

  /**
   * Obtiene el usuario del token.
   *
   * @param token token recibido en la petición
   * @param jwtKey llave del token
   * @return usuario
   */
  public static String getUserName(final String token, final String jwtKey) {
    final JwtParser jwtParser =
        Jwts.parser().verifyWith(SecurityUtilities.getSigningKey(jwtKey)).build();
    final Jws<Claims> claimsJws = jwtParser.parseSignedClaims(token);
    return claimsJws.getPayload().getSubject();
  }

  /***
   * Obtiene los datos de autenticación.
   *
   * @param token token recibido en la petición
   * @param userDetails {@link UserDetails}
   * @param jwtKey llave del token
   * @return {@link UsernamePasswordAuthenticationToken}
   */
  public static UsernamePasswordAuthenticationToken getAuthentication(
      final String token, final UserDetails userDetails, final String jwtKey) {
    final JwtParser jwtParser =
        Jwts.parser().verifyWith(SecurityUtilities.getSigningKey(jwtKey)).build();
    final Jws<Claims> claimsJws = jwtParser.parseSignedClaims(token);
    final Claims claims = claimsJws.getPayload();
    final Collection<SimpleGrantedAuthority> authorities =
        Arrays.stream(
                claims.get(SecurityConstants.AUTHORITIES_KEY).toString().split(ApiConstant.COMMA))
            .map(SimpleGrantedAuthority::new)
            .toList();
    return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, authorities);
  }

  /** Constructor privado. */
  private SecurityUtilities() {}
}
