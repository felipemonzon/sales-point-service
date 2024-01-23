package com.moontech.salesPoint.application.service;

import com.moontech.salesPoint.infrastructure.model.request.ProductRequest;
import com.moontech.salesPoint.infrastructure.model.response.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
  /**
   * Consulta los productos por paginación.
   *
   * @param pageable paginación
   * @return lista de productos
   */
  List<ProductResponse> retrieve(Pageable pageable);

  /**
   * Guarda los datos de un producto.
   *
   * @param request datos del producto
   * @return datos del producto guardado
   */
  ProductResponse save(ProductRequest request);

  /**
   * Actualiza los datos de un producto.
   *
   * @param request datos de un producto
   * @param idProduct identificador de un producto
   */
  void update(ProductRequest request, String idProduct);
}
