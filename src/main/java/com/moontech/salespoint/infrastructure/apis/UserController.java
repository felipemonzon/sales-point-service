package com.moontech.salespoint.infrastructure.apis;

import com.moontech.salespoint.application.service.UserService;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.infrastructure.model.request.UserRequest;
import com.moontech.salespoint.infrastructure.model.response.UserResponse;
import com.moontech.salespoint.infrastructure.security.constant.SecurityConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * APIS para usuarios.
 *
 * @author Felipe Monzón
 * @since 21 jun., 2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.USERS_BASE_PATH)
public class UserController {
  /** Servicio de usuarios. */
  private final UserService userService;

  /**
   * API para consultar los usuarios.
   *
   * @param pageable datos de paginación
   * @return lista de usuarios
   */
  @PreAuthorize(value = SecurityConstants.ADMIN_ALLOWED)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UserResponse>> findAll(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAll(pageable));
  }

  /**
   * API para consultar los usuarios activos.
   *
   * @param pageable datos de la paginación
   * @return lista de usuarios encontrados
   */
  @PreAuthorize(value = SecurityConstants.ADMIN_ALLOWED)
  @GetMapping(path = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UserResponse>> findAllActive(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllActive(pageable));
  }

  /**
   * Guarda los datos del usuario.
   *
   * @param request datos del usuario
   * @return usuario registrado con éxito
   */
  @PreAuthorize(value = SecurityConstants.ADMIN_ALLOWED)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequest request) {
    return new ResponseEntity<>(this.userService.save(request), HttpStatus.CREATED);
  }

  /**
   * API para actualizar los datos del usuario
   *
   * @param request datos del usuario
   * @param id identificador del usuario
   * @return 204 si se actualizó correctamente
   */
  @PreAuthorize(value = SecurityConstants.ADMIN_ALLOWED)
  @PutMapping(path = RoutesConstant.DATA_MODIFIED_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> update(
      @RequestBody @Valid UserRequest request, @PathVariable @NotEmpty String id) {
    this.userService.update(id, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
