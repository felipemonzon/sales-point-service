package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.domain.entity.DebtEntity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para las deudas del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface DebtRepository extends JpaRepository<DebtEntity, Long> {
  /**
   * Consulta una deuda por su folio.
   *
   * @param idDebt folio de la deuda
   * @return datos de la deuda encontrados
   */
  DebtEntity findByIdDebt(String idDebt);

  /**
   * Consulta una deuda por su folio.
   *
   * @param idDebt folio de la deuda
   * @param status estatus a consultar
   * @return datos de la deuda encontrados
   */
  DebtEntity findByIdDebtAndStatus(String idDebt, Status status);

  /**
   * Consulta los datos de la deuda del cliente.
   *
   * @param idCustomer identificador del cliente
   * @return lista con los datos de la deuda
   */
  Set<DebtEntity> findByCustomerIdCustomer(String idCustomer);

  /**
   * Consulta las deudas del cliente por su status.
   *
   * @param idCustomer identificador del cliente
   * @param status status a consultar
   * @return lista con los datos de la deuda
   */
  Set<DebtEntity> findByCustomerIdCustomerAndStatus(String idCustomer, Status status);
}
