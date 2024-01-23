package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.moontech.salesPoint.commons.constant.FormatConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta para retiros de la caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WithdrawalPointSaleResponse {
  /** Folio del retiro. */
  private String idWithdrawal;

  /** Efectivo a retirar. */
  private BigDecimal amount;

  /** Caja. */
  private PointSaleResponse pointSale;

  /** Fecha de retiro. */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatConstant.DEBT_DATE_PATTERN)
  private LocalDateTime withdrawalsDate;

  /** Descripción de retiro. */
  private String description;
}
