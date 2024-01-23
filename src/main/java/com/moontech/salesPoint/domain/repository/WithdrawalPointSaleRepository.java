package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.WithdrawalPointSaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para los retiros de los punto de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface WithdrawalPointSaleRepository
    extends JpaRepository<WithdrawalPointSaleEntity, Long> {
  /**
   * Consulta los datos de n retiro por su identificador
   *
   * @param idWithdrawal identificador del retiro
   * @return datos del retiro encontrados
   */
    WithdrawalPointSaleEntity findByWithdrawalPointSaleIdIdWithdrawals(String idWithdrawal);
}
