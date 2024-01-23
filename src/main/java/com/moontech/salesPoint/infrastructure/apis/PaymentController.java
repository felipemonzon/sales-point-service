package com.moontech.salesPoint.infrastructure.apis;

import com.moontech.salesPoint.application.service.PaymentService;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.constant.RoutesConstant;
import com.moontech.salesPoint.infrastructure.model.request.PaymentRequest;
import com.moontech.salesPoint.infrastructure.model.response.PaymentResponse;
import com.moontech.salesPoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * APIS para pagos
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 04 jan., 2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.DEBT_PAYMENTS_BASE_PATH)
public class PaymentController {
  /** Servicio de pagos. */
  private final PaymentService paymentService;

  /**
   * API para consultar pagos de la deuda
   *
   * @param idPayment identificador de la deuda
   * @return lista de pagos
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @GetMapping(path = "/{idPayment}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PaymentResponse> findById(
      @PathVariable
          @Valid
          @NotEmpty
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String idPayment) {
    return ResponseEntity.ok(this.paymentService.findById(idPayment));
  }

  /**
   * API para consultar todos los pagos
   *
   * @param pageable paginación
   * @return lista de pagos
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<PaymentResponse>> retrieve(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(this.paymentService.retrieve(pageable));
  }

  /**
   * Registra un pago a la deuda.
   *
   * @param request datos del pago
   * @return datos del pago registrados
   */
  @PreAuthorize(SecurityConstants.ADMIN_OR_CUSTOMER_ALLOWED)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PaymentResponse> save(@RequestBody @Valid PaymentRequest request) {
    return ResponseEntity.ok(this.paymentService.save(request));
  }
}
