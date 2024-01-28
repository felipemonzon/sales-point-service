package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Respuesta de salida para la API de detalle de ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 09 jan., 2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SellDetailResponse {
  /** Propiedad para las unidades vendidas. */
  private int piece;

  /** Propiedad para el subtotal de la venta. */
  private BigDecimal amount;

  /** Propiedad para productos. */
  private ProductResponse product;
}
