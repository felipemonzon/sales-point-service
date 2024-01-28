package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.StockTakingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
  @Query(
      value =
          "SELECT st FROM stocktaking st INNER JOIN products p on p.id = st.product.id WHERE p.idProduct = :idProduct")
  StockTakingEntity findByIdProduct(@Param(value = "idProduct") String idProduct);
}
