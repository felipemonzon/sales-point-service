package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.commons.constant.QueryConstant;
import com.moontech.salespoint.domain.entity.RoleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
