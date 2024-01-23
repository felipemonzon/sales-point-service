package com.moontech.salesPoint.infrastructure.apis;

import com.moontech.salesPoint.application.service.PointSaleDetailService;
import com.moontech.salesPoint.application.service.PointSaleService;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.constant.RoutesConstant;
import com.moontech.salesPoint.infrastructure.model.request.ClosePointSaleRequest;
import com.moontech.salesPoint.infrastructure.model.request.OpenPointSaleRequest;
import com.moontech.salesPoint.infrastructure.model.request.PointSaleRequest;
import com.moontech.salesPoint.infrastructure.model.request.WithdrawalPointSaleRequest;
import com.moontech.salesPoint.infrastructure.model.response.PointSaleDetailResponse;
import com.moontech.salesPoint.infrastructure.model.response.PointSaleResponse;
import com.moontech.salesPoint.infrastructure.model.response.WithdrawalPointSaleResponse;
import com.moontech.salesPoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * APIS para cajas o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 08 dec., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(RoutesConstant.POINT_SALE_BASE_PATH)
public class PointSaleController {
  /** Servicio de punto de venta. */
  private final PointSaleService pointSaleService;

  /** Servicio para operaciones de cajas o puntos de venta. */
  private final PointSaleDetailService pointSaleDetailService;

  /**
   * Consulta todos los puntos de venta.
   *
   * @return lista de puntos de venta encontrados
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  ResponseEntity<List<PointSaleResponse>> retrieve() {
    return ResponseEntity.ok(this.pointSaleService.retrieve());
  }

  /**
   * Busca un punto de venta por descripción.
   *
   * @return lista de puntos de venta encontrados
   */
  @GetMapping(path = RoutesConstant.SEARCH_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  ResponseEntity<List<PointSaleResponse>> findBy(@PathVariable @Valid @NotBlank String search) {
    return ResponseEntity.ok(this.pointSaleService.findBy(search.trim()));
  }

  /**
   * API para guarda un punto de venta
   *
   * @return Http 201 si se registro correctamente
   */
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  ResponseEntity<PointSaleResponse> save(@RequestBody @Valid PointSaleRequest request) {
    return new ResponseEntity<>(this.pointSaleService.save(request), HttpStatus.CREATED);
  }

  /**
   * Actualiza los datos de un punto de venta.
   *
   * @return Http 204 si se actualizó correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(path = RoutesConstant.DATA_MODIFIED_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> update(
      @PathVariable @Valid @NotEmpty String id, @RequestBody @Valid PointSaleRequest request) {
    this.pointSaleService.update(id, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Elimina los datos de un punto de venta.
   *
   * @return Http 204 si se elimino correctamente
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @DeleteMapping(
      path = RoutesConstant.DATA_MODIFIED_PATH,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> delete(@PathVariable @Valid @NotEmpty String id) {
    this.pointSaleService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * API para el inicio de operaciones de la caja o punto de venta.
   *
   * @param request datos de apertura
   * @return datos del inicio de operación
   */
  @PostMapping(
      path = RoutesConstant.OPEN_POINT_SALE_PATH,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PointSaleDetailResponse> openPointSale(
      @RequestBody @Valid OpenPointSaleRequest request) {
    return ResponseEntity.ok(this.pointSaleDetailService.openPointSale(request));
  }

  /**
   * API para finalizar las operaciones de la caja o punto de venta.
   *
   * @param request datos de apertura
   * @return datos del inicio de operación
   */
  @PostMapping(
      path = RoutesConstant.CLOSE_POINT_SALE_PATH,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PointSaleDetailResponse> closePointSale(
      @RequestBody @Valid ClosePointSaleRequest request) {
    return ResponseEntity.ok(this.pointSaleDetailService.closePointSale(request));
  }

  /**
   * API para realizar retiros de la caja o punto de venta.
   *
   * @param request datos para realizar el retiro
   * @return datos del retiro registrado
   */
  @PostMapping(
      path = RoutesConstant.WITHDRAWAL_POINT_SALE_PATH,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<WithdrawalPointSaleResponse> withdrawal(
      @RequestBody @Valid WithdrawalPointSaleRequest request) {
    return ResponseEntity.ok(this.pointSaleDetailService.withdrawal(request));
  }

  /**
   * API para actualizar los retiro de la caja.
   *
   * @param request datos del retiro
   * @param id identificador del retiro
   * @return datos actualizados del retiro
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(
      path = RoutesConstant.UPDATE_WITHDRAWAL_POINT_SALE_PATH,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<WithdrawalPointSaleResponse> updateWithdrawal(
      @RequestBody @Valid WithdrawalPointSaleRequest request,
      @PathVariable
          @Valid
          @NotEmpty
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String id) {
    return ResponseEntity.ok(this.pointSaleDetailService.updateWithdrawal(request, id));
  }
}
