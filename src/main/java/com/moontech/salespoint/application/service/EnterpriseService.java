package com.moontech.salespoint.application.service;

import com.moontech.salespoint.infrastructure.model.request.EnterpriseRequest;
import com.moontech.salespoint.infrastructure.model.response.EnterpriseResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

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
