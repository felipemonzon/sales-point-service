package com.moontech.salespoint.domain.entity;

import com.moontech.salespoint.commons.constant.DatabaseConstant;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para el inventario del producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 20 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Table(name = "stocktaking")
@Entity(name = "stocktaking")
@AuditTable(value = "stocktaking_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class StockTakingEntity extends AuditableEntity {
  /** Identificador del inventario. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Propiedad para el inventario máximo. */
  @Column(name = "stock_max", nullable = false)
  private Integer stockMax;

  /** Propiedad para el inventario mínimo. */
  @Column(name = "stock_min", nullable = false)
  private Integer stockMin;

  /** Propiedad para el inventario. */
  @Column(name = "stock", nullable = false)
  private Integer stock;

  /** Propiedad para el status del inventario. */
  @Enumerated(EnumType.STRING)
  @Column(name = DatabaseConstant.STATUS_PROPERTY, nullable = false)
  private Status status;

  /** Producto. */
  @ToString.Exclude
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_product", unique = true)
  private ProductEntity product;
}
