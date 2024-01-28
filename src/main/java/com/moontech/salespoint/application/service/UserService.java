package com.moontech.salespoint.application.service;

import com.moontech.salespoint.infrastructure.model.request.UserRequest;
import com.moontech.salespoint.infrastructure.model.response.UserResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * Reglas de negocio para usuarios.
 *
 * @author Felipe Monzón
 * @since 21 jun., 2023
 */
public interface UserService {
  /**
   * Consulta todos los usuarios
   *
   * @param pageable paginación
   * @return lista de usuarios
   */
  List<UserResponse> findAll(Pageable pageable);

  /**
   * Consulta los usuarios activos.
   *
   * @param pageable paginación
   * @return lista de usuarios
   */
  List<UserResponse> findAllActive(Pageable pageable);

  /**
   * guarda los datos de un usuario.
   *
   * @param request datos del usuario
   * @return datos del usuario registrado
   */
  UserResponse save(UserRequest request);

  /**
   * Actualiza los datos del usuario.
   *
   * @param id identificador del usuario
   * @param request datos a actualizar
   */
  void update(String id, UserRequest request);
}
