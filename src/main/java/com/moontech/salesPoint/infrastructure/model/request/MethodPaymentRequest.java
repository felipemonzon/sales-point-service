package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Tipos de métodos de pago.
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
public class MethodPaymentRequest {
  /** Identificador del método. */
  private Long id;

  /** Propiedad para la descripción. */
  @NotEmpty
  @Size(max = 50)
  private String description;
}
