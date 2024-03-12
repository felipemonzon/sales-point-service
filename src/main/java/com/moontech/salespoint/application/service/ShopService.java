package com.moontech.salespoint.application.service;

import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.infrastructure.model.request.ShopRequest;
import com.moontech.salespoint.infrastructure.model.response.ShopResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * Reglas de negocio para las APIS de compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 29 feb., 2024
 */
public interface ShopService {
  /**
   * Consulta todas las compras.
   *
   * @param pageable paginación
   * @return lista de compras
   */
  List<ShopResponse> retrieve(Pageable pageable);

  /**
   * Consulta todas las compras.
   *
   * @param invoice folio de compra
   * @return lista de compras
   */
  ShopResponse findById(String invoice);

  /**
   * Consulta las compras por fecha y status.
   *
   * @param date fecha de la compra
   * @param status estatus de la compra
   * @return lista de compra
   */
  List<ShopResponse> findByDateAndStatus(Pageable pageable, LocalDate date, Status status);

  /**
   * Guarda una compra.
   *
   * @param request datos de la compra a guardar
   * @return datos de la compra guardada
   */
  ShopResponse save(ShopRequest request);

  /**
   * Cancela una compra.
   *
   * @param idShop identificador de la compra
   */
  void cancel(String idShop);
}
