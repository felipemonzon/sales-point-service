package com.moontech.salespoint.application.service;

import com.moontech.salespoint.infrastructure.model.request.ClosePointSaleRequest;
import com.moontech.salespoint.infrastructure.model.request.OpenPointSaleRequest;
import com.moontech.salespoint.infrastructure.model.request.WithdrawalPointSaleRequest;
import com.moontech.salespoint.infrastructure.model.response.PointSaleDetailResponse;
import com.moontech.salespoint.infrastructure.model.response.WithdrawalPointSaleResponse;

/**
 * Definición de las operaciones para caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 10 dec., 2023
 */
public interface PointSaleDetailService {
  /**
   * Guarda el inicio de operación de la caja.
   *
   * @param request datos del inicio de la operación de la caja
   * @return datos del inicio de operación registrado
   */
  PointSaleDetailResponse openPointSale(OpenPointSaleRequest request);

  /**
   * Finaliza la operación de la caja o punto de venta.
   *
   * @param request datos de la caja a finalizar la operación
   * @return datos de la caja o punto de venta
   */
  PointSaleDetailResponse closePointSale(ClosePointSaleRequest request);

  /**
   * Genera un retiro de la caja.
   *
   * @param request datos del retiro
   * @return datos del retiro registrado
   */
  WithdrawalPointSaleResponse withdrawal(WithdrawalPointSaleRequest request);

  /**
   * Actualiza los datos de un retiro de efectivo en caja.
   *
   * @param request datos de la petición
   * @param withdrawalId identificador del retiro
   * @return datos del retiro
   */
  WithdrawalPointSaleResponse updateWithdrawal(
      WithdrawalPointSaleRequest request, String withdrawalId);
}
