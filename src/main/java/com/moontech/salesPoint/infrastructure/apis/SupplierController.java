package com.moontech.salesPoint.infrastructure.apis;

import com.moontech.salesPoint.application.service.SupplierService;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.constant.RoutesConstant;
import com.moontech.salesPoint.infrastructure.model.request.SupplierRequest;
import com.moontech.salesPoint.infrastructure.model.response.SupplierResponse;
import com.moontech.salesPoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * APIS de proveedores.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.SUPPLIER_BASE_PATH)
public class SupplierController {
  /** Servicio de proveedores. */
  private final SupplierService supplierService;

  /**
   * Consulta todos los proveedores.
   *
   * @return lista de proveedores encontrados
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  public ResponseEntity<List<SupplierResponse>> retrieve() {
    return ResponseEntity.ok(this.supplierService.retrieve());
  }

  /**
   * Busca un proveedor por nombre, dirección, teléfono
   *
   * @return lista de proveedores encontrados
   */
  @GetMapping(path = RoutesConstant.SEARCH_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  public ResponseEntity<List<SupplierResponse>> findBy(
      @PathVariable @Valid @NotBlank String search) {
    return ResponseEntity.ok(this.supplierService.findBy(search.trim()));
  }

  /**
   * API supplier-data-save
   *
   * @return Http 201 si se registro correctamente
   */
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  public ResponseEntity<SupplierResponse> save(@RequestBody @Valid SupplierRequest request) {
    return new ResponseEntity<>(this.supplierService.save(request), HttpStatus.CREATED);
  }

  /**
   * Actualiza los datos de un proveedor
   *
   * @return Http 204 si se elimino correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(path = RoutesConstant.DATA_MODIFIED_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> findBy(
      @PathVariable
          @Valid
          @NotEmpty
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String id,
      @RequestBody @Valid SupplierRequest request) {
    this.supplierService.update(id, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Elimina un proveedor
   *
   * @return Http 204 si se elimino correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @DeleteMapping(path = RoutesConstant.DATA_MODIFIED_PATH)
  public ResponseEntity<Void> delete(
      @PathVariable
          @Valid
          @NotEmpty
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String id) {
    this.supplierService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
