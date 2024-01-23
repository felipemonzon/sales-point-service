package com.moontech.salesPoint.application.service;

import com.moontech.salesPoint.infrastructure.model.request.EnterpriseRequest;
import com.moontech.salesPoint.infrastructure.model.response.EnterpriseResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Reglas de negocio de las APIS de empresas.
 *
 * @author Felipe Monzón
 * @since 18 jun., 2023
 */
public interface EnterpriseService {
  /**
   * Consulta la lista de empresas.
   *
   * @param pageable paginación
   * @return lista de empresas
   */
  List<EnterpriseResponse> retrieve(Pageable pageable);

  /**
   * Guarda los datos de la empresa.
   *
   * @param request datos de la empresa
   * @return datos de la empresa registrados
   */
  EnterpriseResponse save(EnterpriseRequest request);

  /**
   * Actualiza los datos de la empresa.
   *
   * @param request datos a actualizar
   * @param id identificador de la empresa
   */
  void update(EnterpriseRequest request, String id);

  /**
   * Elimina los datos de una empresa.
   *
   * @param id identificador de la empresa
   */
  void delete(String id);
}
