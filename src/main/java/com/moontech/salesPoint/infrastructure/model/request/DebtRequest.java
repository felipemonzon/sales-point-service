package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.enums.Status;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Datos de la petición de las deudas del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 dic., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DebtRequest {
  /** Propiedad para el identificador generado por el sistema. */
  private String idDebt;

  /** Propiedad para la fecha de la deuda. */
  @PastOrPresent
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatConstant.DEBT_DATE_PATTERN)
  private LocalDateTime debtDate;

  /** Propiedad para el total de la deuda. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal total;

  /** Propiedad paras el status de la deuda. */
  private Status status;

  /** Datos del cliente moroso. */
  @NotNull
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String customerId;
}
