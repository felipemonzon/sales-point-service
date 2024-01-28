package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.enums.Genre;
import java.io.Serializable;
import lombok.*;

/**
 * Respuesta de la API de clientes.
 *
 * @author Felipe Monzón
 * @since 23 nov., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerResponse implements Serializable {
  /** Identificador del cliente. */
  private String idCustomer;

  /** Propiedad primer nombre. */
  private String firstName;

  /** Propiedad para el apellido. */
  private String lastName;

  /** Propiedad para el correo. */
  private String email;

  /** Propiedad para el celular. */
  private String cel;

  /** Propiedad para el género. */
  private Genre genre;
}
