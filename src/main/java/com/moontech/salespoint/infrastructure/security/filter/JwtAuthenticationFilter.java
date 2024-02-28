package com.moontech.salespoint.infrastructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.commons.constant.ApiConstant;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.constant.LogConstant;
import com.moontech.salespoint.infrastructure.exception.custom.ErrorResponse;
import com.moontech.salespoint.infrastructure.exception.custom.ForbiddenException;
import com.moontech.salespoint.infrastructure.exception.management.ExceptionManagement;
import com.moontech.salespoint.infrastructure.model.request.AuthorizationRequest;
import com.moontech.salespoint.infrastructure.model.response.LoginResponse;
import com.moontech.salespoint.infrastructure.security.constant.SecurityConstants;
import com.moontech.salespoint.infrastructure.security.utility.SecurityUtilities;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Autenticación para el usuario.
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
          || !userCredentials.getPassword().matches(FormatConstant.PAW_PATTERN)) {
        log.error("Usuario o contraseña no cumplen con las características.");
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

    String token = SecurityUtilities.generateToken(authResult, signingKey, validity);
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
   * Resuelve variables de spring placeholder.
   *
   * @return {@code PropertySourcesPlaceholderConfigurer}
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
