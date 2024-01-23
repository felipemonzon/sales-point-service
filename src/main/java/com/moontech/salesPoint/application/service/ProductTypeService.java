package com.moontech.salesPoint.application.service;

import com.moontech.salesPoint.infrastructure.model.request.ProductTypeRequest;
import com.moontech.salesPoint.infrastructure.model.response.ProductTypeResponse;

import java.util.List;

public interface ProductTypeService {
  /**
   * Consulta todos los tipos de producto.
   *
   * @return lista de tipos de producto
   */
  List<ProductTypeResponse> retrieve();

  /**
   * guarda los datos de un tipo de producto.
   *
   * @param request datos del tipo de producto
   * @return datos guardados
   */
  ProductTypeResponse save(ProductTypeRequest request);

  /**
   * Actualiza los datos del tipo de producto.
   *
   * @param request datos del tipo de producto
   * @param id identificador del tipo de producto
   */
  void update(ProductTypeRequest request, Long id);
}
