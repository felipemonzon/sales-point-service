package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.ProductStockService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.ProductEntity;
import com.moontech.salespoint.domain.entity.StockTakingEntity;
import com.moontech.salespoint.domain.repository.ProductRepository;
import com.moontech.salespoint.domain.repository.StockTakingRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.StockRequest;
import com.moontech.salespoint.infrastructure.model.response.StockTakingResponse;
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
 * Implementación de las reglas de negocio para el inventario del producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 28 dec., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductStockBusiness implements ProductStockService {
  /** Repositorio para el inventario del producto. */
  private final StockTakingRepository stockTakingRepository;

  /** Repositorio para los productos. */
  private final ProductRepository productRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<StockTakingResponse> retrieve(Pageable pageable) {
    log.info("Consulta todo el inventario");
    int page = Utilities.getCurrentPage(pageable);
    return this.stockTakingRepository.findAll(PageRequest.of(page, pageable.getPageSize())).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public StockTakingResponse save(StockRequest request) {
    ProductEntity product = this.findById(request.getProductId());
    StockTakingEntity entity = this.stockTakingRepository.findByIdProduct(request.getProductId());
    if (ObjectUtils.isEmpty(entity)) {
      StockTakingEntity stock = this.mapping(request);
      stock.setStatus(Status.ACTIVE);
      stock.setProduct(product);
      log.info("guarda el inventario de un producto {}", stock);
      return this.mapping(this.stockTakingRepository.save(stock));
    } else {
      throw new BusinessException(ErrorConstant.DATA_EXIST_CODE, ErrorConstant.PRODUCT_STOCK_EXIST);
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public ProductEntity findByIdProduct(String idProduct) {
    return this.findById(idProduct);
  }

  /**
   * Consulta un producto por su identificador.
   *
   * @param idProduct identificador del producto
   * @return datos del producto
   */
  private ProductEntity findById(String idProduct) {
    ProductEntity product = this.productRepository.findByIdProduct(idProduct);
    if (ObjectUtils.isEmpty(product)) {
      throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.PRODUCT_NOT_EXIST);
    }
    return product;
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public StockTakingResponse findStockByProduct(String idProduct) {
    StockTakingEntity entity = this.searchStock(idProduct);
    return this.mapping(entity);
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public void validateStock(String idProduct, int pieces) {
    StockTakingEntity stockEntity = this.searchStock(idProduct);
    int stock = stockEntity.getStock() - pieces;
    if (stock == 0) {
      log.info("Se agoto el inventario del producto {}", idProduct);
      stockEntity.setStatus(Status.OUT_OF_STOCK);
    } else if (stock < 0) {
      log.info("Inventario insuficiente del producto {}, inventario {}", idProduct, stock);
      throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.PRODUCT_OUT_STOCK);
    }
    stockEntity.setStock(stock);
    log.info("Se actualiza inventario {}", stockEntity);
    this.stockTakingRepository.save(stockEntity);
  }

  /**
   * Consulta el inventario por el identificador del producto.
   *
   * @param idProduct identificador del producto
   * @return inventario encontrado
   */
  private StockTakingEntity searchStock(String idProduct) {
    StockTakingEntity entity = this.stockTakingRepository.findByIdProduct(idProduct);
    if (!ObjectUtils.isEmpty(entity)) {
      log.info("consulta inventario del producto {}", idProduct);
      return entity;
    } else {
      throw new BusinessException(
          ErrorConstant.DATA_NOT_EXIST, ErrorConstant.PRODUCT_STOCK_NOT_EXIST);
    }
  }

  /**
   * Convierte una entidad {@code StockTakingRequest} a uno de tipo {@code StockTakingEntity}
   *
   * @param request objeto de tipo {@link StockRequest}
   * @return entidad de inventario
   */
  private StockTakingEntity mapping(StockRequest request) {
    StockTakingEntity stock = new StockTakingEntity();
    stock.setStock(request.getStock());
    stock.setStockMax(request.getStockMax());
    stock.setStockMin(request.getStockMin());
    return stock;
  }

  /**
   * Convierte una entidad {@code StockTakingEntity} a uno de tipo {@code StockTakingResponse}
   *
   * @param entity objeto de tipo {@link StockTakingEntity}
   * @return objeto de salida de la api de inventario
   */
  private StockTakingResponse mapping(StockTakingEntity entity) {
    return new ModelMapper().map(entity, StockTakingResponse.class);
  }
}
