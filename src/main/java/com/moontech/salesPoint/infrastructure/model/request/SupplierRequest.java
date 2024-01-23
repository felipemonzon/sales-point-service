package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Objeto de entrada de la API de proveedores.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SupplierRequest {
  /** Identificador del proveedor. */
  private Long id;

  /** Nombre del proveedor. */
  @NotBlank
  @Size(max = 50)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String manager;

  /** Propiedad para la empresa. */
  @NotBlank
  @Size(max = 50)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String enterprise;

  /** Propiedad para el RFC. */
  @NotBlank
  @Size(min = 12, max = 18)
  private String rfc;

  /** Dirección del proveedor. */
  @NotBlank
  @Size(max = 50)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String address;

  /** Teléfono del proveedor. */
  @NotBlank
  @Size(min = 10, max = 10)
  private String phone;

  /** Comentarios. */
  @NotNull
  @Size(max = 200)
  private String comments;

  /** Status del proveedor. */
  @Builder.Default private Status status = Status.ACTIVE;
}
