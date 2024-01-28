package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.EnterpriseService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.EnterpriseEntity;
import com.moontech.salespoint.domain.repository.EnterpriseRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.EnterpriseRequest;
import com.moontech.salespoint.infrastructure.model.response.EnterpriseResponse;
import java.text.MessageFormat;
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
 * @since 09 jun., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseBusiness implements EnterpriseService {
  /** Repositorio de empresas. */
  private final EnterpriseRepository enterpriseRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<EnterpriseResponse> retrieve(Pageable pageable) {
    int page = Utilities.getCurrentPage(pageable);
    log.info("consulta las empresas por la página {}", page);
    return this.enterpriseRepository.findAll(PageRequest.of(page, pageable.getPageSize())).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public EnterpriseResponse save(EnterpriseRequest request) {
    EnterpriseEntity enterprise = this.enterpriseRepository.findByName(request.getName());
    if (ObjectUtils.isEmpty(enterprise)) {
      EnterpriseEntity entity = this.mapping(request);
      entity.setIdEnterprise(Utilities.generateRandomId(Identifier.ENTERPRISES.getCode()));
      entity.setActive(Boolean.TRUE);
      log.info("Guarda los datos de una empresa {}", entity);
      return this.mapping(this.enterpriseRepository.save(entity));
    } else {
      throw new BusinessException(
          ErrorConstant.DATA_EXIST_CODE,
          MessageFormat.format(ErrorConstant.ENTERPRISE_EXIST, request.getName()));
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void update(EnterpriseRequest request, String id) {
    EnterpriseEntity entity = this.enterpriseRepository.findByIdEnterprise(id);
    if (ObjectUtils.isEmpty(entity)) {
      throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.ENTERPRISE_NOT_EXIST);
    } else {
      request.setId(entity.getId());
      request.setIdEnterprise(id);
      log.info("Actualiza los datos de la empresa {}", request);
      this.enterpriseRepository.save(this.mapping(request));
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void delete(String id) {
    EnterpriseEntity entity = this.enterpriseRepository.findByIdEnterprise(id);
    if (ObjectUtils.isEmpty(entity)) {
      throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.ENTERPRISE_NOT_EXIST);
    } else {
      entity.setActive(Boolean.FALSE);
      log.info("Elimina los datos de la empresa {}", entity);
      this.enterpriseRepository.save(entity);
    }
  }

  /**
   * Convierte un objeto {@code EnterpriseRequest} a uno de tipo {@code EnterpriseEntity}
   *
   * @param request objeto de tipo {@link EnterpriseRequest}
   * @return objeto de entrada de la api de empresas
   */
  private EnterpriseEntity mapping(EnterpriseRequest request) {
    return new ModelMapper().map(request, EnterpriseEntity.class);
  }

  /**
   * Convierte una entidad {@code EnterpriseEntity} a uno de tipo {@code EnterpriseResponse}
   *
   * @param entity objeto de tipo {@link EnterpriseEntity}
   * @return objeto de salida de la api de empresas
   */
  private EnterpriseResponse mapping(EnterpriseEntity entity) {
    return new ModelMapper().map(entity, EnterpriseResponse.class);
  }
}
