package com.moontech.salesPoint.application.service;

import com.moontech.salesPoint.infrastructure.model.request.ProfileRequest;
import com.moontech.salesPoint.infrastructure.model.response.AuthorityResponse;
import java.util.List;

/**
 * Interface con las reglas de negocio para perfiles.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
public interface RoleService {
  /**
   * Consulta todos los perfiles.
   *
   * @return {@code AuthorityResponse}
   */
  List<AuthorityResponse> retrieve();

  /**
   * Guarda un perfile
   *
   * @param request {@code ProfileRequest}
   * @return datos del perfil registrado
   */
  AuthorityResponse save(ProfileRequest request);

  /**
   * Actualiza los datos del perfil
   *
   * @param id identificador del perfil
   * @param request {@code ProfileRequest}
   */
  void update(Long id, ProfileRequest request);

  /**
   * Consulta perfiles por nombre o valor
   *
   * @param search parámetro de búsqueda
   * @return lista de perfiles encontrados
   */
  List<AuthorityResponse> findBy(final String search);
}
