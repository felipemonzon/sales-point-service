package com.moontech.salespoint.infrastructure.apis;

import com.moontech.salespoint.application.service.MenuService;
import com.moontech.salespoint.commons.constant.RoutesConstant;
import com.moontech.salespoint.infrastructure.model.response.MenuResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APIS del menú de la aplicación
 *
 * @author Felipe Monzón
 * @since 01 sept., 2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = RoutesConstant.MENU_BASE_PATH)
public class MenuController {
  /** Servicio del menu. */
  private final MenuService menuService;

  /**
   * Consulta el menú de la aplicación.
   *
   * @return datos del menú
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<MenuResponse>> retrieve() {
    return ResponseEntity.ok(this.menuService.retrieve());
  }

  /**
   * Consulta el menú activo de la aplicación.
   *
   * @return datos del menú
   */
  @GetMapping(path = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<MenuResponse>> findByActiveMenu() {
    return ResponseEntity.ok(this.menuService.getActive());
  }
}
