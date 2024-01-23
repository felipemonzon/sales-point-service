package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta para el inventario del producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 28 ec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StockTakingResponse {
  /** Identificador del inventario. */
  private Long id;

  /** Propiedad para el inventario máximo. */
  private Integer stockMax;

  /** Propiedad para el inventario mínimo. */
  private Integer stockMin;

  /** Propiedad para el inventario. */
  private Integer stock;

  /** Propiedad para el status del inventario. */
  private Status status;

  /** Datos del producto. */
  private ProductResponse product;
}
