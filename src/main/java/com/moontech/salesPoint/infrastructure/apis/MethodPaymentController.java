package com.moontech.salesPoint.infrastructure.apis;

import com.moontech.salesPoint.application.service.MethodPaymentService;
import com.moontech.salesPoint.commons.constant.RoutesConstant;
import com.moontech.salesPoint.infrastructure.model.request.MethodPaymentRequest;
import com.moontech.salesPoint.infrastructure.model.response.MethodPaymentResponse;
import com.moontech.salesPoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * APIS para los métodos de pago.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 05 dic., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.METHOD_PAYMENT_BASE_PATH)
public class MethodPaymentController {
  /** Servicio para los métodos de pago. */
  private final MethodPaymentService methodPaymentService;

  /**
   * Consulta todos los métodos de pago.
   *
   * @return lista de métodos de pago encontrados
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  public ResponseEntity<List<MethodPaymentResponse>> retrieve() {
    return ResponseEntity.ok(this.methodPaymentService.retrieve());
  }

  /**
   * Busca un método de pago por descripción.
   *
   * @return lista de métodos de pago encontrados
   */
  @GetMapping(path = RoutesConstant.SEARCH_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  public ResponseEntity<List<MethodPaymentResponse>> findBy(
      @PathVariable @Valid @NotBlank String search) {
    return ResponseEntity.ok(this.methodPaymentService.findBy(search.trim()));
  }

  /**
   * API para guarda un método de pago
   *
   * @return Http 201 si se registro correctamente
   */
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  public ResponseEntity<MethodPaymentResponse> save(
      @RequestBody @Valid MethodPaymentRequest request) {
    return new ResponseEntity<>(this.methodPaymentService.save(request), HttpStatus.CREATED);
  }

  /**
   * Actualiza los datos de un método de pago.
   *
   * @return Http 204 si se elimino correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(path = RoutesConstant.DATA_MODIFIED_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(
      @PathVariable @Valid @Min(1) Long id, @RequestBody @Valid MethodPaymentRequest request) {
    this.methodPaymentService.update(id, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
