package com.moontech.salesPoint.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

/**
 * Llave compuesta para los retiros de los punto de venta..
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
@Setter
@Getter
@ToString
@Embeddable
public class WithdrawalPointSaleId implements Serializable {
  /** Folio del retiro. */
  @NaturalId
  @Column(name = "id_withdrawals", nullable = false, length = 20, unique = true)
  private String idWithdrawals;

  /** Identificador del punto de venta. */
  private Long pointSaleId;
}
