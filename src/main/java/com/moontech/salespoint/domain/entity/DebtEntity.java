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
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para la deuda del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 20 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Table(name = "debts")
@Entity(name = "debts")
@AuditTable(value = "debts_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class DebtEntity extends AuditableEntity {
  /** Identificador del inventario. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  @NaturalId
  @Column(name = "id_debts", nullable = false, length = 20, unique = true)
  private String idDebt;

  /** Propiedad para la fecha de la deuda. */
  @Column(name = "debt_date", nullable = false)
  private LocalDateTime debtDate;

  /** Propiedad para el abono de la deuda. */
  @Column(name = "payment_debt", nullable = false)
  private BigDecimal payment;

  /** Propiedad para el total de la deuda. */
  @Column(name = "total_debt", nullable = false)
  private BigDecimal total;

  /** Propiedad paras el status de la deuda. */
  @Enumerated(EnumType.STRING)
  @Column(name = DatabaseConstant.STATUS_PROPERTY, nullable = false, columnDefinition = "varchar")
  private Status status;

  /** Cliente. */
  @ManyToOne
  @JoinColumn(name = "id_customer", referencedColumnName = "id")
  private CustomerEntity customer;

  /** Abonos pertenecientes a la deuda. */
  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL)
  private Set<PaymentEntity> payments;
}
