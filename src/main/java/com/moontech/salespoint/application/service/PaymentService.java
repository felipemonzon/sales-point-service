package com.moontech.salespoint.application.service;

import com.moontech.salespoint.infrastructure.model.request.PaymentRequest;
import com.moontech.salespoint.infrastructure.model.response.PaymentResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * Reglas de negocio para la API de pagos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 01 jan., 2024
 */
public interface PaymentService {
  /**
   * Consulta los pagos de una deuda.
   *
   * @param pageable paginación
   * @param idDebt identificador de la deuda
   * @return lista de pagos
   */
  List<PaymentResponse> findByIdDebt(Pageable pageable, String idDebt);

  /**
   * Consulta los datos de un pago
   *
   * @param paymentId identificador del pago
   * @return datos del pago
   */
  PaymentResponse findById(String paymentId);

  /**
   * Registra el pago del cliente.
   *
   * @param request datos del pago
   * @return datos del pago
   */
  PaymentResponse save(PaymentRequest request);

  /**
   * Consulta todos los pagos.
   *
   * @param pageable paginación
   * @return lista de pagos
   */
  List<PaymentResponse> retrieve(Pageable pageable);
}
