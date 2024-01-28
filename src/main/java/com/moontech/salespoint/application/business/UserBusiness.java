package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.UserService;
import com.moontech.salespoint.commons.constant.ApiConstant;
import com.moontech.salespoint.commons.constant.ErrorConstant;
import com.moontech.salespoint.commons.enums.Identifier;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.commons.utilities.Utilities;
import com.moontech.salespoint.domain.entity.RoleEntity;
import com.moontech.salespoint.domain.entity.UserEntity;
import com.moontech.salespoint.domain.repository.UserRepository;
import com.moontech.salespoint.infrastructure.exception.custom.BusinessException;
import com.moontech.salespoint.infrastructure.model.request.UserRequest;
import com.moontech.salespoint.infrastructure.model.response.AuthorityResponse;
import com.moontech.salespoint.infrastructure.model.response.UserResponse;
import com.moontech.salespoint.infrastructure.security.utility.SecurityUtilities;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * Implementación de las reglas de negocio de usuarios.
 *
 * @author Felipe Monzón
 * @since 21 jun., 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserBusiness implements UserService {
  /** Repositorio de usuarios. */
  private final UserRepository userRepository;

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<UserResponse> findAll(Pageable pageable) {
    int page = Utilities.getCurrentPage(pageable);
    log.info("consulta los usuarios por la página {}", page);
    return this.userRepository.findAll(PageRequest.of(page, pageable.getPageSize())).stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional(readOnly = true)
  public List<UserResponse> findAllActive(Pageable pageable) {
    int page = Utilities.getCurrentPage(pageable);
    log.info("consulta los usuarios activos por la página {}", page);
    return this.userRepository
        .findAllByStatus(PageRequest.of(page, pageable.getPageSize()), Status.ACTIVE)
        .stream()
        .map(this::mapping)
        .toList();
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public UserResponse save(UserRequest request) {
    Optional<UserEntity> user = this.userRepository.findByUsername(request.getUsername());
    if (user.isPresent()) {
      throw new BusinessException(ErrorConstant.DATA_EXIST_CODE, ErrorConstant.USER_EXIST);
    } else {
      UserEntity entity = this.mapping(request);
      entity.setStatus(Status.ACTIVE);
      entity.setPassword(SecurityUtilities.passwordEncoder(request.getPassword()));
      entity.setRoles(
          request.getProfiles().stream().map(this::mapping).collect(Collectors.toSet()));
      entity.setIdUser(Utilities.generateRandomId(Identifier.USERS.getCode()));
      log.info("Guarda los datos de un usuario {}", entity);
      return this.mapping(this.userRepository.save(entity));
    }
  }

  /** {@inheritDoc}. */
  @Override
  @Transactional
  public void update(String id, UserRequest request) {
    UserEntity entity = this.userRepository.findByIdUser(id);
    if (ObjectUtils.isEmpty(entity)) {
      throw new BusinessException(
          ErrorConstant.RECORD_NOT_FOUND_CODE, ErrorConstant.USER_NOT_FOUND);
    } else {
      request.setId(entity.getId());
      request.setIdUser(id);
      request.setUsername(entity.getUsername());
      UserEntity user = this.mapping(request);
      user.setRoles(request.getProfiles().stream().map(this::mapping).collect(Collectors.toSet()));
      log.info("Actualiza los datos del cliente {}", user);
      this.userRepository.save(user);
    }
  }

  /**
   * Convierte una entidad {@code UserEntity} a uno de tipo {@code UserResponse}
   *
   * @param entity objeto de tipo {@link UserEntity}
   * @return objeto de salida de la api de usuarios
   */
  private UserResponse mapping(UserEntity entity) {
    UserResponse user = new ModelMapper().map(entity, UserResponse.class);
    user.setProfiles(entity.getRoles().stream().map(this::mapping).collect(Collectors.toSet()));
    user.setDisplayName(user.getFirstName() + ApiConstant.WHITE_SPACE + user.getLastName());
    return user;
  }

  /**
   * Convierte un objeto {@code UserRequest} a uno de tipo {@code UserEntity}
   *
   * @param request objeto de tipo {@link UserRequest}
   * @return objeto de entrada de la api de usuarios
   */
  private UserEntity mapping(UserRequest request) {
    return new ModelMapper().map(request, UserEntity.class);
  }

  /**
   * Convierte una entidad {@code RoleEntity} a uno de tipo {@code AuthorityResponse}
   *
   * @param entity objeto de tipo {@link RoleEntity}
   * @return objeto de salida de la api de perfiles
   */
  private AuthorityResponse mapping(RoleEntity entity) {
    return new ModelMapper().map(entity, AuthorityResponse.class);
  }

  /**
   * Convierte genera una entidad de tipo {@code RoleEntity}
   *
   * @param idProfile identificador del perfil
   * @return entidad para perfiles
   */
  private RoleEntity mapping(Long idProfile) {
    RoleEntity role = new RoleEntity();
    role.setId(idProfile);
    return role;
  }
}
