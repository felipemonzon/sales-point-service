package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Respuesta de salida para la API de compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 29 feb., 2024
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShopResponse {
  /** Propiedad para el identificador generado por el sistema. */
  private String idShop;

  /** Propiedad para la fecha de la compra. */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatConstant.ERROR_DATE_PATTERN)
  private LocalDateTime shopDate;

  /** Propiedad para el total de la compra. */
  private BigDecimal total;

  /** Propiedad para el folio de la venta en línea (transferencia o tarjeta). */
  private String invoice = StringUtils.EMPTY;

  /** Propiedad paras el status de la venta. */
  private Status status;

  /** Proveedor. */
  private SupplierResponse supplier;

  /** Método de pago. */
  private MethodPaymentResponse methodPayment;

  /** Punto de venta. */
  private PointSaleResponse pointSale;

  /** Detalle de la compra. */
  private Set<ShopDetailResponse> details;
}
