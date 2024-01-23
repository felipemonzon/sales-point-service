package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.commons.constant.QueryConstant;
import com.moontech.salesPoint.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para los perfiles.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  /**
   * Consulta perfil por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Valor
   * </ul>
   *
   * @param search parámetro de consulta
   * @return lista de perfiles encontrados {@link RoleEntity}
   */
  @Query(nativeQuery = true, value = QueryConstant.FIND_PROFILES_BY)
  List<RoleEntity> findBy(@Param(QueryConstant.SEARCH_PARAMETER) String search);
}
