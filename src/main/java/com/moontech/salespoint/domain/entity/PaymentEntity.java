package com.moontech.salespoint.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para los abonos de la deuda del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 20 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Table(name = "payments")
@Entity(name = "payments")
@AuditTable(value = "payments_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class PaymentEntity extends AuditableEntity {
  /** Identificador del detalle. */
  @EmbeddedId private PaymentId paymentId;

  /** Propiedad para la fecha del abono. */
  @Column(name = "payment_date", nullable = false)
  private LocalDateTime paymentDate;

  /** Propiedad para el abono de la deuda. */
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  /** Deuda del cliente. */
  @MapsId("debtId")
  @ManyToOne(optional = false)
  @JoinColumn(name = "id_debt", referencedColumnName = "id", nullable = false)
  private DebtEntity debt;

  /** Método de pago. */
  @NotAudited
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_method_payment", nullable = false)
  private MethodPaymentEntity methodPayment;
}
