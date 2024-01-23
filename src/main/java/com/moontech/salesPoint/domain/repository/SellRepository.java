package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.domain.entity.SellEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface SellRepository extends JpaRepository<SellEntity, Long> {
  /**
   * Consulta una venta por su folio.
   *
   * @param idSell folio de la venta
   * @return datos de la venta
   */
  SellEntity findByIdSell(String idSell);

  /**
   * Consulta venta por fecha y status
   *
   * @param pageRequest paginación
   * @param date fecha de la venta
   * @param status estatus de la venta
   * @return lista de ventas encontrada
   */
  List<SellEntity> findBySellDateAndStatus(
      PageRequest pageRequest, LocalDateTime date, Status status);
}
