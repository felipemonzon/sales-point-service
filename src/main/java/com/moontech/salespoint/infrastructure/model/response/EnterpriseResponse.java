package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

/**
 * Objeto de salida para las APIS de empresas.
 *
 * @author Felipe Monzón
 * @since 23 nov., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EnterpriseResponse {
  /** Identificador alfanumérico de la empresa. */
  private String idEnterprise;

  /** Nombre de la empresa. */
  private String name;

  /** Dirección de la empresa. */
  private String address;

  /** Teléfono de la empresa. */
  private String phone;

  /** Status de la empresa. */
  private Boolean active;

  /** Encargado de la empresa. */
  private String manager;

  /** RFC de la empresa. */
  private String rfc;
}
