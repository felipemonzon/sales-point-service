package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Petición para retiros de la caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WithdrawalPointSaleRequest {
  /** Cantidad retirada. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal amount;

  /** Caja. */
  @NotNull
  @Size(min = 20, max = 20)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String pointSaleId;

  /** Descripción del retiro. */
  @NotNull
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String description;
}
