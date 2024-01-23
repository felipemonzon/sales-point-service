package com.moontech.salesPoint.application.service;

import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.infrastructure.model.request.DebtRequest;
import com.moontech.salesPoint.infrastructure.model.response.DebtResponse;
import java.util.List;

/**
 * Reglas de negocio para las deudas del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 dic., 2023
 */
public interface DebtService {
  /**
   * Consulta todas las deudas.
   *
   * @return lista de deudas
   */
  List<DebtResponse> retrieve();

  /**
   * Consulta las deudas de un cliente.
   *
   * @param idCustomer identificador del cliente
   * @return lista de deudas del cliente
   */
  List<DebtResponse> findByCustomer(String idCustomer);

  /**
   * Consulta las deudas de un cliente por su estatus.
   *
   * @param idCustomer identificador del cliente
   * @param status status de la deuda
   * @return lista de deudas encontradas
   */
  List<DebtResponse> findByCustomerAndStatus(String idCustomer, Status status);

  /**
   * Registra una deuda al cliente
   *
   * @param request datos de la deuda
   * @return datos registrados de la deuda
   */
  DebtResponse save(DebtRequest request);

  /**
   * Cancela una deuda.
   *
   * @param idDebt identificador de la deuda
   */
  void cancele(String idDebt);

  /**
   * Actualiza los datos de una deuda.
   *
   * @param id identificador de la deuda
   * @param request datos de la deuda
   */
  void update(String id, DebtRequest request);
}
