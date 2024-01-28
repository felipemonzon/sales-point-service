package com.moontech.salespoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salespoint.commons.constant.DatabaseConstant;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para detalles de la caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 17 nov., 2023
 */
@Getter
@Setter
@Audited
@ToString
@Entity(name = "point_sale_details")
@Table(name = "point_sale_details")
@AuditTable(value = "point_sale_details_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class PointSaleDetailEntity extends AuditableEntity {
  /** Identificador del punto de venta. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** Dinero con el que se inicia la caja. */
  @Column(name = "money", nullable = false)
  private BigDecimal openAmount;

  /** Datos de la caja. */
  @ManyToOne
  @JsonIgnore
  @ToString.Exclude
  @JoinColumn(name = "id_point_sale", referencedColumnName = "id")
  private PointSaleEntity pointSale;

  /** fecha de apertura. */
  @Column(name = "open_date", nullable = false)
  private LocalDateTime openPointSaleDate;

  /** Usuario que inicia la caja. */
  @ToString.Exclude
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "open_user", nullable = false)
  private UserEntity openUser;

  /** Fecha de cierre. */
  @Column(name = "close_date")
  private LocalDateTime closePointSaleDate;

  /** Usuario que cierra la caja. */
  @ToString.Exclude
  @JoinColumn(name = "close_user")
  @OneToOne(fetch = FetchType.LAZY)
  private UserEntity closeUser;

  /** Propiedad para el status de la caja. */
  @Enumerated(EnumType.STRING)
  @Column(name = DatabaseConstant.STATUS_PROPERTY, nullable = false)
  private Status status;
}
