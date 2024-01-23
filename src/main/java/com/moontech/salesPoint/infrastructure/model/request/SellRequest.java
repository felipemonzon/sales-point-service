package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entrada de la API de ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 09 jan., 2024
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SellRequest {
  /** Propiedad para el total de la venta. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal total;

  /** Propiedad para el folio de la venta en línea (transferencia o tarjeta). */
  private String invoice;

  /** Propiedad para el identificador del cliente. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String customerId;

  /** Propiedad para el identificador del pago. */
  @NotNull private Long methodPaymentId;

  /** Propiedad para el identificador de la caja o punto de venta. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String pointSaleId;

  /** Propiedad para el estatus de la venta. */
  @NotNull private Status status;

  /** Propiedad para el detalle de la venta. */
  @Valid @NotNull private List<SellDetailRequest> details;
}
