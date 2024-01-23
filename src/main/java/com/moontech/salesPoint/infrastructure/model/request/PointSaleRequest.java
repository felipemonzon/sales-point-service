package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase de entrada para cajas o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 07 dec., 2023
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PointSaleRequest {
  /** Identificador de la caja. */
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  private String idPointSale;

  /** Propiedad para el nombre de la caja */
  @NotEmpty
  @Size(max = 50)
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String name;

  /** sucursal de la caja. */
  @Min(1)
  @NotNull
  private Long enterpriseId;

  @Builder.Default private Status status = Status.ACTIVE;
}
