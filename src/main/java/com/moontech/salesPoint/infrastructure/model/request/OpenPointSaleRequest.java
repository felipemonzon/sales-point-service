package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.enums.Status;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Inicia caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 18 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OpenPointSaleRequest {
  /** Dinero con el que se inicia la caja. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal openAmount;

  /** Caja. */
  @NotNull
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String pointSale;

  /** Fecha de apertura. */
  private LocalDateTime openPointSaleDate;

  /** Usuario que inicia la caja. */
  @NotNull
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String openUser;

  /** Propiedad para el status de la caja. */
  private Status status = Status.OPENED;
}
