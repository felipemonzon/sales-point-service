package com.moontech.salespoint.infrastructure.apis;

import com.moontech.salespoint.application.service.CustomerService;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.infrastructure.model.request.CustomerRequest;
import com.moontech.salespoint.infrastructure.model.response.CustomerResponse;
import com.moontech.salespoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * APIS para clientes.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.CUSTOMER_BASE_PATH)
public class CustomerController {
  /** Servicio para clientes. */
  private final CustomerService customerService;

  /**
   * Guarda los datos de un cliente
   *
   * @param request datos del cliente
   * @return 201 si se guardo con éxito
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerResponse> save(@RequestBody @Valid CustomerRequest request) {
    return new ResponseEntity<>(this.customerService.save(request), HttpStatus.CREATED);
  }

  /**
   * Consulta todos los clientes.
   *
   * @return lista de clientes encontrados
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CustomerResponse>> retrieve(@PageableDefault Pageable page) {
    return ResponseEntity.ok(this.customerService.retrieve(page));
  }

  /**
   * API para actualizar los datos de un cliente.
   *
   * @param request datos del cliente
   * @param id identificador del cliente
   * @return 204 si se actualizo con éxito
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(path = RoutesConstant.DATA_MODIFIED_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(
      @Valid @RequestBody CustomerRequest request, @NotBlank @PathVariable String id) {
    this.customerService.update(request, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
