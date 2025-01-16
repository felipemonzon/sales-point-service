package com.moontech.salespoint.application.service;

import com.moontech.salespoint.infrastructure.model.response.MenuResponse;
import java.util.List;

/**
 * Reglas de negocio para el menú de la aplicación.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 01 sept., 2024
 */
public interface MenuService {
  /**
   * Consulta el menú de la aplicación.
   *
   * @return datos del menú de la aplicación
   */
  List<MenuResponse> retrieve();

  /**
   * Consulta el menú activo de la aplicación
   *
   * @return datos del menú de la aplicación
   */
  List<MenuResponse> getActive();
}
