package com.moontech.salesPoint.infrastructure.apis;

import com.moontech.salesPoint.application.service.ProductTypeService;
import com.moontech.salesPoint.commons.constant.RoutesConstant;
import com.moontech.salesPoint.infrastructure.model.request.ProductTypeRequest;
import com.moontech.salesPoint.infrastructure.model.response.ProductTypeResponse;
import com.moontech.salesPoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * APIS para tipos de productos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 24 dec., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.PRODUCT_TYPE_BASE_PATH)
public class ProductTypeController {
  /** Servicio para tipo de productos. */
  private final ProductTypeService productTypeService;

  /**
   * API para consultar los tipos de productos.
   *
   * @return lista de tipos de productos
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ProductTypeResponse>> retrieve() {
    return ResponseEntity.ok(this.productTypeService.retrieve());
  }

  /**
   * API para guardar un tipo de producto.
   *
   * @param request datos a guardar
   * @return dato guardado de tipo de producto
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ProductTypeResponse> save(@RequestBody @Valid ProductTypeRequest request) {
    return ResponseEntity.ok(this.productTypeService.save(request));
  }

  /**
   * API para actualizar los datos del tipo de producto.
   *
   * @param request datos del tipo de producto
   * @param id identificador del tipo de producto
   * @return 204 si se actualizo correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(
      path = RoutesConstant.DATA_MODIFIED_PATH,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> update(
      @RequestBody @Valid ProductTypeRequest request, @PathVariable @Valid @NotNull Long id) {
    this.productTypeService.update(request, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
