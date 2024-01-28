package com.moontech.salespoint.application.service;

import com.moontech.salespoint.infrastructure.model.request.SupplierRequest;
import com.moontech.salespoint.infrastructure.model.response.SupplierResponse;
import java.util.List;

/**
 * Reglas de negocio del módulo de proveedores.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
public interface SupplierService {
  /**
   * Consulta todos los proveedores.
   *
   * @return lista de proveedores {@link SupplierResponse}
   */
  List<SupplierResponse> retrieve();

  /**
   * Consulta proveedores por:
   *
   * <ul>
   *   *
   *   <li>Nombre
   *   <li>Teléfono
   *   <li>Dirección
   * </ul>
   *
   * @param search parámetro de búsqueda
   * @return lista de proveedores {@link SupplierResponse}
   */
  List<SupplierResponse> findBy(String search);

  /**
   * Registra un proveedor.
   *
   * @param request datos del proveedor
   * @return datos del proveedor guardado
   */
  SupplierResponse save(SupplierRequest request);

  /**
   * Actualiza los datos de un proveedor.
   *
   * @param id identificador del proveedor
   * @param request datos del proveedor
   */
  void update(String id, SupplierRequest request);

  /**
   * Elimina un proveedor.
   *
   * @param id identificador del proveedor
   */
  void delete(String id);
}
