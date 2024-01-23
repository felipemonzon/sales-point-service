package com.moontech.salesPoint.infrastructure.apis;

import com.moontech.salesPoint.application.service.SellService;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import com.moontech.salesPoint.commons.constant.RoutesConstant;
import com.moontech.salesPoint.infrastructure.model.request.SellRequest;
import com.moontech.salesPoint.infrastructure.model.response.SellResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
