package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.PaymentService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.DebtEntity;
import com.moontech.salespoint.domain.entity.MethodPaymentEntity;
import com.moontech.salespoint.domain.entity.PaymentEntity;
import com.moontech.salespoint.domain.entity.PaymentId;
import com.moontech.salespoint.domain.repository.DebtRepository;
import com.moontech.salespoint.domain.repository.MethodPaymentRepository;
import com.moontech.salespoint.domain.repository.PaymentRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.PaymentRequest;
import com.moontech.salespoint.infrastructure.model.response.PaymentResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
 * Implementación de las reglas de negocio para las APIS de pagos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 31 dec., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentBusiness implements PaymentService {
  /** Repositorio de pagos. */
  private final PaymentRepository paymentRepository;

  /** Repositorio de deudas. */
  private final DebtRepository debtRepository;

  /** Repositorio de métodos de pago. */
  private final MethodPaymentRepository methodPaymentRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<PaymentResponse> retrieve(Pageable pageable) {
    log.info("Consulta todos los pagos");
    return this.paymentRepository.findAll(pageable).stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<PaymentResponse> findByIdDebt(Pageable pageable, String idDebt) {
    log.info("Consulta los pagos de la deuda {}", idDebt);
    int page = Utilities.getCurrentPage(pageable);
    return this.paymentRepository
        .findByDebtIdDebt(PageRequest.of(page, pageable.getPageSize()), idDebt)
        .stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public PaymentResponse findById(String paymentId) {
    return this.mapping(this.paymentRepository.findByPaymentIdIdPayment(paymentId));
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public PaymentResponse save(PaymentRequest request) {
    DebtEntity debt = this.validateDebt(request.getDebtId());
    PaymentEntity payment = this.mapping(request, debt);
    log.info("Registra el pago {}", payment);
    PaymentResponse response = this.mapping(this.paymentRepository.save(payment));
    this.validatePaymentDebt(debt, request.getAmount());
    return response;
  }

  /**
   * Valida si la deuda ya se pago.
   *
   * @param debt datos de la deuda
   * @param payment pago
   */
  private void validatePaymentDebt(DebtEntity debt, BigDecimal payment) {
    if (debt.getPayment().add(payment).compareTo(debt.getTotal()) == 0) {
      debt.setStatus(Status.PAYMENT);
      this.debtRepository.save(debt);
    }
  }

  /**
   * Valida si la deuda existe.
   *
   * @param idDebt identificador de la deuda
   * @return datos de la deuda
   */
  private DebtEntity validateDebt(final String idDebt) {
    DebtEntity debt = this.debtRepository.findByIdDebtAndStatus(idDebt, Status.ACTIVE);
    if (ObjectUtils.isEmpty(debt)) {
      throw new BusinessException(
          ErrorConstant.DATA_NOT_EXIST, ErrorConstant.DEBT_INVOICE_NOT_REGISTER_MESSAGE);
    }
    return debt;
  }

  /**
   * Convierte un objeto {@code PaymentEntity} a uno de tipo {@code PaymentResponse}
   *
   * @param entity entidad de pagos
   * @return {@code PaymentResponse}
   */
  private PaymentResponse mapping(PaymentEntity entity) {
    return new ModelMapper().map(entity, PaymentResponse.class);
  }

  /**
   * Convierte un objeto {@code PaymentRequest} a uno de tipo {@code PaymentEntity}
   *
   * @param request datos de pagos
   * @return {@code PaymentEntity}
   */
  private PaymentEntity mapping(PaymentRequest request, DebtEntity debt) {
    PaymentEntity entity = new PaymentEntity();
    entity.setDebt(debt);
    entity.setAmount(request.getAmount());
    entity.setPaymentDate(LocalDateTime.now());
    entity.setMethodPayment(this.methodPayment(request.getMethodPayment()));
    entity.setPaymentId(new PaymentId());
    entity.getPaymentId().setDebtId(entity.getDebt().getId());
    entity.getPaymentId().setIdPayment(Utilities.generateRandomId(Identifier.PAYMENTS.getCode()));
    return entity;
  }

  /**
   * Consulta el método de pago.
   *
   * @param id identificador del pago
   * @return datos del método de pago
   */
  private MethodPaymentEntity methodPayment(Long id) {
    return this.methodPaymentRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    ErrorConstant.DATA_NOT_EXIST, ErrorConstant.METHOD_PAYMENT_NOT_FOUND_MESSAGE));
  }
}
