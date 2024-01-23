package com.moontech.salesPoint.infrastructure.exception.custom;

/**
 * Excepción de negocio.
 *
 * @author Felipe Monzón
 * @since May 30, 2023
 */
public class BusinessException extends RuntimeException {
  /** Serial. */
  private static final long serialVersionUID = -6450278167900735942L;
  /** Código de error. */
  private final int code;

  /**
   * Constructor de la clase.
   *
   * @param code código de error
   * @param message mensaje de error
   */
  public BusinessException(int code, String message) {
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
