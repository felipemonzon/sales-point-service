package com.moontech.salespoint.application.service;

import com.moontech.salespoint.infrastructure.model.request.MethodPaymentRequest;
import com.moontech.salespoint.infrastructure.model.response.MethodPaymentResponse;
import java.util.List;

/**
 * Definición de las reglas de negocio para los métodos de pago.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 05 dic., 2023
 */
public interface MethodPaymentService {
  /**
   * Consulta todos los métodos de pago.
   *
   * @return lista de métodos de pago
   */
  List<MethodPaymentResponse> retrieve();

  /**
   * Consulta los datos de los métodos de pago por su descripción.
   *
   * @param search descripción del método de pago
   * @return lista o dato del método de pago
   */
  List<MethodPaymentResponse> findBy(String search);

  /**
   * Guarda los datos de un método de pago.
   *
   * @param request datos de la petición
   * @return datos del método de pago guardado
   */
  MethodPaymentResponse save(MethodPaymentRequest request);

  /**
   * Actualiza los datos de un método de pago.
   *
   * @param id identificador del método de pago
   * @param request datos del método de pago
   */
  void update(Long id, MethodPaymentRequest request);
}
