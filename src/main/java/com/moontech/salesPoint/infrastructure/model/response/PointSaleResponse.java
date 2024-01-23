package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Respuesta para cajas o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 07 dec., 2023
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PointSaleResponse {
  /** Propiedad para el identificador generado por el sistema. */
  private String idPointSale;

  /** Propiedad para el nombre de la caja */
  private String name;

  /** Sucursal de la caja. */
  private EnterpriseResponse enterprise;
}
