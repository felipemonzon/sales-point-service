package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para los productos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  /**
   * Consulta un producto por su identificador.
   *
   * @param idProduct identificador del producto
   * @return datos del producto
   */
  ProductEntity findByIdProduct(String idProduct);

  /**
   * Consulta un producto por su nombre y proveedor.
   *
   * @param name nombre del producto
   * @param idSupplier identificador del proveedor
   * @return datos del producto
   */
  ProductEntity findByNameAndSupplierIdSupplier(String name, String idSupplier);
}
