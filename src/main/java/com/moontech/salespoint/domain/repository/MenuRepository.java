package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.domain.entity.MenuEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para el menú de la aplicación.
 *
 * @author Felipe Monzón
 * @since 31 ago., 2023
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
  /**
   * Consulta el menú de la aplicación por el status.
   *
   * @param status status a consultar
   * @return datos del menú
   */
  List<MenuEntity> findByStatus(Status status);
}
