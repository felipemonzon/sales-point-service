package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entrada de la API para el inventario del producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 28 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StockRequest {
  /** Identificador del inventario. */
  private Long id;

  /** Propiedad para el inventario máximo. */
  @Min(1)
  @NotNull
  private Integer stockMax;

  /** Propiedad para el inventario mínimo. */
  @Min(1)
  @NotNull
  private Integer stockMin;

  /** Propiedad para el inventario. */
  @Min(1)
  @NotNull
  private Integer stock;

  /** Identificador del producto. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String productId;
}
