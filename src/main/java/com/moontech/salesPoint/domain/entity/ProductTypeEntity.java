package com.moontech.salesPoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para el tipo de producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 18 nov., 2023
 */
@Getter
@Setter
@ToString
@Table(name = "product_type")
@Entity(name = "product_type")
@AuditTable(value = "product_type_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class ProductTypeEntity extends AuditableEntity {
  /** Identificador del tipo de producto. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** Propiedad para el nombre del tipo de producto. */
  @NaturalId(mutable = true)
  @Column(name = "name", nullable = false, length = 100, unique = true)
  private String name;

  /** Productos pertenecientes al tipo. */
  @JsonIgnore
  @ToString.Exclude
  @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
  private Set<ProductEntity> products;
}
