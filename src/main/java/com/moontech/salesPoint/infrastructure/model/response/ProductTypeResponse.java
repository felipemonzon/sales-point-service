package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta para tipos de productos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductTypeResponse {
  /** Identificador del tipo de producto. */
  private Long id;

  /** Propiedad para el nombre del tipo de producto. */
  private String name;
}
