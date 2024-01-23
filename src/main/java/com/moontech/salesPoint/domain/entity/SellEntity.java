package com.moontech.salesPoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salesPoint.commons.constant.DatabaseConstant;
import com.moontech.salesPoint.commons.enums.Status;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
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
 * Entidad para las ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 20 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Table(name = "sells")
@Entity(name = "sells")
@AuditTable(value = "sells_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class SellEntity extends AuditableEntity {
  /** Identificador de la venta. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  @NaturalId
  @Column(name = "id_sell", nullable = false, length = 20, unique = true)
  private String idSell;

  /** Propiedad para la fecha de la venta. */
  @Column(name = "sell_date", nullable = false)
  private LocalDateTime sellDate;

  /** Propiedad para el total de la venta. */
  @Column(name = "amount", nullable = false)
  private BigDecimal total;

  /** Propiedad para el folio de la venta en línea (transferencia o tarjeta). */
  @Column(name = "invoice", length = 100)
  private String invoice;

  /** Propiedad paras el status de la venta. */
  @Enumerated(EnumType.STRING)
  @Column(name = DatabaseConstant.STATUS_PROPERTY, nullable = false)
  private Status status;

  /** Cliente. */
  @ManyToOne
  @JoinColumn(name = "id_customer", referencedColumnName = "id")
  private CustomerEntity customer;

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

  /** Detalle de venta. */
  @NotAudited
  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL)
  private Set<SellDetailEntity> details;
}
