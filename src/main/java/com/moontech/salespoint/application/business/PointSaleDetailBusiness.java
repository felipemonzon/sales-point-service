package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.PointSaleDetailService;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.*;
import com.moontech.salespoint.domain.repository.PointSaleDetailRepository;
import com.moontech.salespoint.domain.repository.PointSaleRepository;
import com.moontech.salespoint.domain.repository.UserRepository;
import com.moontech.salespoint.domain.repository.WithdrawalPointSaleRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.ClosePointSaleRequest;
import com.moontech.salespoint.infrastructure.model.request.OpenPointSaleRequest;
import com.moontech.salespoint.infrastructure.model.request.WithdrawalPointSaleRequest;
import com.moontech.salespoint.infrastructure.model.response.PointSaleDetailResponse;
import com.moontech.salespoint.infrastructure.model.response.WithdrawalPointSaleResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementación operaciones para caja o puntos de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 19 dec., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PointSaleDetailBusiness implements PointSaleDetailService {
  /** Repositorio para las operaciones de las cajas. */
  private final PointSaleDetailRepository pointSaleDetailRepository;

  /** Repositorio para los datos de la caja o pos. */
  private final PointSaleRepository pointSaleRepository;

  /** Repositorio de usuarios. */
  private final UserRepository userRepository;

  /** Repositorio para retiros parciales de caja. */
  private final WithdrawalPointSaleRepository withdrawalPointSaleRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public PointSaleDetailResponse openPointSale(OpenPointSaleRequest request) {
    PointSaleDetailEntity posEntity =
        this.pointSaleDetailRepository.findByPointSaleIdPointSaleAndStatus(
            request.getPointSale(), Status.OPENED);
    if (ObjectUtils.isEmpty(posEntity)) {
      PointSaleDetailEntity data = this.mapping(request);
      log.info("Iniciando operaciones con la caja {}", data);
      return this.mapping(this.pointSaleDetailRepository.save(data));
    } else {
      throw new BusinessException(ErrorConstant.DATA_EXIST_CODE, ErrorConstant.POINT_SALE_STARTED);
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public PointSaleDetailResponse closePointSale(ClosePointSaleRequest request) {
    PointSaleDetailEntity posEntity =
        this.pointSaleDetailRepository.findByPointSaleIdPointSaleAndStatus(
            request.getPointSale(), Status.OPENED);
    if (!ObjectUtils.isEmpty(posEntity)) {
      this.mapping(request, posEntity);
      log.info("Cerrando operaciones con la caja {}", posEntity);
      return this.mapping(this.pointSaleDetailRepository.save(posEntity));
    } else {
      throw new BusinessException(ErrorConstant.DATA_EXIST_CODE, ErrorConstant.POINT_SALE_CLOSED);
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public WithdrawalPointSaleResponse withdrawal(WithdrawalPointSaleRequest request) {
    this.validateOpenPointSale(request.getPointSaleId());
    WithdrawalPointSaleEntity with = this.mapping(request);
    log.info("Retiro de la caja {}", with);
    return this.mapping(this.withdrawalPointSaleRepository.save(with));
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public WithdrawalPointSaleResponse updateWithdrawal(
      WithdrawalPointSaleRequest request, String withdrawalId) {
    this.validateOpenPointSale(request.getPointSaleId());

    WithdrawalPointSaleEntity withdrawal =
        this.withdrawalPointSaleRepository.findByWithdrawalPointSaleIdIdWithdrawals(withdrawalId);
    if (ObjectUtils.isEmpty(withdrawal)) {
      throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.WITHDRAWAL_NOT_EXIST);
    }
    withdrawal.setDescription(request.getDescription());
    withdrawal.setAmount(request.getAmount());
    log.info("Actualiza los datos del retiro de la caja {}", withdrawal);
    return this.mapping(this.withdrawalPointSaleRepository.save(withdrawal));
  }

  /**
   * Consulta si la caja ya inicio operaciones.
   *
   * @param pointSaleId identificador de la caja
   */
  private void validateOpenPointSale(String pointSaleId) {
    PointSaleDetailEntity posEntity =
        this.pointSaleDetailRepository.findByPointSaleIdPointSaleAndStatus(
            pointSaleId, Status.OPENED);
    if (ObjectUtils.isEmpty(posEntity)) {
      throw new BusinessException(
          ErrorConstant.DATA_EXIST_CODE, ErrorConstant.POINT_SALE_CANNOT_STARTED);
    }
  }

  /**
   * Convierte un objeto {@link WithdrawalPointSaleRequest} a un entity de tipo {@link
   * WithdrawalPointSaleEntity}
   *
   * @param request petición con los datos del retiro de la caja
   * @return entidad para el retiro de efectivo de la caja
   */
  private WithdrawalPointSaleEntity mapping(WithdrawalPointSaleRequest request) {
    WithdrawalPointSaleEntity entity = new WithdrawalPointSaleEntity();
    entity.setPointSale(this.pointSaleData(request.getPointSaleId()));
    entity.setAmount(request.getAmount());
    entity.setDescription(request.getDescription());
    entity.setWithdrawalsDate(LocalDateTime.now());
    entity.setWithdrawalPointSaleId(new WithdrawalPointSaleId());
    entity
        .getWithdrawalPointSaleId()
        .setIdWithdrawals(Utilities.generateRandomId(Identifier.WITHDRAWAL.getCode()));
    return entity;
  }

  /**
   * Consulta un usuario por su identificador.
   *
   * @param idUser identificador del usuario
   * @return datos del usuario
   */
  private UserEntity userData(String idUser) {
    UserEntity user = this.userRepository.findByIdUser(idUser);
    if (ObjectUtils.isEmpty(user)) {
      throw new BusinessException(ErrorConstant.DATA_NOT_EXIST, ErrorConstant.USER_NOT_FOUND);
    }
    return user;
  }

  /**
   * Consulta los datos del punto de venta.
   *
   * @param idPointSale identificador del punto de venta
   * @return datos del punto de venta
   */
  private PointSaleEntity pointSaleData(String idPointSale) {
    return this.pointSaleRepository
        .findByIdPointSale(idPointSale)
        .orElseThrow(
            () ->
                new BusinessException(
                    ErrorConstant.DATA_NOT_EXIST, ErrorConstant.POINT_SALE_NOT_FOUND_MESSAGE));
  }

  /**
   * Convierte un objeto {@link ClosePointSaleRequest} a un entity de tipo {@link
   * PointSaleDetailEntity}
   *
   * @param request petición con los datos de la operación de la caja
   */
  private void mapping(ClosePointSaleRequest request, PointSaleDetailEntity posEntity) {
    posEntity.setPointSale(this.pointSaleData(request.getPointSale()));
    posEntity.setCloseUser(this.userData(request.getCloseUser()));
    posEntity.setStatus(Status.CLOSED);
    posEntity.setClosePointSaleDate(LocalDateTime.now());
  }

  /**
   * Convierte un objeto {@link OpenPointSaleRequest} a un entity de tipo {@link
   * PointSaleDetailEntity}
   *
   * @param request petición con los datos de la operación de la caja
   * @return {@link PointSaleDetailEntity} entidad de operaciones de cajas
   */
  private PointSaleDetailEntity mapping(OpenPointSaleRequest request) {
    PointSaleDetailEntity data = new PointSaleDetailEntity();
    data.setPointSale(this.pointSaleData(request.getPointSale()));
    data.setOpenAmount(request.getOpenAmount());
    data.setOpenUser(this.userData(request.getOpenUser()));
    data.setStatus(Status.OPENED);
    data.setOpenPointSaleDate(LocalDateTime.now());
    return data;
  }

  /**
   * Convierte un objeto {@link WithdrawalPointSaleEntity} a un entity de tipo {@link
   * WithdrawalPointSaleResponse}
   *
   * @param entity petición con los datos del retiro de la caja
   * @return {@link WithdrawalPointSaleResponse} entidad de retiros de cajas
   */
  private WithdrawalPointSaleResponse mapping(WithdrawalPointSaleEntity entity) {
    return new ModelMapper().map(entity, WithdrawalPointSaleResponse.class);
  }

  /**
   * Convierte un objeto {@link PointSaleDetailEntity} a un entity de tipo {@link
   * PointSaleDetailResponse}
   *
   * @param entity petición con los datos de la operación de la caja
   * @return {@link PointSaleDetailResponse} entidad de operaciones de cajas
   */
  private PointSaleDetailResponse mapping(PointSaleDetailEntity entity) {
    return new ModelMapper().map(entity, PointSaleDetailResponse.class);
  }
}
