package com.moontech.salespoint.application.service;

import com.moontech.salespoint.domain.entity.ProductEntity;
import com.moontech.salespoint.infrastructure.model.request.StockTakingRequest;
import com.moontech.salespoint.infrastructure.model.response.StockTakingResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProductStockService {
  /**
   * Consulta el inventario.
   *
   * @param pageable paginación
   * @return datos del inventario
   */
  List<StockTakingResponse> retrieve(Pageable pageable);

  /**
   * Guarda el inventario de un producto.
   *
   * @param request datos del inventario
   * @return inventario registrado
   */
  StockTakingResponse save(StockTakingRequest request);

  /**
   * Consulta el inventario de un producto.
   *
   * @param idProduct identificador del producto
   * @return inventario del producto
   */
  StockTakingResponse findStockByProduct(String idProduct);

  /**
   * Válida el inventario del producto
   *
   * @param idProduct identificador del producto
   * @param pieces piezas a comparar
   */
  void validateStock(String idProduct, int pieces);

  /**
   * Válida si el producto existe y trae sus datos.
   *
   * @param idProduct identificador del producto
   * @return datos del producto
   */
  ProductEntity findByIdProduct(String idProduct);
}
