package com.moontech.salespoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para retiros de la caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 18 nov., 2023
 */
@Getter
@Setter
@ToString
@Entity(name = "withdrawals_point_sales")
@Table(name = "withdrawals_point_sales")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
@AuditTable(value = "withdrawals_point_sales_aud")
public class WithdrawalPointSaleEntity extends AuditableEntity {
  /** Clave primaria. */
  @EmbeddedId private WithdrawalPointSaleId withdrawalPointSaleId;

  /** Cantidad retirada. */
  @Column(name = "withdrawals", nullable = false)
  private BigDecimal amount;

  /** Caja. */
  @ManyToOne
  @JsonIgnore
  @ToString.Exclude
  @MapsId("pointSaleId")
  @JoinColumn(name = "id_point_sale", referencedColumnName = "id")
  private PointSaleEntity pointSale;

  /** Fecha de retiro. */
  @Column(name = "withdrawals_date", nullable = false)
  private LocalDateTime withdrawalsDate;

  /** Descripción de retiro. */
  @Column(name = "description", nullable = false)
  private String description;
}
