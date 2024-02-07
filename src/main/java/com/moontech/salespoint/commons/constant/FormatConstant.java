package com.moontech.salespoint.commons.constant;

/**
 * Clase con constantes para formatos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 30 may., 2023
 */
public class FormatConstant {
  /** Formato de salida de la respuesta de error. */
  public static final String ERROR_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /** Formato de fecha para deudas. */
  public static final String DEBT_DATE_PATTERN = "yyyy-MM-dd HH:mm";

  /** Formato para solo letras */
  public static final String ONLY_LETTERS_PATTERN = "^[A-Za-z\\s]+$";

  /** Formato para solo números */
  public static final String ONLY_NUMBERS_PATTERN = "^[0-9]+$";

  /** Formato para solo números y letras */
  public static final String ONLY_NUMBERS_AND_LETTERS_PATTERN = "^[A-Za-z0-9\\s]+$";

  /** Formato para contraseñas. */
  public static final String PAW_PATTERN = "^[A-Za-z0-9@#$%-&+=]+$";

  /** Formato para solo números y letras */
  public static final String USERNAME_PATTERN = "^[A-Za-z0-9\\s\\.\\_\\-]+$";

  /** Constructor privado. */
  private FormatConstant() {}
}
