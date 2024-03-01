package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.enums.Status;
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
 * Entrada de la API de compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 29 feb., 2024
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopRequest {
  /** Propiedad para el total de la compra. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal total;

  /** Propiedad para el folio de la venta en línea (transferencia o tarjeta). */
  private String invoice;

  /** Propiedad para el identificador del proveedor. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String supplierId;

  /** Propiedad para el identificador del pago. */
  @NotNull private Long methodPaymentId;

  /** Propiedad para el identificador de la caja o punto de venta. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String pointSaleId;

  /** Propiedad para el estatus de la venta. */
  @NotNull private Status status;

  /** Propiedad para el detalle de la compra. */
  @Valid @NotNull private List<DetailRequest> details;
}
