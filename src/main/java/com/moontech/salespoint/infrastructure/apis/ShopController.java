package com.moontech.salespoint.infrastructure.apis;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moontech.salespoint.application.service.ShopService;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.infrastructure.model.request.ShopRequest;
import com.moontech.salespoint.infrastructure.model.response.ShopResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * APIS para compras.
 *
 * @author Felipe Monzón
 * @since 29 feb., 2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(RoutesConstant.SHOP_BASE_PATH)
public class ShopController {
  /** Servicio de compras. */
  private final ShopService shopService;

  /**
   * API para consultar todas las compras.
   *
   * @param pageable paginación
   * @return lista de compras
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ShopResponse>> retrieve(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(this.shopService.retrieve(pageable));
  }

  /**
   * API para consultar compra por folio.
   *
   * @param invoice folio a consultar
   * @return datos de la compra
   */
  @GetMapping(path = "/{invoice}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ShopResponse> findById(
      @PathVariable @Valid @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String invoice) {
    return ResponseEntity.ok(this.shopService.findById(invoice));
  }

  /**
   * API para guardar una compra.
   *
   * @param request datos de la compra
   * @return datos de la compra guardados
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ShopResponse> save(@RequestBody @Valid ShopRequest request) {
    return ResponseEntity.ok(this.shopService.save(request));
  }

  /**
   * API para consultar compra por folio.
   *
   * @param pageable paginación
   * @param status status a consultar
   * @param date fecha de la compra
   * @return datos de la compra
   */
  @GetMapping(path = "/{status}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ShopResponse>> findByDateAndStatus(
      @PageableDefault Pageable pageable,
      @PathVariable @Valid @NotNull @JsonFormat(pattern = FormatConstant.SELL_DATE_PATTERN)
          LocalDate date,
      @PathVariable @Valid @NotNull Status status) {
    return ResponseEntity.ok(this.shopService.findByDateAndStatus(pageable, date, status));
  }

  /**
   * Cancela una compra.
   *
   * @param idSell identificador de la compra
   * @return 204 si se cancelo correctamente
   */
  @DeleteMapping(path = "/{idSell}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> cancel(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String idSell) {
    this.shopService.cancel(idSell);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
