package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.EnterpriseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para empresas.
 *
 * @author Felipe Monzón
 * @since 07 jun., 2023
 */
public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {
  /**
   * Consulta una empresa por su nombre.
   *
   * @param name nombre de la empresa
   * @return datos de la empresa
   */
  EnterpriseEntity findByName(String name);

  /**
   * Consulta los datos de una empresa por su id.
   *
   * @param idEnterprise identificador de la empresa
   * @return datos de la empresa
   */
  EnterpriseEntity findByIdEnterprise(String idEnterprise);
}
