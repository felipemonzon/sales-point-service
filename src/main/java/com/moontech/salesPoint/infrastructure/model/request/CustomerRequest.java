package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.enums.Genre;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Datos con la petición de cliente
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerRequest implements Serializable {
  /** Identificador del usuario. */
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  private String idCustomer;

  /** Propiedad primer nombre. */
  @NotBlank
  @Pattern(regexp = FormatConstant.ONLY_LETTERS_PATTERN)
  private String firstName;

  /** Propiedad para el apellido. */
  @NotBlank
  @Pattern(regexp = FormatConstant.ONLY_LETTERS_PATTERN)
  private String lastName;

  /** Propiedad para el correo. */
  @NotBlank @Email private String email;

  /** Propiedad para el celular. */
  @NotBlank
  @Size(min = 10, max = 10)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_PATTERN)
  private String cel;

  /** Propiedad para el género. */
  @NotNull private Genre genre;
}
