package com.moontech.salesPoint.application.business;

import com.moontech.salesPoint.application.service.CustomerService;
import com.moontech.salesPoint.application.service.PointSaleService;
import com.moontech.salesPoint.application.service.ProductStockService;
import com.moontech.salesPoint.application.service.SellService;
import com.moontech.salesPoint.commons.constant.ErrorConstant;
import com.moontech.salesPoint.commons.enums.Identifier;
import com.moontech.salesPoint.commons.enums.Status;
import com.moontech.salesPoint.commons.utilities.Utilities;
import com.moontech.salesPoint.domain.entity.MethodPaymentEntity;
import com.moontech.salesPoint.domain.entity.SellDetailEntity;
import com.moontech.salesPoint.domain.entity.SellDetailId;
import com.moontech.salesPoint.domain.entity.SellEntity;
import com.moontech.salesPoint.domain.repository.MethodPaymentRepository;
import com.moontech.salesPoint.domain.repository.SellDetailRepository;
import com.moontech.salesPoint.domain.repository.SellRepository;
import com.moontech.salesPoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salesPoint.infrastructure.model.request.SellDetailRequest;
import com.moontech.salesPoint.infrastructure.model.request.SellRequest;
import com.moontech.salesPoint.infrastructure.model.response.SellResponse;
import java.math.BigDecimal;
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
 * Implementación de las reglas de negocio para las APIS de ventas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 10 jan., 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SellBusiness implements SellService {
  /** Repositorio de ventas. */
  private final SellRepository sellRepository;

  /** Repositorio de detalle. */
  private final SellDetailRepository sellDetailRepository;

  /** Repositorio de métodos de pago. */
  private final MethodPaymentRepository methodPaymentRepository;

  /** Servicio para consultar stock. */
  private final ProductStockService productStockService;

  /** Servicio de clientes. */
  private final CustomerService customerService;

  /** Servicio de punto de venta. */
  private final PointSaleService pointSaleService;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<SellResponse> retrieve(Pageable pageable) {
    int page = Utilities.getCurrentPage(pageable);
    return this.sellRepository.findAll(PageRequest.of(page, pageable.getPageSize())).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public SellResponse findById(String invoice) {
    return this.mapping(this.sellRepository.findByIdSell(invoice));
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<SellResponse> findByDateAndStatus(
      Pageable pageable, LocalDateTime date, Status status) {
    int page = Utilities.getCurrentPage(pageable);
    return this.sellRepository
        .findBySellDateAndStatus(PageRequest.of(page, pageable.getPageSize()), date, status)
        .stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public SellResponse save(SellRequest request) {
    SellEntity entity = this.mapping(request);
    log.info("guarda una venta {}", entity);
    SellEntity sell = this.sellRepository.save(entity);
    entity.setDetails(
        request.getDetails().stream()
            .map(detail -> this.mapping(detail, entity.getId()))
            .collect(Collectors.toSet()));
    this.validateStock(entity.getDetails());
    this.sellDetailRepository.saveAll(entity.getDetails());
    return this.mapping(sell);
  }

  /**
   * Valida el inventario disponible.
   *
   * @param sellDetail detalles de la venta
   */
  private void validateStock(Set<SellDetailEntity> sellDetail) {
    log.info("Valida el inventario de los productos a vender");
    sellDetail.forEach(
        detail ->
            this.productStockService.validateStock(
                detail.getProduct().getIdProduct(), detail.getPiece()));
  }

  /**
   * Convierte un objeto {@code SellRequest} a uno de tipo {@code SellEntity}
   *
   * @param request datos de ventas
   * @return {@code SellEntity}
   */
  private SellEntity mapping(SellRequest request) {
    SellEntity entity = new SellEntity();
    entity.setStatus(request.getStatus());
    entity.setIdSell(Utilities.generateRandomId(Identifier.TRANSACTIONS.getCode()));
    entity.setMethodPayment(this.methodPayment(request.getMethodPaymentId()));
    entity.setInvoice(request.getInvoice());
    entity.setTotal(request.getTotal());
    entity.setCustomer(this.customerService.searchById(request.getCustomerId()));
    entity.setPointSale(this.pointSaleService.findById(request.getPointSaleId()));
    return entity;
  }

  /**
   * Convierte un objeto {@code SellEntity} a uno de tipo {@code SellResponse}
   *
   * @param entity entidad de ventas
   * @return {@code SellResponse}
   */
  private SellResponse mapping(SellEntity entity) {
    return new ModelMapper().map(entity, SellResponse.class);
  }

  /**
   * Convierte un objeto {@code SellDetailRequest} a uno de tipo {@code SellDetailEntity}
   *
   * @param request entidad de detalle de ventas
   * @return {@code SellDetailEntity}
   */
  private SellDetailEntity mapping(SellDetailRequest request, Long idSell) {
    SellDetailEntity detail = new SellDetailEntity();
    detail.setPiece(request.getPiece());
    detail.setProduct(this.productStockService.findByIdProduct(request.getProductId()));
    detail.setAmount(
        BigDecimal.valueOf((request.getPiece() * detail.getProduct().getSellPrice().longValue())));
    detail.setSellDetailId(new SellDetailId());
    detail.getSellDetailId().setProductId(detail.getProduct().getId());
    detail.getSellDetailId().setSellId(idSell);
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
