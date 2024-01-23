package com.moontech.salesPoint.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para el detalle de las compras a proveedor.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
@Getter
@Setter
@ToString
@Table(name = "shopping_details")
@Entity(name = "shopping_details")
@EntityListeners(AuditingEntityListener.class)
public class ShoppingDetailEntity extends AuditableEntity {
  /** Identificador del detalle de la compra. */
  @EmbeddedId private ShoppingDetailId shoppingDetailId;

  /** Propiedad para las unidades compradas. */
  @Column(name = "piece", nullable = false)
  private int piece;

  /** Propiedad para el subtotal de la compra. */
  @Column(name = "subtotal", nullable = false)
  private BigDecimal amount;

  /** Compra. */
  @MapsId(value = "shopId")
  @ManyToOne(optional = false)
  @JoinColumn(name = "id_shopping", referencedColumnName = "id", nullable = false)
  private ShopEntity shop;

  /** Productos. */
  @ManyToOne
  @MapsId(value = "productId")
  @JoinColumn(name = "id_product", referencedColumnName = "id", nullable = false)
  private ProductEntity product;
}
