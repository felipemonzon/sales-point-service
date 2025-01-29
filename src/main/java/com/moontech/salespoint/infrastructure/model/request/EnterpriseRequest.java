package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO para la API de empresas.
 *
 * @author Felipe Monzón
 * @since 12 jun., 2023
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EnterpriseRequest {
  /** Identificador de la empresa. */
  private Long id;

  /** Identificador de la empresa. */
  private String idEnterprise;

  /** Nombre de la empresa. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String name;

  /** RFC de la empresa. */
  @NotEmpty
  @Size(max = 20)
  private String rfc;

  /** Dirección de la empresa. */
  @NotEmpty private String address;

  /** Teléfono de la empresa. */
  @NotEmpty
  @Size(min = 10, max = 10)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_PATTERN)
  private String phone;

  /** Status de la empresa. */
  private Boolean active;

  /** Encargado de la empresa. */
  @NotEmpty private String manager;
}
