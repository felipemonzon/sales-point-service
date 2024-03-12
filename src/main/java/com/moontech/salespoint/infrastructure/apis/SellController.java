package com.moontech.salespoint.infrastructure.apis;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moontech.salespoint.application.service.SellService;
import com.moontech.salespoint.commons.constant.FormatConstant;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.infrastructure.model.request.SellRequest;
import com.moontech.salespoint.infrastructure.model.response.SellResponse;
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
 * APIS para ventas.
 *
 * @author Felipe Monzón
 * @since 29 feb., 2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(RoutesConstant.SELL_BASE_PATH)
public class SellController {
  /** Servicio de ventas. */
  private final SellService sellService;

  /**
   * API para consultar todas las ventas.
   *
   * @param pageable paginación
   * @return lista de ventas
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<SellResponse>> retrieve(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(this.sellService.retrieve(pageable));
  }

  /**
   * API para consultar venta por folio.
   *
   * @param invoice folio a consultar
   * @return datos de la venta
   */
  @GetMapping(path = "/{invoice}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SellResponse> findById(
      @PathVariable @Valid @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String invoice) {
    return ResponseEntity.ok(this.sellService.findById(invoice));
  }

  /**
   * API para guardar una venta.
   *
   * @param request datos de la venta
   * @return datos de la venta guardados
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<SellResponse> save(@RequestBody @Valid SellRequest request) {
    return ResponseEntity.ok(this.sellService.save(request));
  }

  /**
   * API para consultar venta por folio.
   *
   * @param pageable paginación
   * @param status status a consultar
   * @param date fecha de la venta
   * @return datos de la venta
   */
  @GetMapping(path = "/{status}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<SellResponse>> findByDateAndStatus(
      @PageableDefault Pageable pageable,
      @PathVariable @Valid @NotNull @JsonFormat(pattern = FormatConstant.SELL_DATE_PATTERN)
          LocalDate date,
      @PathVariable @Valid @NotNull Status status) {
    return ResponseEntity.ok(this.sellService.findByDateAndStatus(pageable, date, status));
  }

  /**
   * Cancela una venta.
   *
   * @param idSell identificador de la venta
   * @return 204 si se cancelo correctamente
   */
  @DeleteMapping(path = "/{idSell}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> cancel(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
          String idSell) {
    this.sellService.cancel(idSell);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
