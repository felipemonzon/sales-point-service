package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.domain.entity.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para el tipo de producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {
  /**
   * Consulta un tipo de producto por su nombre.
   *
   * @param name nombre del tipo de producto
   * @return datos del tipo de producto
   */
  ProductTypeEntity findByName(String name);
}
