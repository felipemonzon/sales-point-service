package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.enums.Genre;
import com.moontech.salespoint.commons.enums.Status;
import java.io.Serializable;
import lombok.*;

/**
 * Clase para retornar los datos de usuario que inicio sesión.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 25 nov,. 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponse implements Serializable {
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

  /** Nombre completo. */
  private String displayName;

  /** Identificador de la empresa. */
  private Long enterpriseId;

  /** Nombre de la empresa. */
  private String enterpriseName;

  /** Estatus del empleado. */
  private Status status;
}
