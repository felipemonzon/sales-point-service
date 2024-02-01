package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta de salida para la API de pagos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 30 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentResponse implements Serializable {
  /** Propiedad para el identificador generado por el sistema. */
  private String idPayment;

  /** Propiedad para la fecha del abono. */
  private LocalDateTime paymentDate;

  /** Propiedad para el abono de la deuda. */
  private BigDecimal amount;

  /** Deuda del cliente. */
  private DebtResponse debt;

  /** Método de pago. */
  private MethodPaymentResponse methodPayment;
}
