package com.moontech.salesPoint.commons.enums;

import lombok.Getter;

/**
 * Enum de identificadores.
 *
 * @author Felipe Monzón
 * @since 20 nov., 2023
 */
@Getter
public enum Identifier {
  ENTERPRISES("ENT"),
  CUSTOMERS("CUS"),
  USERS("USR"),
  POINT_SALE("POS"),
  SUPPLIERS("SUP"),
  DEBTS("DEB"),
  WITHDRAWAL("WID"),
  PRODUCTS("PRO"),
  PAYMENTS("PAY"),
  TRANSACTIONS("TXN");

  /** Código del identificador. */
  private final String code;

  /**
   * Constructor del enum
   *
   * @param code código
   */
  Identifier(String code) {
    this.code = code;
  }
}
