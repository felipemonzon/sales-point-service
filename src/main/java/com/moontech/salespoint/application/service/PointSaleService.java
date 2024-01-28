package com.moontech.salespoint.application.service;

import com.moontech.salespoint.domain.entity.PointSaleEntity;
import com.moontech.salespoint.infrastructure.model.request.PointSaleRequest;
import com.moontech.salespoint.infrastructure.model.response.PointSaleResponse;
import java.util.List;

/**
 * Definición de las reglas de negocio para cajas o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 08 dec., 2023
 */
public interface PointSaleService {
  /**
   * Consulta todos los puntos de venta.
   *
   * @return lista con los datos de punto de venta
   */
  List<PointSaleResponse> retrieve();

  /**
   * Consulta puntos de venta por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Identificador
   *   <li>status
   * </ul>
   *
   * @param search dato a consultar
   * @return lista de puntos de ventas encontrados
   */
  List<PointSaleResponse> findBy(String search);

  /**
   * Guarda los datos de un punto de venta.
   *
   * @param request datos del punto de venta {@link PointSaleRequest}
   * @return datos del punto de venta registrado
   */
  PointSaleResponse save(PointSaleRequest request);

  /**
   * Actualiza los datos de un punto de venta.
   *
   * @param id identificador del punto de venta
   * @param request datos del punto de venta {@link PointSaleRequest}
   */
  void update(String id, PointSaleRequest request);

  /**
   * Elimina un punto de venta.
   *
   * @param id identificador del punto de venta
   */
  void delete(String id);

  /**
   * Consulta un punto de venta por su identificador.
   *
   * @param id identificador del punto de venta
   * @return datos del punto de venta.
   */
  PointSaleEntity findById(String id);
}
