package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.domain.entity.PointSaleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la apertura y cierre de los puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface PointSaleDetailRepository extends JpaRepository<PointSaleDetailEntity, Long> {
  /**
   * Consulta las operaciones de una caja o punto de venta.
   *
   * @param idPointSale identificador del punto de venta
   * @return datos del punto de venta
   */
  PointSaleDetailEntity findByPointSaleIdPointSale(String idPointSale);

  /**
   * Consulta las operaciones de una caja o punto de venta.
   *
   * @param idPointSale identificador del punto de venta
   * @param status estatus a consultar
   * @return datos del punto de venta
   */
  PointSaleDetailEntity findByPointSaleIdPointSaleAndStatus(String idPointSale, Status status);
}
