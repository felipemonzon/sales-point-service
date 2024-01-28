package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.commons.constant.QueryConstant;
import com.moontech.salespoint.domain.entity.SupplierEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para los proveedores.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
  /**
   * Consulta proveedores por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Teléfono
   *   <li>Dirección
   * </ul>
   *
   * @param search parámetro de consulta
   * @return lista de proveedores encontrados {@link SupplierEntity}
   */
  @Query(nativeQuery = true, value = QueryConstant.FIND_SUPPLIERS_BY)
  List<SupplierEntity> findBy(@Param(QueryConstant.SEARCH_PARAMETER) String search);

  /**
   * Consulta un proveedor por su RFC.
   *
   * @param rfc a consultar
   * @return datos del proveedor encontrado
   */
  SupplierEntity findByRfc(String rfc);

  /**
   * Consulta un proveedor por su identificador
   *
   * @param idSupplier identificador del proveedor
   * @return datos del proveedor encontrado
   */
  Optional<SupplierEntity> findByIdSupplier(String idSupplier);
}
