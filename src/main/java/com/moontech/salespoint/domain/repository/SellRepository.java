package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.domain.entity.SellEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
  @Query(value = "SELECT s FROM sells s WHERE s.status = :status AND DATe(s.sellDate) = :date")
  List<SellEntity> findBySellDateAndStatus(
      PageRequest pageRequest,
      @Param(value = "date") LocalDate date,
      @Param(value = "status") Status status);
}
