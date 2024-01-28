package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Datos de la petición para tipos de productos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 dec., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductTypeRequest {
  /** Identificador del tipo de producto. */
  private Long id;

  /** Propiedad para el nombre del tipo de producto. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_LETTERS_PATTERN)
  private String name;
}
