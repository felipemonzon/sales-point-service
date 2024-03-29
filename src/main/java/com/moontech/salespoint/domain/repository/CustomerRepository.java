package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.domain.entity.CustomerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
  /**
   * Consulta los datos del cliente por su nombre y apellido.
   *
   * @param firstname nombre del cliente
   * @param lastName apellido del cliente
   * @return datos del cliente en caso de existir
   */
  List<CustomerEntity> findByFirstNameAndLastName(final String firstname, final String lastName);

  /**
   * Consulta un cliente por su identificador
   *
   * @param idCustomer identificador del cliente
   * @return datos del cliente
   */
  CustomerEntity findByIdCustomer(String idCustomer);
}
