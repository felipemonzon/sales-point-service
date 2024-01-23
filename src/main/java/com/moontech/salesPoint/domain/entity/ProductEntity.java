package com.moontech.salesPoint.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para el producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 18 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Table(name = "products")
@Entity(name = "products")
@AuditTable(value = "products_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class ProductEntity extends AuditableEntity {
  /** Identificador del producto. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  @NaturalId
  @Column(name = "id_product", nullable = false, length = 20, unique = true)
  private String idProduct;

  /** Imagen del producto. */
  @Lob
  @ToString.Exclude
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "image", nullable = false, columnDefinition = "MEDIUMBLOB")
  private byte[] image;

  /** Propiedad para el nombre del producto. */
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /** Propiedad para el precio unitario. */
  @Column(name = "price_unit", nullable = false)
  private BigDecimal unitPrice;

  /** Propiedad para el precio venta. */
  @Column(name = "price_sell", nullable = false)
  private BigDecimal sellPrice;

  /** Proveedor del producto. */
  @ManyToOne
  @JoinColumn(name = "supplier", referencedColumnName = "id")
  private SupplierEntity supplier;

  /** Tipo de producto. */
  @NotAudited
  @ManyToOne
  @JoinColumn(name = "product_type", referencedColumnName = "id")
  private ProductTypeEntity productType;

  /** Inventario. */
  @OneToOne(mappedBy = "product")
  private StockTakingEntity stockTaking;
}
