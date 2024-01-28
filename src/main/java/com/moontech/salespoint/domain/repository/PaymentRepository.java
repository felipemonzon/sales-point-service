package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.domain.entity.PaymentEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
