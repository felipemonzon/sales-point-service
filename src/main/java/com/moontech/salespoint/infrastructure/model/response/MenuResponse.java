package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.enums.Status;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Respuesta con los datos de el menú de la aplicación.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 01 sept., 2024
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuResponse implements Serializable {
  /** Serial. */
  @Serial private static final long serialVersionUID = 1L;

  /** Identificador del menú. */
  private Long id;

  /** Ruta del menú. */
  private String path;

  /** Titulo a mostrar. */
  private String title;

  /** Icono representativo del menú. */
  private String icon;

  /** Propiedad paras el status de la venta. */
  private Status status;
}
