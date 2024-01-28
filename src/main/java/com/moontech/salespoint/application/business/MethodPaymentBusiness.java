package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.MethodPaymentService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.domain.entity.MethodPaymentEntity;
import com.moontech.salespoint.domain.repository.MethodPaymentRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.exception.custom.NotDataFoundException;
import com.moontech.salespoint.infrastructure.model.request.MethodPaymentRequest;
import com.moontech.salespoint.infrastructure.model.response.MethodPaymentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Implementación de las reglas de negocio para los métodos de pago.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 05 dic., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MethodPaymentBusiness implements MethodPaymentService {
  /** Repositorio para los métodos de pago. */
  private final MethodPaymentRepository methodPaymentRepository;

  /** {@inheritDoc}. */
  @Override
  public List<MethodPaymentResponse> retrieve() {
    log.info("Consulta todos los métodos de pagos");
    return this.methodPaymentRepository.findAll().stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  public List<MethodPaymentResponse> findBy(String search) {
    log.debug("Consulta métodos de pago por {}", search);
    return this.methodPaymentRepository.findBy(search).stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  public MethodPaymentResponse save(MethodPaymentRequest request) {
    MethodPaymentEntity entity =
        this.methodPaymentRepository.findByDescription(request.getDescription());
    if (ObjectUtils.isEmpty(entity)) {
      log.debug("Método de pago a guardar {}", request);
      return this.mapping(this.methodPaymentRepository.save(this.mapping(request)));
    } else {
      throw new BusinessException(
          ErrorConstant.DATA_EXIST_CODE, ErrorConstant.METHOD_PAYMENT_REGISTER_MESSAGE);
    }
  }

  /** {@inheritDoc}. */
  @Override
  public void update(Long id, MethodPaymentRequest request) {
    this.validateMethodPayment(id);
    request.setId(id);
    log.debug("Datos del método de pago a actualizar {}", request);
    this.methodPaymentRepository.save(this.mapping(request));
  }

  /**
   * Valida si el proveedor existe.
   *
   * @param id identificador de la sucursal
   */
  private void validateMethodPayment(Long id) {
    this.methodPaymentRepository
        .findById(id)
        .orElseThrow(
            () -> new NotDataFoundException(ErrorConstant.METHOD_PAYMENT_NOT_FOUND_MESSAGE));
  }

  /**
   * Convierte un entity de tipo {@link MethodPaymentEntity} a un objeto {@link
   * MethodPaymentResponse}
   *
   * @param entity entidad de métodos de pago
   * @return {@link MethodPaymentResponse} respuesta de la API de métodos de pago
   */
  private MethodPaymentResponse mapping(MethodPaymentEntity entity) {
    return new ModelMapper().map(entity, MethodPaymentResponse.class);
  }

  /**
   * Convierte un objeto {@link MethodPaymentRequest} a un entity de tipo {@link
   * MethodPaymentEntity}
   *
   * @param request petición con los datos de los tipos de pago
   * @return {@link MethodPaymentEntity} entidad de métodos de pagos
   */
  private MethodPaymentEntity mapping(MethodPaymentRequest request) {
    return new ModelMapper().map(request, MethodPaymentEntity.class);
  }
}
