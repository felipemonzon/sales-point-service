package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.validation.constraints.*;
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

  /** Estatus del punto de venta. */
  private Status status = Status.ACTIVE;
}
