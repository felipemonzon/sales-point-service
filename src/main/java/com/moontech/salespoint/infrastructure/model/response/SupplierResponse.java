package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.enums.Status;
import java.io.Serializable;
import lombok.*;

/**
 * Salida de la API de Proveedores.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SupplierResponse implements Serializable {
  /** Identificador del proveedor. */
  private String idSupplier;

  /** Nombre del encargado. */
  private String manager;

  /** Propiedad para la empresa. */
  private String enterprise;

  /** Propiedad para el RFC. */
  private String rfc;

  /** Dirección del proveedor. */
  private String address;

  /** Teléfono del proveedor. */
  private String phone;

  /** Comentarios. */
  private String comments;

  /** Status del proveedor. */
  private Status status;
}
