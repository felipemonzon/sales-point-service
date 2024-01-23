package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.commons.constant.QueryConstant;
import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.domain.entity.PointSaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para los puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface PointSaleRepository extends JpaRepository<PointSaleEntity, Long> {
  /**
   * Consulta una caja o punto de venta por su identificador generado.
   *
   * @param name nombre del punto de venta
   * @param status status a buscar
   * @param idEnterprise identificador de la sucursal
   * @return datos de la caja encontrados
   */
  PointSaleEntity findByNameAndStatusAndEnterpriseId(String name, Status status, Long idEnterprise);

  /**
   * Consulta un punto de venta por su identificador.
   *
   * @param pointSale identificador del punto de venta
   * @return datos del punto de venta
   */
  Optional<PointSaleEntity> findByIdPointSale(String pointSale);

  /**
   * Consulta punto de venta por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Identificador
   *   <li>status
   * </ul>
   *
   * @param search parámetro de búsqueda
   * @return datos del punto de venta
   */
  @Query(nativeQuery = true, value = QueryConstant.FIND_POINT_SALE_BY)
  List<PointSaleEntity> findBy(@Param(value = QueryConstant.SEARCH_PARAMETER) String search);
}
