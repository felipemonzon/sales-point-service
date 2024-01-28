package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.enums.Genre;
import com.moontech.salespoint.commons.enums.Status;
import java.util.Set;
import lombok.*;

/**
 * Respuesta de la API nde usuarios.
 *
 * @author Felipe Monzón
 * @since 21 nov., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {
  /** Identificador del usuario. */
  private String idUser;

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

  /** Nombre completo. */
  private String displayName;

  /** Identificador de la empresa. */
  private Long enterpriseId;

  /** Nombre de la empresa. */
  private String enterpriseName;

  /** Estatus del empleado. */
  private Status status;

  /** perfiles del usuario. */
  private Set<AuthorityResponse> profiles;
}
