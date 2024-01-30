package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.enums.Genre;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.validation.constraints.*;
import java.util.Set;
import lombok.*;

/**
 * Datos de entrada de la api de usuarios.
 *
 * @author Felipe Monzón
 * @since 22 jun., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequest {
  /** Identificador de sistema. */
  private Long id;

  /** Identificador del usuario. */
  private String idUser;

  /** Nombre de usuario. */
  @NotEmpty
  @Size(max = 100)
  @Pattern(regexp = FormatConstant.USERNAME_PATTERN)
  private String username;

  /** Contraseña del usuario. */
  @NotBlank
  @Size(max = 100)
  @Pattern(regexp = FormatConstant.PAW_PATTERN)
  private String password;

  /** Correo del usuario. */
  @Email
  @NotEmpty
  @Size(max = 100)
  private String email;

  /** Propiedad primer nombre. */
  @NotEmpty
  @Size(max = 100)
  @Pattern(regexp = FormatConstant.ONLY_LETTERS_PATTERN)
  private String firstName;

  /** Propiedad segundo nombre. */
  @NotEmpty
  @Size(max = 100)
  @Pattern(regexp = FormatConstant.ONLY_LETTERS_PATTERN)
  private String lastName;

  /** Propiedad para el celular. */
  @NotEmpty
  @Size(min = 10, max = 10)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_PATTERN)
  private String cel;

  /** Propiedad para el género. */
  @NotNull private Genre genre;

  /** Identificador de la empresa. */
  @Min(1)
  @NotNull
  private Long enterpriseId;

  /** Roles del usuario. */
  @NotNull private Set<Long> profiles;

  /** Estatus del usuario */
  private Status status = Status.ACTIVE;
}
