package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta de salida para la API de ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 09 jan., 2024
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SellResponse {
  /** Propiedad para el identificador generado por el sistema. */
  private String idSell;

  /** Propiedad para la fecha de la venta. */
  private LocalDateTime sellDate;

  /** Propiedad para el total de la venta. */
  private BigDecimal total;

  /** Propiedad para el folio de la venta en línea (transferencia o tarjeta). */
  private String invoice;

  /** Propiedad paras el status de la venta. */
  private Status status;

  /** Cliente. */
  private CustomerResponse customer;

  /** Método de pago. */
  private MethodPaymentResponse methodPayment;

  /** Punto de venta. */
  private PointSaleResponse pointSale;

  /** Detalle de la venta. */
  private Set<SellDetailResponse> details;
}
