package com.moontech.salespoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para cajas o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 17 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Table(name = "point_sales")
@Entity(name = "point_sales")
@AuditTable(value = "point_sales_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class PointSaleEntity extends AuditableEntity {
  /** Identificador del punto de venta. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** Propiedad para el identificador generado por el sistema. */
  @NaturalId
  @Column(name = "id_point_sale", nullable = false, length = 20, unique = true)
  private String idPointSale;

  /** Propiedad para el nombre de la caja */
  @Column(name = "name", nullable = false, length = 50)
  private String name;

  /** Status del punto de venta. */
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;

  /** sucursal de la caja. */
  @NotAudited
  @ManyToOne
  @JsonIgnore
  @ToString.Exclude
  @JoinColumn(name = "id_enterprise", referencedColumnName = "id")
  @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
  private EnterpriseEntity enterprise;

  /** Detalles de la Cajas. */
  @JsonIgnore
  @OneToMany(mappedBy = "pointSale", cascade = CascadeType.ALL)
  private Set<PointSaleDetailEntity> details;
}
