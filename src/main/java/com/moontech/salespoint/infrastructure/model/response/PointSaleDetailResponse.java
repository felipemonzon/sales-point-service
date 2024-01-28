package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta para detalles de la caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 18 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PointSaleDetailResponse {
  /** Dinero con el que se inicia la caja. */
  private BigDecimal openAmount;

  /** Caja. */
  private PointSaleResponse pointSale;

  /** fecha de apertura. */
  private LocalDateTime openPointSaleDate;

  /** Usuario que inicia la caja. */
  private UserResponse openUser;

  /** Fecha de cierre. */
  private LocalDateTime closePointSaleDate;

  /** Usuario que cierra la caja. */
  private UserResponse closeUser;

  /** Propiedad para el status de la caja. */
  private Status status;
}
