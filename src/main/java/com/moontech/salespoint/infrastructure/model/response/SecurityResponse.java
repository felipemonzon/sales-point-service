package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.enums.Genre;
import com.moontech.salespoint.commons.enums.Status;
import java.util.Collection;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Respuesta de inicio de sesión.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 26 nov., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SecurityResponse implements UserDetails {
  /** Identificador del usuario. */
  private Long id;

  /** Nombre de usuario. */
  private String username;

  /** Correo del usuario. */
  private String email;

  /** Propiedad primer nombre. */
  private String firstName;

  /** Propiedad segundo nombre. */
  private String lastName;

  /** Propiedad para el celular. */
  private String cel;

  /** Propiedad para el género. */
  private Genre genre;

  /** Contraseña. */
  private String password;

  /** Habilitado. */
  private Boolean enabled;

  /** Cuenta expirada. */
  private Boolean accountNonExpired;

  /** Cuenta bloqueada. */
  private Boolean accountNonLocked;

  /** Credenciales no expiradas. */
  private boolean credentialsNonExpired;

  /** Perfiles del usuario. */
  private Collection<? extends GrantedAuthority> authorities;

  /** Nombre completo. */
  private String displayName;

  /** Estatus del usuario. */
  private Status status;

  /** Identificador de la empresa. */
  private Long enterpriseId;

  /** Nombre de la empresa. */
  private String enterpriseName;

  /** Perfiles del usuario. */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
