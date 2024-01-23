package com.moontech.salesPoint.infrastructure.property;

import com.moontech.salesPoint.commons.constant.ApiConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Propiedades externalizadas de la aplicación.
 *
 * @author Felipe Monzón
 * @since May 26, 2023
 */
@Data
@Configuration
@ConfigurationProperties(prefix = ApiConstant.PROPERTY_PREFIX)
public class PropertiesConstant {
  /** Ruta para la intercepción de la petición. */
  private String interceptorPath;
}
