package com.moontech.salesPoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para el detalle de las ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
@Getter
@Setter
@ToString
@Table(name = "sells_details")
@Entity(name = "sells_details")
@EntityListeners(AuditingEntityListener.class)
public class SellDetailEntity extends AuditableEntity {
  /** Identificador del detalle. */
  @EmbeddedId private SellDetailId sellDetailId;

  /** Propiedad para las unidades vendidas. */
  @Column(name = "piece", nullable = false)
  private int piece;

  /** Propiedad para el subtotal de la venta. */
  @Column(name = "subtotal", nullable = false)
  private BigDecimal amount;

  /** Productos. */
  @ManyToOne
  @MapsId(value = "productId")
  @JoinColumn(name = "id_product", referencedColumnName = "id", nullable = false)
  private ProductEntity product;

  /** Venta. */
  @JsonIgnore
  @MapsId(value = "sellId")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id_sell", referencedColumnName = "id", nullable = false)
  private SellEntity sell;
}
