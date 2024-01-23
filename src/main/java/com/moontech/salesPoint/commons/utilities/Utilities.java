package com.moontech.salesPoint.commons.utilities;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.Pageable;

/**
 * Clase de utilerías.
 *
 * @author Felipe Monzón
 * @since 09 nov., 2023
 */
public class Utilities {
  /**
   * Obtiene la pagina actual.
   *
   * @param pageable paginación
   * @return pagina actual
   */
  public static int getCurrentPage(Pageable pageable) {
    int page = pageable.getPageNumber();
    if (pageable.getPageNumber() != 0) {
      page -= 1;
    }
    return page;
  }

  /**
   * Genera el id a guardar.
   *
   * @param prefix prefijo del identificador aleatorio
   * @return identificador generado
   */
  public static String generateRandomId(String prefix) {
    return prefix + RandomStringUtils.randomAlphanumeric(17);
  }

  /** Constructor privado. */
  private Utilities() {}
}
