package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entrada de la API de detalle de ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 09 jan., 2024
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SellDetailRequest {
  /** Propiedad para las unidades vendidas. */
  @Min(1)
  @NotNull
  private Integer piece;

  /** Identificador del producto. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String productId;
}
