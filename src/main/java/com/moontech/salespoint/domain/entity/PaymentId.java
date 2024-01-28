package com.moontech.salespoint.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

/**
 * Llave compuesta para el abono de la deuda.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
@Setter
@Getter
@ToString
@Embeddable
public class PaymentId implements Serializable {
  /** Propiedad para el identificador generado por el sistema. */
  @NaturalId
  @Column(name = "id_payment", nullable = false, length = 20, unique = true)
  private String idPayment;

  /** Identificador de la deuda. */
  private Long debtId;
}
