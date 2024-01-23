package com.moontech.salesPoint.infrastructure.apis;

import com.moontech.salesPoint.application.service.DebtService;
import com.moontech.salesPoint.application.service.PaymentService;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.constant.RoutesConstant;
import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.infrastructure.model.request.DebtRequest;
import com.moontech.salesPoint.infrastructure.model.response.DebtResponse;
import com.moontech.salesPoint.infrastructure.model.response.PaymentResponse;
import com.moontech.salesPoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APIS para las deudas del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 dic., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.DEBT_BASE_PATH)
public class DebtController {
  /** Servicio de deudas. */
  private final DebtService debtService;

  /** Servicio de pagos. */
  private final PaymentService paymentService;

  /**
   * Consulta odas las deudas.
   *
   * @return lista de deudas encontradas
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<DebtResponse>> retrieve() {
    return ResponseEntity.ok(this.debtService.retrieve());
  }

  /**
   * API para consultar las deudas de un cliente
   *
   * @param idCustomer identificador del cliente
   * @return lista de deudas encontradas
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @GetMapping(path = RoutesConstant.CUSTOMER_DEBT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<DebtResponse>> findByCustomer(
      @PathVariable("idCustomer")
          @Valid
          @NotNull
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String idCustomer) {
    return ResponseEntity.ok(this.debtService.findByCustomer(idCustomer));
  }

  /**
   * Consulta las deudas de un cliente por status
   *
   * @param idCustomer identificador del cliente
   * @param status status a consultar
   * @return lista de deudas encontradas
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @GetMapping(
      path = RoutesConstant.CUSTOMER_DEBT_STATUS_PATH,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<DebtResponse>> findByCustomerAndStatus(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String idCustomer,
      @PathVariable @Valid @NotNull Status status) {
    return ResponseEntity.ok(this.debtService.findByCustomerAndStatus(idCustomer, status));
  }

  /**
   * API para guardar una deuda
   *
   * @param request datos de la deuda
   * @return datos de la deuda registrada
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<DebtResponse> save(@RequestBody @Valid DebtRequest request) {
    return new ResponseEntity<>(this.debtService.save(request), HttpStatus.CREATED);
  }

  /**
   * API para cancelar las deudas.
   *
   * @param id identificador de la deuda
   * @return 204 si se cancelo correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @DeleteMapping(
      path = RoutesConstant.DATA_MODIFIED_PATH,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> cancele(
      @PathVariable
          @Valid
          @NotEmpty
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String id) {
    this.debtService.cancele(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Actualiza los datos de la deuda.
   *
   * @param request datos a actualizar
   * @param id identificador de la deuda
   * @return 204 si se actualizó correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(path = RoutesConstant.DATA_MODIFIED_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> update(
      @RequestBody @Valid DebtRequest request,
      @PathVariable
          @Valid
          @NotEmpty
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String id) {
    this.debtService.update(id, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * API para consultar pagos de la deuda
   *
   * @param idDebt identificador de la deuda
   * @param pageable paginación
   * @return lista de pagos
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @GetMapping(path = "/{idDebt}/payments", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<PaymentResponse>> findByIdDebt(
      @PathVariable
          @Valid
          @NotEmpty
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String idDebt,
      @PageableDefault() Pageable pageable) {
    return ResponseEntity.ok(this.paymentService.findByIdDebt(pageable, idDebt));
  }
}
