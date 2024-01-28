package com.moontech.salespoint.infrastructure.apis;

import com.moontech.salespoint.application.service.EnterpriseService;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.infrastructure.model.request.EnterpriseRequest;
import com.moontech.salespoint.infrastructure.model.response.EnterpriseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * APIS de empresas
 *
 * @author Felipe Monzón
 * @since 09 jun., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.OFFICE_BASE_PATH)
public class EnterpriseController {
  /** Servicio de empresas. */
  private final EnterpriseService enterpriseService;

  /**
   * Consulta las empresas.
   *
   * @param pageable paginación
   * @return lista de empresas
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<EnterpriseResponse>> retrieveByPagination(
      @PageableDefault Pageable pageable) {
    return ResponseEntity.ok(this.enterpriseService.retrieve(pageable));
  }

  /**
   * Guarda los datos de una empresa.
   *
   * @param request datos de la empresa
   * @return 201 si se creo correctamente
   */
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EnterpriseResponse> save(@RequestBody @Valid EnterpriseRequest request) {
    return new ResponseEntity<>(this.enterpriseService.save(request), HttpStatus.CREATED);
  }

  /**
   * Actualiza los datos de la empresa.
   *
   * @param id identificador de la empresa
   * @param request datos de la petición
   * @return 204 si se actualizo correctamente
   */
  @PutMapping(
      path = "{idEnterprise}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(
      @PathVariable(name = "idEnterprise") @Valid @NotEmpty String id,
      @RequestBody @Valid EnterpriseRequest request) {
    this.enterpriseService.update(request, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * API para eliminar los datos de la empresa.
   *
   * @param id identificador de la empresa
   * @return 204 si se elimino correctamente
   */
  @DeleteMapping(path = "{idEnterprise}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(
      @PathVariable(name = "idEnterprise") @Valid @NotEmpty String id) {
    this.enterpriseService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
