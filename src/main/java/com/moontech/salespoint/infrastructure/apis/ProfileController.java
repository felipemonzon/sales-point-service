package com.moontech.salespoint.infrastructure.apis;

import com.moontech.salespoint.application.service.RoleService;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.infrastructure.model.request.ProfileRequest;
import com.moontech.salespoint.infrastructure.model.response.AuthorityResponse;
import com.moontech.salespoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * APIS para perfiles.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 19 nov., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.PROFILE_BASE_PATH)
public class ProfileController {
  /** Servicio de perfiles. */
  private final RoleService roleService;

  /**
   * Consulta los perfiles disponibles.
   *
   * @return lista de perfiles
   */
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AuthorityResponse>> retrieve() {
    return ResponseEntity.ok(this.roleService.retrieve());
  }

  /**
   * Consulta los datos de un perfil por un dato de consulta
   *
   * @return perfiles encontrados
   */
  @GetMapping(path = RoutesConstant.SEARCH_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AuthorityResponse>> findBy(
      @PathVariable @Valid @NotBlank String search) {
    return ResponseEntity.ok(this.roleService.findBy(search.trim().toLowerCase()));
  }

  /**
   * Guarda los datos de un perfil.
   *
   * @param request {@code ProfileRequest}
   * @return 201 si se creo correctamente el perfil
   */
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  public ResponseEntity<AuthorityResponse> save(@RequestBody @Valid ProfileRequest request) {
    return new ResponseEntity<>(this.roleService.save(request), HttpStatus.CREATED);
  }

  /**
   * Actualiza los datos de un perfil
   *
   * @param id identificador del perfil
   * @param request {@code ProfileRequest}
   * @return 204 si se actualizó con éxito
   */
  @PutMapping(
      path = RoutesConstant.DATA_MODIFIED_PATH,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SecurityConstants.ADMIN_ALLOWED)
  public ResponseEntity<Void> update(
      @PathVariable @NotNull @Min(value = 1) Long id, @RequestBody @Valid ProfileRequest request) {
    this.roleService.update(id, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
