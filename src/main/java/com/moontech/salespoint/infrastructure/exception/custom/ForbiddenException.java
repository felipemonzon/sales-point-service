package com.moontech.salespoint.infrastructure.exception.custom;

import java.io.Serial;

/**
 * Excepción lanzada para credenciales inválidas.
 *
 * @author Felipe Monzón
 * @since May 30, 2023
 */
public class ForbiddenException extends RuntimeException {
  /** Serial. */
  @Serial private static final long serialVersionUID = -6450278167900735942L;
  /** Código de error. */
  private final int code;

  /**
   * Constructor de la clase.
   *
   * @param code código de error
   * @param message mensaje de error
   */
  public ForbiddenException(int code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Obtiene el código de error
   *
   * @return código de error
   */
  public int getCode() {
    return code;
  }
}
