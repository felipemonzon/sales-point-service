package com.moontech.salesPoint.infrastructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salesPoint.commons.constant.ApiConstant;
import com.moontech.salesPoint.commons.constant.ErrorConstant;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.constant.LogConstant;
import com.moontech.salesPoint.infrastructure.exception.custom.ErrorResponse;
import com.moontech.salesPoint.infrastructure.exception.custom.ForbiddenException;
import com.moontech.salesPoint.infrastructure.exception.management.ExceptionManagement;
import com.moontech.salesPoint.infrastructure.model.request.AuthorizationRequest;
import com.moontech.salesPoint.infrastructure.model.response.LoginResponse;
import com.moontech.salesPoint.infrastructure.security.constant.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * *Autenticación para el usuario.
 *
 * @author Felipe Monzón
 * @since May 31, 2023
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  /** Administrador de autenticación. */
  private final AuthenticationManager authenticationManager;

  /** LLave del JWT. */
  private final String signingKey;

  /** Tiempo de validación. */
  private final long validity;

  /**
   * Constructor de la clase.
   *
   * @param authenticationManager {@code AuthenticationManager}
   * @param signingKey llave privada del token
   * @param validity tiempo de validación
   * @param antPathRequestMatcher url para autenticación
   */
  public JwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      String signingKey,
      long validity,
      AntPathRequestMatcher antPathRequestMatcher) {
    this.authenticationManager = authenticationManager;
    this.signingKey = signingKey;
    this.validity = validity;
    super.setRequiresAuthenticationRequestMatcher(antPathRequestMatcher);
    super.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
  }

  /**
   * Valida el token en la autenticación.
   *
   * @param request {@code HttpServletRequest}
   * @param response {@code HttpServletResponse}
   * @return {@code AuthenticationManager}
   * @throws AuthenticationException excepción {@code AuthenticationException}
   */
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      AuthorizationRequest userCredentials =
          new ObjectMapper().readValue(request.getInputStream(), AuthorizationRequest.class);

      if (!userCredentials.getUsername().matches(FormatConstant.USERNAME_PATTERN)
          || !userCredentials.getPassword().matches(FormatConstant.PWD_PATTERN)) {
        log.error("Usuario o contraseña no cumplen con las caracteristicas.");
        throw new BadCredentialsException(ErrorConstant.INVALID_CREDENTIAL_USER_MESSAGE);
      }
      return this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              userCredentials.getUsername(), userCredentials.getPassword()));
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
      throw new BadCredentialsException(ErrorConstant.INVALID_CREDENTIAL_USER_MESSAGE);
    }
  }

  /**
   * Autenticación válida, genera el token.
   *
   * @param request {@code HttpServletRequest}
   * @param response {@code HttpServletResponse}
   * @param chain {@code FilterChain}
   * @param authResult {@code Authentication}
   */
  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException {

    String token = this.generateToken(authResult);
    LoginResponse login = new ModelMapper().map(authResult.getPrincipal(), LoginResponse.class);
    response.addHeader(
        HttpHeaders.AUTHORIZATION,
        SecurityConstants.TOKEN_BEARER_PREFIX + ApiConstant.WHITE_SPACE + token);
    response.getWriter().write(new ObjectMapper().writeValueAsString(login));
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    log.info(LogConstant.LOGIN_SUCCESSFULLY);
  }

  /**
   * Autenticación inválida.
   *
   * @param request {@code HttpServletRequest}
   * @param response {@code HttpServletResponse}
   * @param failed {@code AuthenticationException}
   * @throws IOException excepción {@code IOException}
   */
  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException {
    log.error(failed.getMessage(), failed);
    if (failed instanceof BadCredentialsException
        || failed.getCause() instanceof ForbiddenException) {
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response
          .getWriter()
          .print(
              new ObjectMapper()
                  .writeValueAsString(
                      ErrorResponse.builder()
                          .type(ExceptionManagement.ErrorType.INVALID.name())
                          .code(ErrorConstant.INVALID_CREDENTIAL_USER_CODE)
                          .message(ErrorConstant.INVALID_CREDENTIAL_USER_MESSAGE)
                          .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                          .build()));
    }
  }

  /**
   * Genera el token con roles, issuer, fecha, expiración (8 h).
   *
   * @param authentication {@code Authentication}
   * @return token generado
   */
  private String generateToken(Authentication authentication) {
    final String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(SecurityConstants.AUTHORITIES_KEY, authorities)
        .signWith(SignatureAlgorithm.HS256, this.signingKey)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setIssuer(SecurityConstants.ISSUER_TOKEN)
        .setExpiration(new Date(System.currentTimeMillis() + this.validity * 1000))
        .compact();
  }

  /**
   * Resuelve variables de spring placeholder.
   *
   * @return {@code PropertySourcesPlaceholderConfigurer}
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
