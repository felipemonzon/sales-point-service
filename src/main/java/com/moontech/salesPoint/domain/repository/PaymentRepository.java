package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.PaymentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para los abonos de las deudas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
  /**
   * Consulta los pagos de una deuda.
   *
   * @param pageable paginación
   * @param idDebt identificador de una deuda
   * @return lista de pagos
   */
  List<PaymentEntity> findByDebtIdDebt(Pageable pageable, String idDebt);

  /**
   * Consulta un pago por su identificador
   *
   * @param idPayment identificador del pago
   * @return datos del pago
   */
  PaymentEntity findByPaymentIdIdPayment(String idPayment);
}
