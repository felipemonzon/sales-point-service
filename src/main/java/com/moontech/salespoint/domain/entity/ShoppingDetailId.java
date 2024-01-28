package com.moontech.salespoint.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Llave compuesta para el detalle de la compra..
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
@Setter
@Getter
@ToString
@Embeddable
public class ShoppingDetailId implements Serializable {
  /** Identificador del producto. */
  @Column(name = "id_product", nullable = false)
  private Long productId;

  /** Folio de la compra. */
  private Long shopId;
}
