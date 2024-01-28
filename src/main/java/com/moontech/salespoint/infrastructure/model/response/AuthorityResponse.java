package com.moontech.salespoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Respuesta para perfiles.
 *
 * @author Felipe Monzón
 * @since 23 jun., 2023
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorityResponse implements Serializable {
  /** Identificador del perfil. */
  private long id;

  /** Descripción del perfil. */
  private String name;

  /** Valor del perfil. */
  private String value;
}
