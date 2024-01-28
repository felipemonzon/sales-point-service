package com.moontech.salespoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salespoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Objeto para autenticación de usuarios.
 *
 * @author Felipe Monzón
 * @since May. 31, 2023
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorizationRequest implements Serializable {
  /** Serial. */
  @Serial private static final long serialVersionUID = 1L;

  /** Nombre de usuario. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_LETTERS_PATTERN)
  private String username;

  /** Contraseña. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.PWD_PATTERN)
  private String password;
}
