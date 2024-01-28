package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.ProductTypeService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.domain.entity.ProductTypeEntity;
import com.moontech.salespoint.domain.repository.ProductTypeRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.ProductTypeRequest;
import com.moontech.salespoint.infrastructure.model.response.ProductTypeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementación para tipos de productos.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 dec., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTypeBusiness implements ProductTypeService {
  /** Repositorio para tipos de producto. */
  private final ProductTypeRepository productTypeRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<ProductTypeResponse> retrieve() {
    log.info("Consulta los tipos de productos");
    return this.productTypeRepository.findAll().stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public ProductTypeResponse save(ProductTypeRequest request) {
    ProductTypeEntity type = this.productTypeRepository.findByName(request.getName());
    if (!ObjectUtils.isEmpty(type)) {
      throw new BusinessException(ErrorConstant.DATA_EXIST_CODE, ErrorConstant.PRODUCT_TYPE_EXIST);
    } else {
      ProductTypeEntity entity = this.mapping(request);
      log.info("Guarda un tipo de producto {}", entity);
      return this.mapping(this.productTypeRepository.save(entity));
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void update(ProductTypeRequest request, Long id) {
    this.productTypeRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    ErrorConstant.RECORD_NOT_FOUND_CODE, ErrorConstant.PRODUCT_TYPE_NOT_EXIST));
    request.setId(id);
    log.info("Actualiza un tipo de producto {}", request);
    this.productTypeRepository.save(this.mapping(request));
  }

  /**
   * Convierte una entidad {@code ProductTypeEntity} a uno de tipo {@code ProductTypeResponse}
   *
   * @param entity objeto de tipo {@link ProductTypeEntity}
   * @return objeto de salida de la api de tipos de productos
   */
  private ProductTypeResponse mapping(ProductTypeEntity entity) {
    return new ModelMapper().map(entity, ProductTypeResponse.class);
  }

  /**
   * Convierte una objeto {@code ProductTypeRequest} a uno de tipo {@code ProductTypeEntity}
   *
   * @param request objeto de tipo {@link ProductTypeRequest}
   * @return entidad para tipos de producto
   */
  private ProductTypeEntity mapping(ProductTypeRequest request) {
    return new ModelMapper().map(request, ProductTypeEntity.class);
  }
}
