package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.domain.entity.ShopEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositorio para las compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
  /**
   * Consulta una venta por su folio.
   *
   * @param invoice folio de la venta
   * @return datos de la venta
   */
  ShopEntity findByIdShop(String invoice);

  /**
   * Consulta compra por fecha y status
   *
   * @param pageable paginación
   * @param date fecha de la compra
   * @param status estatus de la compra
   * @return lista de compras encontrada
   */
  @Query(value = "SELECT s FROM shopping s WHERE s.status = :status AND DATe(s.shopDate) = :date")
  List<ShopEntity> findByShopDateAndStatus(Pageable pageable, LocalDate date, Status status);
}
