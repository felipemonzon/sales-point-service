package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.StockTakingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para el inventario.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface StockTakingRepository extends JpaRepository<StockTakingEntity, Long> {
  /**
   * Consulta el inventario de un producto.
   *
   * @param idProduct identificador del producto
   * @return Inventario del producto
   */
  StockTakingEntity findByProductIdProduct(String idProduct);
}
