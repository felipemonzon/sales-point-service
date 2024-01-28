package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.DebtService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.CustomerEntity;
import com.moontech.salespoint.domain.entity.DebtEntity;
import com.moontech.salespoint.domain.repository.CustomerRepository;
import com.moontech.salespoint.domain.repository.DebtRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.DebtRequest;
import com.moontech.salespoint.infrastructure.model.response.DebtResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Implementación de las reglas de negocio para las deudas del cliente.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 dic., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DebtBusiness implements DebtService {
  /** Repositorio para deudas. */
  private final DebtRepository debtRepository;

  /** Repositorio de clientes. */
  private final CustomerRepository customerRepository;

  /** {@inheritDoc}. */
  @Override
  public List<DebtResponse> retrieve() {
    log.info("Consulta todos las deudas");
    return this.debtRepository.findAll().stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  public List<DebtResponse> findByCustomer(String idCustomer) {
    log.info("Consulta todos las deudas de un cliente {}", idCustomer);
    return this.debtRepository.findByCustomerIdCustomer(idCustomer).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  public List<DebtResponse> findByCustomerAndStatus(String idCustomer, Status status) {
    log.info("Consulta todos las deudas de un cliente {} por status {}", idCustomer, status);
    return this.debtRepository.findByCustomerIdCustomerAndStatus(idCustomer, status).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  public DebtResponse save(DebtRequest request) {
    Set<DebtEntity> entity =
        this.debtRepository.findByCustomerIdCustomerAndStatus(
            request.getCustomerId(), Status.ACTIVE);
    if (ObjectUtils.isEmpty(entity)) {
      CustomerEntity customer = this.validateCustomer(request.getCustomerId());
      request.setIdDebt(Utilities.generateRandomId(Identifier.DEBTS.getCode()));
      DebtEntity debt = this.mapping(request, customer);
      log.info("Registra deuda {}", debt);
      return this.mapping(this.debtRepository.save(debt));
    } else {
      throw new BusinessException(
          ErrorConstant.RECORD_NOT_FOUND_CODE, ErrorConstant.DEBT_ACTIVE_MESSAGE);
    }
  }

  /** {@inheritDoc}. */
  @Override
  public void cancele(String idDebt) {
    DebtEntity debt = this.debtRepository.findByIdDebt(idDebt);
    if (ObjectUtils.isEmpty(debt)) {
      throw new BusinessException(
          ErrorConstant.RECORD_NOT_FOUND_CODE, ErrorConstant.DEBT_INVOICE_NOT_REGISTER_MESSAGE);
    } else {
      debt.setStatus(Status.CANCELED);
      this.debtRepository.save(debt);
    }
  }

  /** {@inheritDoc}. */
  @Override
  public void update(String id, DebtRequest request) {
    DebtEntity entity = this.debtRepository.findByIdDebt(id);
    if (ObjectUtils.isEmpty(entity)) {
      throw new BusinessException(
          ErrorConstant.RECORD_NOT_FOUND_CODE, ErrorConstant.DEBT_INVOICE_NOT_REGISTER_MESSAGE);
    } else {
      CustomerEntity customer = this.validateCustomer(request.getCustomerId());
      request.setIdDebt(id);
      DebtEntity debt = this.mapping(request, customer);
      debt.setId(entity.getId());
      log.info("Actualiza los datos de la deuda {}", debt);
      this.debtRepository.save(debt);
    }
  }

  /**
   * Valida si el cliente existe.
   *
   * @param customerId identificador del cliente
   * @return datos del cliente
   */
  private CustomerEntity validateCustomer(String customerId) {
    CustomerEntity customer = this.customerRepository.findByIdCustomer(customerId);
    if (ObjectUtils.isEmpty(customer)) {
      throw new BusinessException(
          ErrorConstant.RECORD_NOT_FOUND_CODE, ErrorConstant.CUSTOMER_NOT_EXIST);
    }
    return customer;
  }

  /**
   * Convierte un entity de tipo {@link DebtEntity} a un objeto {@link DebtResponse}
   *
   * @param entity entidad de deudas
   * @return {@link DebtResponse} respuesta de la API de deudas
   */
  private DebtResponse mapping(DebtEntity entity) {
    return new ModelMapper().map(entity, DebtResponse.class);
  }

  /**
   * Convierte un objeto {@link DebtRequest} a un entity de tipo {@link DebtEntity}
   *
   * @param request petición con los datos de las deudas
   * @return {@link DebtEntity} entidad de deudas
   */
  private DebtEntity mapping(DebtRequest request, CustomerEntity customer) {
    DebtEntity debt = new DebtEntity();
    debt.setDebtDate(request.getDebtDate());
    debt.setTotal(request.getTotal());
    debt.setCustomer(customer);
    debt.setIdDebt(request.getIdDebt());
    debt.setPayment(BigDecimal.ZERO);
    debt.setStatus(Status.ACTIVE);
    return debt;
  }
}
