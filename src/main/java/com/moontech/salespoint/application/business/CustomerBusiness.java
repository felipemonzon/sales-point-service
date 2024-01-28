package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.CustomerService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.CustomerEntity;
import com.moontech.salespoint.domain.repository.CustomerRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.CustomerRequest;
import com.moontech.salespoint.infrastructure.model.response.CustomerResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementación de las reglas de negocio.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerBusiness implements CustomerService {
  /** Repositorio de clientes. */
  private final CustomerRepository customerRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<CustomerResponse> retrieve(Pageable pageable) {
    int page = Utilities.getCurrentPage(pageable);
    log.info("consulta los clientes por la página {}", page);
    return this.customerRepository.findAll(PageRequest.of(page, pageable.getPageSize())).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public CustomerResponse save(CustomerRequest request) {
    List<CustomerEntity> customers =
        this.customerRepository.findByFirstNameAndLastName(
            request.getFirstName(), request.getLastName());
    if (customers.isEmpty()) {
      request.setIdCustomer(Utilities.generateRandomId(Identifier.CUSTOMERS.getCode()));
      log.info("Datos del cliente a guardar {}", request);
      return this.mapping(this.customerRepository.save(this.mapping(request)));
    } else {
      throw new BusinessException(
          ErrorConstant.DATA_EXIST_CODE, ErrorConstant.CUSTOMER_REGISTER_MESSAGE);
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void update(CustomerRequest request, String id) {
    CustomerEntity entity = this.findById(id);
    request.setId(entity.getId());
    request.setIdCustomer(id);
    log.info("Actualiza los datos del cliente {}", request);
    this.customerRepository.save(this.mapping(request));
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public CustomerEntity searchById(String id) {
    return this.findById(id);
  }

  /**
   * Consulta un cliente por su identificador.
   *
   * @param customerId identificador del cliente
   * @return datos del cliente
   */
  private CustomerEntity findById(String customerId) {
    CustomerEntity entity = this.customerRepository.findByIdCustomer(customerId);
    if (ObjectUtils.isEmpty(entity)) {
      throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.CUSTOMER_NOT_EXIST);
    }
    return entity;
  }

  /**
   * Convierte una entidad {@code CustomerEntity} a uno de tipo {@code CustomerResponse}
   *
   * @param entity objeto de tipo {@link CustomerEntity}
   * @return objeto de salida de la api de clientes
   */
  private CustomerResponse mapping(CustomerEntity entity) {
    return new ModelMapper().map(entity, CustomerResponse.class);
  }

  /**
   * Convierte un objeto {@link CustomerRequest} a un entity de tipo {@link CustomerEntity}
   *
   * @param request petición con los datos del cliente
   * @return {@link CustomerRequest} entidad de clientes
   */
  private CustomerEntity mapping(CustomerRequest request) {
    return new ModelMapper().map(request, CustomerEntity.class);
  }
}
