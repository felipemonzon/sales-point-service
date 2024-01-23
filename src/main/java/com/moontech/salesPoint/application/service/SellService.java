package com.moontech.salesPoint.application.service;

import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.infrastructure.model.request.SellRequest;
import com.moontech.salesPoint.infrastructure.model.response.SellResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * Reglas de negocio para las APIS de ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 13 jan., 2024
 */
public interface SellService {
  /**
   * Consulta todas las ventas.
   *
   * @return lista de ventas
   */
  List<SellResponse> retrieve(Pageable pageable);

  /**
   * Consulta todas las ventas.
   *
   * @return lista de ventas
   */
  SellResponse findById(String invoice);

  /**
   * Consulta las ventas por fecha y status.
   *
   * @param date fecha de la venta
   * @param status estatus de la venta
   * @return lista de ventas
   */
  List<SellResponse> findByDateAndStatus(Pageable pageable, LocalDateTime date, Status status);

  /**
   * Guarda una venta.
   *
   * @param request datos de la venta a guardar
   * @return datos de la venta guardada
   */
  SellResponse save(SellRequest request);
}
