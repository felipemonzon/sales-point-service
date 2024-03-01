package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.*;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.*;
import com.moontech.salespoint.domain.repository.*;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.DetailRequest;
import com.moontech.salespoint.infrastructure.model.request.ShopRequest;
import com.moontech.salespoint.infrastructure.model.response.ShopResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de las reglas de negocio para las APIS de compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 29 feb., 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShopBusiness implements ShopService {
  /** Repositorio de compras. */
  private final ShopRepository shopRepository;

  /** Repositorio de detalle. */
  private final ShoppingDetailRepository shoppingDetailRepository;

  /** Repositorio de métodos de pago. */
  private final MethodPaymentRepository methodPaymentRepository;

  /** Servicio para consultar stock. */
  private final ProductStockService productStockService;

  /** Servicio de proveedores. */
  private final SupplierService supplierService;

  /** Servicio de punto de venta. */
  private final PointSaleService pointSaleService;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<ShopResponse> retrieve(Pageable pageable) {
    int page = Utilities.getCurrentPage(pageable);
    return this.shopRepository.findAll(PageRequest.of(page, pageable.getPageSize())).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public ShopResponse findById(String invoice) {
    return this.mapping(this.shopRepository.findByIdShop(invoice));
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<ShopResponse> findByDateAndStatus(Pageable pageable, LocalDate date, Status status) {
    int page = Utilities.getCurrentPage(pageable);
    return this.shopRepository
        .findByShopDateAndStatus(PageRequest.of(page, pageable.getPageSize()), date, status)
        .stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public ShopResponse save(ShopRequest request) {
    ShopEntity entity = this.mapping(request);
    log.info("guarda una compra {}", entity);
    ShopEntity shop = this.shopRepository.save(entity);
    entity.setDetails(
        request.getDetails().stream()
            .map(detail -> this.mapping(detail, entity))
            .collect(Collectors.toSet()));
    this.updateStock(entity.getDetails());
    this.shoppingDetailRepository.saveAll(entity.getDetails());
    shop.setDetails(entity.getDetails());
    return this.mapping(shop);
  }

  /**
   * Actualiza el inventario comprado.
   *
   * @param shopDetail detalles de la compra
   */
  private void updateStock(Set<ShoppingDetailEntity> shopDetail) {
    log.info("Actualiza el inventario de los productos comprados");
    shopDetail.forEach(
        detail ->
            this.productStockService.addStock(
                detail.getProduct().getIdProduct(), detail.getPiece()));
  }

  /**
   * Convierte un objeto {@code ShopRequest} a uno de tipo {@code ShopEntity}
   *
   * @param request datos de compras
   * @return {@code ShopEntity}
   */
  private ShopEntity mapping(ShopRequest request) {
    ShopEntity entity = new ShopEntity();
    entity.setStatus(request.getStatus());
    entity.setShopDate(LocalDateTime.now());
    entity.setIdShop(Utilities.generateRandomId(Identifier.TRANSACTIONS.getCode()));
    entity.setMethodPayment(this.methodPayment(request.getMethodPaymentId()));
    entity.setInvoice(request.getInvoice());
    entity.setTotal(request.getTotal());
    entity.setSupplier(this.supplierService.searchById(request.getSupplierId()));
    entity.setPointSale(this.pointSaleService.findById(request.getPointSaleId()));
    return entity;
  }

  /**
   * Convierte un objeto {@code ShopEntity} a uno de tipo {@code ShopResponse}
   *
   * @param entity entidad de compras
   * @return {@code ShopResponse}
   */
  private ShopResponse mapping(ShopEntity entity) {
    return new ModelMapper().map(entity, ShopResponse.class);
  }

  /**
   * Convierte un objeto {@code DetailRequest} a uno de tipo {@code ShoppingDetailEntity}
   *
   * @param request entidad de detalle de compras
   * @return {@code ShoppingDetailEntity}
   */
  private ShoppingDetailEntity mapping(DetailRequest request, ShopEntity shop) {
    ShoppingDetailEntity detail = new ShoppingDetailEntity();
    detail.setPiece(request.getPiece());
    detail.setProduct(this.productStockService.findByIdProduct(request.getProductId()));
    detail.setAmount(
        BigDecimal.valueOf((request.getPiece() * detail.getProduct().getSellPrice().longValue())));
    detail.setShoppingDetailId(new ShoppingDetailId());
    detail.getShoppingDetailId().setProductId(detail.getProduct().getId());
    detail.getShoppingDetailId().setShopId(shop.getId());
    detail.setShop(shop);
    return detail;
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
