package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entrada para los datos de perfile.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfileRequest {
  /** Identificador del perfil. */
  private long id;

  /** Descripción del perfil. */
  @NotBlank private String name;

  /** Valor del perfil. */
  @NotBlank private String value;
}
