package com.moontech.salesPoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salesPoint.commons.constant.DatabaseConstant;
import com.moontech.salesPoint.commons.enums.Genre;
import jakarta.persistence.*;
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
 * Entidad para cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Entity(name = "customers")
@Table(name = "customers")
@AuditTable(value = "customers_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class CustomerEntity extends AuditableEntity {
  /** Identificador del usuario. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  @NaturalId
  @Column(name = "id_customer", nullable = false, length = 20, unique = true)
  private String idCustomer;

  /** Propiedad primer nombre. */
  @Column(name = DatabaseConstant.FIRST_NAME_PROPERTY, nullable = false)
  private String firstName;

  /** Propiedad para el apellido. */
  @Column(name = DatabaseConstant.LAST_NAME_PROPERTY, nullable = false)
  private String lastName;

  /** Propiedad para el correo. */
  @Column(name = "email", nullable = false)
  private String email;

  /** Propiedad para el celular. */
  @Column(name = "phone", nullable = false)
  private String cel;

  /** Propiedad para el género. */
  @Enumerated(EnumType.ORDINAL)
  private Genre genre;

  /** Deudas pertenecientes al cliente. */
  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private Set<DebtEntity> debts;
}
