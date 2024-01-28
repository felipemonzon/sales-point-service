package com.moontech.salespoint.infrastructure.property;

import com.moontech.salespoint.commons.constant.ApiConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Propiedades para la configuración de swagger.
 *
 * @author Felipe Monzón
 * @since May 26, 2023
 */
@Data
@Configuration
@ConfigurationProperties(prefix = ApiConstant.SWAGGER_PROPERTIES)
public class SwaggerProperties {
  /** Configuración de paquetes base de Swagger. */
  private String basePackage;
  /** Titulo del Swagger. */
  private String title;
  /** Descripción de la aplicación. */
  private String descriptionApi;
  /** Versión del servicio. */
  private String version;
  /** Nombre del desarrollador. */
  private String nameDeveloper;
  /** URL de Contacto. */
  private String contactUrl;
  /** Email del desarrollador. */
  private String emailDeveloper;
  /** URI de la licencia. */
  private String licenceUrl;
  /** Tipo de licencia. */
  private String licenceType;
}
