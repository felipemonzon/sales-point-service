package com.moontech.salesPoint.infrastructure.security.config;

import com.moontech.salesPoint.infrastructure.property.SecurityProperties;
import com.moontech.salesPoint.infrastructure.security.filter.JwtAuthenticationFilter;
import com.moontech.salesPoint.infrastructure.security.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuración para spring security.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 30 may., 2023
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
  /** Constante para permitir multiples conexiones. */
  private static final String ASTERISK = "*";

  /** Propiedades de seguridad {@code SecurityProperties}. */
  private final SecurityProperties securityProperties;

  /** {@code UserDetailsService} */
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize ->
                authorize.requestMatchers(WHITELIST).permitAll().anyRequest().authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(
            new JwtAuthenticationFilter(
                this.authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
                this.securityProperties.getJwtKey(),
                this.securityProperties.getJwtLifeTime(),
                new AntPathRequestMatcher(
                    this.securityProperties.getUserAuthenticationPath(), HttpMethod.POST.name())),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(
            this.jwtAuthorizationFilterBean(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * Inicia el filtro para la autorización basada de token.
   *
   * @return filtro del token
   */
  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilterBean() {
    return new JwtAuthorizationFilter();
  }

  /**
   * Used by spring security if CORS is enabled.
   *
   * @return {@code CorsFilter}
   */
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin(ASTERISK);
    config.addAllowedHeader(ASTERISK);
    config.setAllowedMethods(
        Arrays.asList(
            HttpMethod.POST.name(),
            HttpMethod.GET.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()));
    config.setExposedHeaders(Collections.singletonList(ASTERISK));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  /** Lista blanca de end points. */
  private static final String[] WHITELIST = {
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/health",
    "/actuator/health/**",
    "/actuator/info/**",
    "/actuator/prometheus"
  };
}
