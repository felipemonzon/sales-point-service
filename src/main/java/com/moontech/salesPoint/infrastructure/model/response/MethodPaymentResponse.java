package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Respuesta de los métodos de pago.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 05 dic., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MethodPaymentResponse {
  /** Identificador del método. */
  private Long id;

  /** Propiedad para la descripción. */
  private String description;
}