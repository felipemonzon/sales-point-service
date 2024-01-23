package com.moontech.salesPoint.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para los métodos de pago aceptados.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 20 nov., 2023
 */
@Getter
@Setter
@ToString
@Table(name = "method_payment")
@Entity(name = "method_payment")
@AuditTable(value = "method_payment_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class MethodPaymentEntity extends AuditableEntity {
  /** Identificador del método. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Propiedad para la descripción. */
  @Column(name = "description", nullable = false, length = 50, unique = true)
  private String description;
}
