package com.moontech.salespoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salespoint.commons.constant.DatabaseConstant;
import com.moontech.salespoint.commons.enums.Status;
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
 * Entidad para la tabla "proveedores".
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 may., 2022
 */
@Getter
@Setter
@Entity
@Audited
@ToString
@Table(name = "suppliers")
@AuditTable(value = "suppliers_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class SupplierEntity extends AuditableEntity {
  /** Identificador del proveedor. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Propiedad para la empresa. */
  @Column(name = "id_supplier", nullable = false, length = 20)
  private String idSupplier;

  /** Nombre del encargado. */
  @NaturalId(mutable = true)
  @Column(name = "name", nullable = false, length = 50, unique = true)
  private String manager;

  /** Propiedad para la empresa. */
  @Column(name = "enterprise", nullable = false, length = 50)
  private String enterprise;

  /** Propiedad para el RFC. */
  @NaturalId(mutable = true)
  @Column(name = "rfc", nullable = false, unique = true, length = 20)
  private String rfc;

  /** Dirección del proveedor. */
  @Column(name = DatabaseConstant.PROPERTY_ADDRESS, nullable = false, length = 100)
  private String address;

  /** Teléfono del proveedor. */
  @Column(name = DatabaseConstant.PROPERTY_PHONE, nullable = false, length = 10)
  private String phone;

  /** Comentarios. */
  @Column(name = "comments", nullable = false, length = 200)
  private String comments;

  /** Status del proveedor. */
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;

  /** Productos pertenecientes al proveedor. */
  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
  private Set<ProductEntity> products;
}
