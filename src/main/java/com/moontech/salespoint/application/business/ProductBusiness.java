package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.ProductService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.utilities.ImageUtil;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.ProductEntity;
import com.moontech.salespoint.domain.entity.ProductTypeEntity;
import com.moontech.salespoint.domain.entity.SupplierEntity;
import com.moontech.salespoint.domain.repository.ProductRepository;
import com.moontech.salespoint.domain.repository.ProductTypeRepository;
import com.moontech.salespoint.domain.repository.SupplierRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.ProductRequest;
import com.moontech.salespoint.infrastructure.model.response.ProductResponse;
import java.io.IOException;
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
 * Implementación de las reglas de negocio para el producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 26 dec., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductBusiness implements ProductService {
  /** Repositorio para los productos. */
  private final ProductRepository productRepository;

  /** Repositorio para proveedores. */
  private final SupplierRepository supplierRepository;

  /** Repositorio para tipos de productos. */
  private final ProductTypeRepository productTypeRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<ProductResponse> retrieve(Pageable pageable) {
    int page = Utilities.getCurrentPage(pageable);
    log.info("Consulta los productos por página {}", page);
    return this.productRepository.findAll(PageRequest.of(page, pageable.getPageSize())).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public ProductResponse save(ProductRequest request) {
    try {
      ProductEntity product =
          this.productRepository.findByNameAndSupplierIdSupplier(
              request.getName(), request.getSupplierId());
      if (ObjectUtils.isEmpty(product)) {
        ProductEntity entity = this.mapping(request);
        entity.setIdProduct(Utilities.generateRandomId(Identifier.PRODUCTS.getCode()));
        log.info("Guarda un producto {}", entity);
        return this.mapping(this.productRepository.save(entity));
      } else {
        throw new BusinessException(ErrorConstant.DATA_EXIST_CODE, ErrorConstant.PRODUCT_EXIST);
      }
    } catch (IOException e) {
      throw new BusinessException(ErrorConstant.GENERIC_ERROR_CODE, e.getMessage());
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void update(ProductRequest request, String idProduct) {
    try {
      ProductEntity product = this.productRepository.findByIdProduct(idProduct);
      if (ObjectUtils.isEmpty(product)) {
        throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.PRODUCT_NOT_EXIST);
      } else {
        ProductEntity entity = this.mapping(request);
        entity.setIdProduct(idProduct);
        entity.setId(product.getId());
        log.info("Actualiza los datos de un producto {}", entity);
        this.mapping(this.productRepository.save(entity));
      }
    } catch (IOException e) {
      throw new BusinessException(ErrorConstant.GENERIC_ERROR_CODE, e.getMessage());
    }
  }

  /**
   * Convierte una entidad {@code ProductEntity} a uno de tipo {@code ProductResponse}
   *
   * @param entity objeto de tipo {@link ProductEntity}
   * @return objeto de salida de la api de productos
   */
  private ProductResponse mapping(ProductEntity entity) {
    ProductResponse product = new ModelMapper().map(entity, ProductResponse.class);
    product.setImage(ImageUtil.decompressImage(entity.getImage()));
    return product;
  }

  /**
   * Convierte una entidad {@code ProductRequest} a uno de tipo {@code ProductEntity}
   *
   * @param request objeto de tipo {@link ProductRequest}
   * @return entidad para producto
   */
  private ProductEntity mapping(ProductRequest request) throws IOException {
    ProductEntity entity = new ProductEntity();
    entity.setName(request.getName());
    entity.setImage(ImageUtil.compressImage(request.getImage().getBytes()));
    entity.setUnitPrice(request.getUnitPrice());
    entity.setSellPrice(request.getSellPrice());
    entity.setProductType(this.findProductType(request.getProductTypeId()));
    entity.setSupplier(this.findSupplier(request.getSupplierId()));
    return entity;
  }

  /**
   * Consulta los datos de un proveedor por su identificador.
   *
   * @param idSupplier identificador del proveedor
   * @return datos del proveedor
   */
  private SupplierEntity findSupplier(String idSupplier) {
    return this.supplierRepository
        .findByIdSupplier(idSupplier)
        .orElseThrow(
            () ->
                new BusinessException(
                    ErrorConstant.DATA_NOT_EXIST, ErrorConstant.SUPPLIER_NOT_FOUND_MESSAGE));
  }

  /**
   * Consulta los datos del tipo de producto.
   *
   * @param idProductType identificador del tipo de producto
   * @return datos del tipo de producto
   */
  private ProductTypeEntity findProductType(Long idProductType) {
    return this.productTypeRepository
        .findById(idProductType)
        .orElseThrow(
            () ->
                new BusinessException(
                    ErrorConstant.DATA_NOT_EXIST, ErrorConstant.PRODUCT_TYPE_NOT_EXIST));
  }
}
