package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.SupplierService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.SupplierEntity;
import com.moontech.salespoint.domain.repository.SupplierRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.exception.custom.NotDataFoundException;
import com.moontech.salespoint.infrastructure.model.request.SupplierRequest;
import com.moontech.salespoint.infrastructure.model.response.SupplierResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Implementación de las reglas de negocio del módulo de proveedores.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierBusiness implements SupplierService {
  /** Repositorio de proveedores. */
  private final SupplierRepository supplierRepository;

  /** {@inheritDoc}. */
  @Override
  public List<SupplierResponse> retrieve() {
    log.info("Consulta todos los proveedores");
    return this.supplierRepository.findAll().stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  public List<SupplierResponse> findBy(String search) {
    log.debug("Consulta proveedores por {}", search);
    return this.supplierRepository.findBy(search).stream().map(this::mapping).toList();
  }

  /** {@inheritDoc}. */
  @Override
  public SupplierResponse save(SupplierRequest request) {
    SupplierEntity entity = this.supplierRepository.findByRfc(request.getRfc());
    if (ObjectUtils.isEmpty(entity)) {
      SupplierEntity supplier = this.mapping(request);
      supplier.setIdSupplier(Utilities.generateRandomId(Identifier.SUPPLIERS.getCode()));
      supplier.setStatus(Status.ACTIVE);
      log.debug("Proveedor a guardar {}", supplier);
      return this.mapping(this.supplierRepository.save(supplier));
    } else {
      throw new BusinessException(
          ErrorConstant.DATA_EXIST_CODE, ErrorConstant.SUPPLIER_REGISTER_MESSAGE);
    }
  }

  /** {@inheritDoc}. */
  @Override
  public void update(String id, SupplierRequest request) {
    SupplierEntity entity = this.validateSupplier(id);
    request.setId(entity.getId());
    log.debug("Datos del proveedor a actualizar {}", request);
    this.supplierRepository.save(this.mapping(request));
  }

  /** {@inheritDoc}. */
  @Override
  public void delete(String id) {
    log.info("Elimina proveedor {}", id);
    SupplierEntity entity = this.validateSupplier(id);
    entity.setStatus(Status.INACTIVE);
    this.supplierRepository.save(entity);
  }

  /**
   * Valida si el proveedor existe.
   *
   * @param id identificador del proveedor
   * @return entidad del proveedor {@link SupplierEntity}
   */
  private SupplierEntity validateSupplier(String id) {
    return this.supplierRepository
        .findByIdSupplier(id)
        .orElseThrow(() -> new NotDataFoundException(ErrorConstant.SUPPLIER_NOT_FOUND_MESSAGE));
  }

  /**
   * Convierte un entity de tipo {@link SupplierEntity} a un objeto {@link SupplierResponse}
   *
   * @param entity entidad de proveedores
   * @return {@link SupplierResponse} respuesta de la API de proveedores
   */
  private SupplierResponse mapping(SupplierEntity entity) {
    return new ModelMapper().map(entity, SupplierResponse.class);
  }

  /**
   * Convierte un objeto {@link SupplierRequest} a un entity de tipo {@link SupplierEntity}
   *
   * @param request petición con los datos de los proveedores
   * @return {@link SupplierEntity} entidad de proveedores
   */
  private SupplierEntity mapping(SupplierRequest request) {
    return new ModelMapper().map(request, SupplierEntity.class);
  }
}
