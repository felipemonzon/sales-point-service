package com.moontech.salespoint.infrastructure.apis;

import com.moontech.salespoint.application.service.ProductStockService;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.infrastructure.model.request.StockTakingRequest;
import com.moontech.salespoint.infrastructure.model.response.StockTakingResponse;
import com.moontech.salespoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * API para productos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 29 dec., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.PRODUCT_STOCK_BASE_PATH)
public class ProductStockController {
  /** Servicio del inventario de productos. */
  private final ProductStockService productStockService;

  /**
   * API ára consultar el inventario completo.
   *
   * @param pageable paginación
   * @return lista de inventario de los productos
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<StockTakingResponse>> retrieve(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(this.productStockService.retrieve(pageable));
  }

  /**
   * Guarda el inventario de un producto.
   *
   * @param request datos del inventario
   * @return inventario registrado
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<StockTakingResponse> save(@RequestBody @Valid StockTakingRequest request) {
    return ResponseEntity.ok(this.productStockService.save(request));
  }

  /**
   * API para consultar el inventario de un producto
   *
   * @param id identificador del producto
   * @return inventario del producto
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @GetMapping(path = RoutesConstant.DATA_MODIFIED_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<StockTakingResponse> findStockByProduct(
      @PathVariable @Valid @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String id) {
    return ResponseEntity.ok(this.productStockService.findStockByProduct(id));
  }
}
