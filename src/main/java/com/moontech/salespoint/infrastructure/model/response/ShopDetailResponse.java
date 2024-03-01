package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta de salida para la API de detalle de compra.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 29 feb., 2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopDetailResponse {
  /** Propiedad para las unidades vendidas. */
  private int piece;

  /** Propiedad para el subtotal de la compra. */
  private BigDecimal amount;

  /** Propiedad para productos. */
  private ProductResponse product;
}
