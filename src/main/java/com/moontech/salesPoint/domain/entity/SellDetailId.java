package com.moontech.salesPoint.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Llave compuesta para el detalle de las ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
@Setter
@Getter
@ToString
@Embeddable
public class SellDetailId implements Serializable {
  /** Identificador del producto. */
  @Column(name = "id_product", nullable = false)
  private Long productId;

  /** Identificador de la venta. */
  private Long sellId;
}
