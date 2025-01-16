package com.moontech.salespoint.application.business;

import com.moontech.salespoint.application.service.MenuService;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.domain.entity.MenuEntity;
import com.moontech.salespoint.domain.entity.ProductTypeEntity;
import com.moontech.salespoint.domain.repository.MenuRepository;
import com.moontech.salespoint.infrastructure.model.response.MenuResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Implementación de las reglas de negocio para el menú de la aplicación.
 *
 * @author Felipe Monzón
 * @since 01 sept, 2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuBusiness implements MenuService {
  /** Repositorio para el menú de la aplicación. */
  private final MenuRepository menuRepository;

  /**
   * Consulta el menú de la aplicación.
   *
   * @return datos del menú de la aplicación
   */
  @Override
  public List<MenuResponse> retrieve() {
    return this.menuRepository.findAll().stream().map(this::mapping).toList();
  }

  /**
   * Consulta el menú activo de la aplicación
   *
   * @return datos del menú de la aplicación
   */
  @Override
  public List<MenuResponse> getActive() {
    return this.menuRepository.findByStatus(Status.ACTIVE).stream().map(this::mapping).toList();
  }

  /**
   * Convierte una entidad {@code ProductTypeEntity} a uno de tipo {@code MenuResponse}
   *
   * @param entity objeto de tipo {@link ProductTypeEntity}
   * @return objeto de salida de la api del menú de la aplicación
   */
  private MenuResponse mapping(MenuEntity entity) {
    return new ModelMapper().map(entity, MenuResponse.class);
  }
}
