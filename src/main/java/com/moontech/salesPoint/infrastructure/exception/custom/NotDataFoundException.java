package com.moontech.salesPoint.infrastructure.exception.custom;

import java.io.Serial;

/**
 * Excepción para datos no encontrados.
 *
 * @author Edgar Alarcón
 * @since May 30, 2023
 */
public class NotDataFoundException extends RuntimeException {
  /** Serial. */
  @Serial private static final long serialVersionUID = -6450278167900735942L;

  /** Constructor de la clase. */
  public NotDataFoundException() {
    super();
  }

  /**
   * Constructor de la clase.
   *
   * @param message mensaje de error
   */
  public NotDataFoundException(String message) {
    super(message);
  }
}
