package com.moontech.salespoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salespoint.commons.constant.DatabaseConstant;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para las compras a proveedor.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Table(name = "shopping")
@Entity(name = "shopping")
@AuditTable(value = "shopping_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class ShopEntity extends AuditableEntity {
  /** Identificador de la compra. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  @NaturalId
  @Column(name = "id_shopping", nullable = false, length = 20, unique = true)
  private String idShop;

  /** Propiedad para la fecha de la compra. */
  @Column(name = "shopping_date", nullable = false)
  private LocalDateTime sellDate;

  /** Propiedad para el total de la compra. */
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  /** Propiedad para el folio de la venta en linea (transferencia o tarjeta). */
  @Column(name = "invoice", length = 100)
  private String invoice;

  /** Propiedad paras el status de la venta. */
  @Enumerated(EnumType.STRING)
  @Column(name = DatabaseConstant.STATUS_PROPERTY, nullable = false)
  private Status status;

  /** Proveedor. */
  @ManyToOne
  @JoinColumn(name = "id_supplier", referencedColumnName = "id")
  private SupplierEntity supplier;

  /** Método de pago. */
  @NotAudited
  @ToString.Exclude
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_method_payment")
  private MethodPaymentEntity methodPayment;

  /** Punto de venta. */
  @ToString.Exclude
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_point_sale")
  private PointSaleEntity pointSale;

  /** Detalle de la compra. */
  @NotAudited
  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
  private Set<ShoppingDetailEntity> details;
}
