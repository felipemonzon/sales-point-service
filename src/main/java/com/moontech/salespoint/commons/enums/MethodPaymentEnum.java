package com.moontech.salespoint.commons.enums;

import lombok.Getter;

/**
 * Catalogo de métodos de pagos.
 *
 * @author Felipe Monzón
 * @since 03 apr., 2024
 */
@Getter
public enum MethodPaymentEnum {
  DEBIT_CARD("Tarjeta debito"),
  CASH("Efectivo"),
  CREDIT_CARD("Tarjeta credito");

  /** Descripción del identificador. */
  private final String description;

  /**
   * Constructor.
   *
   * @param description descripción
   */
  MethodPaymentEnum(String description) {
    this.description = description;
  }
}
