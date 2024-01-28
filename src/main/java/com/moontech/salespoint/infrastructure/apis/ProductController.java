package com.moontech.salespoint.infrastructure.apis;

import com.moontech.salespoint.application.service.ProductService;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.infrastructure.model.request.ProductRequest;
import com.moontech.salespoint.infrastructure.model.response.ProductResponse;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API para productos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 26 dec., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.PRODUCT_BASE_PATH)
public class ProductController {
  /** Servicio para productos. */
  private final ProductService productService;

  /**
   * API para consultar productos por página.
   *
   * @param page paginación
   * @return lista de productos
   */
  @Timed(value = "get-products")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ProductResponse>> retrieve(@PageableDefault Pageable page) {
    return ResponseEntity.ok(this.productService.retrieve(page));
  }

  /**
   * API para guardar un producto.
   *
   * @param request datos del producto
   * @return datos del producto guardado
   */
  @Timed(value = "save-product")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ProductResponse> save(@Valid ProductRequest request) {
    return ResponseEntity.ok(this.productService.save(request));
  }

  /**
   * Actualiza los datos del producto
   *
   * @param request datos del producto
   * @param id identificador del producto
   * @return 204 si se guardo con éxito
   */
  @Timed(value = "update-product")
  @PutMapping(path = RoutesConstant.DATA_MODIFIED_PATH)
  ResponseEntity<Void> update(
      @Valid ProductRequest request,
      @PathVariable @Valid @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String id) {
    this.productService.update(request, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
