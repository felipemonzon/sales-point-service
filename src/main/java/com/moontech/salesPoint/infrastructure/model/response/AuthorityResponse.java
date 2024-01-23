package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
