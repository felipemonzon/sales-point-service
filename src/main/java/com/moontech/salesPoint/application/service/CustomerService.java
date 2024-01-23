package com.moontech.salesPoint.application.service;

import com.moontech.salesPoint.domain.entity.CustomerEntity;
import com.moontech.salesPoint.infrastructure.model.request.CustomerRequest;
import com.moontech.salesPoint.infrastructure.model.response.CustomerResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Reglas de negocio para las APIS de clientes.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
public interface CustomerService {
  /**
   * Consulta todos los clientes.
   *
   * @return lista de clientes encontrados
   */
  List<CustomerResponse> retrieve(Pageable pageable);

  /**
   * Guarda los datos del cliente.
   *
   * @param request datos del cliente
   * @return datos del cliente registrado
   */
  CustomerResponse save(CustomerRequest request);

  /**
   * Actualiza los datos de un cliente
   *
   * @param request datos del cliente
   * @param id identificador del cliente
   */
  void update(CustomerRequest request, String id);

  /**
   * Consulta un cliente por su identificador.
   *
   * @param id identificador del cliente
   * @return datos del cliente
   */
  CustomerEntity searchById(String id);
}
