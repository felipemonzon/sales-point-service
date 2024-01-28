package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.enums.Status;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Respuesta de las deudas del cliente.
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
public class DebtResponse implements Serializable {
  /** Propiedad para el identificador generado por el sistema. */
  private String idDebt;

  /** Propiedad para la fecha de la deuda. */
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = FormatConstant.DEBT_DATE_PATTERN, shape = JsonFormat.Shape.STRING)
  private LocalDateTime debtDate;

  /** Propiedad para el total pagado de la deuda. */
  private BigDecimal payment;

  /** Propiedad para el total de la deuda. */
  private BigDecimal total;

  /** Propiedad paras el status de la deuda. */
  private Status status;

  /** Datos del cliente moroso. */
  private CustomerResponse customer;

  /** Abonos pertenecientes a la deuda. */
  @JsonIgnore private Set<PaymentResponse> payments;
}
