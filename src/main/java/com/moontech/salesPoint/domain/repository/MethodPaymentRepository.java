package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.commons.constant.QueryConstant;
import com.moontech.salesPoint.domain.entity.MethodPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para el método de pago.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface MethodPaymentRepository extends JpaRepository<MethodPaymentEntity, Long> {
  /**
   * Consulta métodos de pago por su descripción.
   *
   * @param description descripción del método de pago
   * @return datos encontrados
   */
  MethodPaymentEntity findByDescription(String description);

  /**
   * Consulta método de pago por:
   *
   * <ul>
   *   <li>Descripción
   * </ul>
   *
   * @param param parámetro de búsqueda
   * @return lista de métodos encontrados
   */
  @Query(nativeQuery = true, value = QueryConstant.FIND_METHOD_PAYMENT_BY)
  List<MethodPaymentEntity> findBy(@Param(QueryConstant.SEARCH_PARAMETER) String param);
}
