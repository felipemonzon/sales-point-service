package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.PointSaleService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.PointSaleEntity;
import com.moontech.salespoint.domain.repository.PointSaleRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.exception.custom.NotDataFoundException;
import com.moontech.salespoint.infrastructure.model.request.PointSaleRequest;
import com.moontech.salespoint.infrastructure.model.response.PointSaleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementación de las reglas de negocio para cajas o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 07 dec., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PointSaleBusiness implements PointSaleService {
  /** Repositorio para punto de venta. */
  private final PointSaleRepository pointSaleRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<PointSaleResponse> retrieve() {
    log.info("Consulta todos los puntos de venta");
    return this.pointSaleRepository.findAll().stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<PointSaleResponse> findBy(String search) {
    log.debug("Consulta punto de venta por {}", search);
    return this.pointSaleRepository.findBy(search).stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public PointSaleResponse save(PointSaleRequest request) {
    PointSaleEntity entity =
        this.pointSaleRepository.findByNameAndStatusAndEnterpriseId(
            request.getName(), Status.ACTIVE, request.getEnterpriseId());
    if (ObjectUtils.isEmpty(entity)) {
      request.setIdPointSale(Utilities.generateRandomId(Identifier.POINT_SALE.getCode()));
      request.setStatus(Status.ACTIVE);
      log.debug("Punto de venta a guardar {}", request);
      return this.mapping(this.pointSaleRepository.save(this.mapping(request)));
    } else {
      throw new BusinessException(
          ErrorConstant.DATA_EXIST_CODE, ErrorConstant.POINT_SALE_REGISTER_MESSAGE);
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void update(String id, PointSaleRequest request) {
    PointSaleEntity entity = this.validatePointSale(id);
    request.setId(entity.getId());
    request.setIdPointSale(id);
    log.debug("Datos del punto de venta a actualizar {}", request);
    this.pointSaleRepository.save(this.mapping(request));
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void delete(String id) {
    log.info("Elimina un punto de venta {}", id);
    PointSaleEntity entity = this.validatePointSale(id);
    entity.setStatus(Status.INACTIVE);
    this.pointSaleRepository.save(entity);
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public PointSaleEntity findById(String id) {
    return this.validatePointSale(id);
  }

  /**
   * Válida si el punto de venta existe.
   *
   * @param id identificador del punto de venta
   * @return entidad del punto de venta {@link PointSaleEntity}
   */
  private PointSaleEntity validatePointSale(String id) {
    return this.pointSaleRepository
        .findByIdPointSale(id)
        .orElseThrow(() -> new NotDataFoundException(ErrorConstant.POINT_SALE_NOT_FOUND_MESSAGE));
  }

  /**
   * Convierte un entity de tipo {@link PointSaleEntity} a un objeto {@link PointSaleResponse}
   *
   * @param entity entidad de punto de venta
   * @return {@link PointSaleResponse} respuesta de la API de punto de venta
   */
  private PointSaleResponse mapping(PointSaleEntity entity) {
    return new ModelMapper().map(entity, PointSaleResponse.class);
  }

  /**
   * Convierte un objeto {@link PointSaleRequest} a un entity de tipo {@link PointSaleEntity}
   *
   * @param request petición con los datos del punto de venta
   * @return {@link PointSaleEntity} entidad de punto de venta
   */
  private PointSaleEntity mapping(PointSaleRequest request) {
    return new ModelMapper().map(request, PointSaleEntity.class);
  }
}
