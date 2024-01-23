package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datos de la API de pagos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 30 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentRequest {
  /** Propiedad para el abono de la deuda. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal amount;

  /** Deuda del cliente. */
  @NotNull
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String debtId;

  /** Método de pago. */
  @Min(1)
  @NotNull
  private Long methodPayment;
}
