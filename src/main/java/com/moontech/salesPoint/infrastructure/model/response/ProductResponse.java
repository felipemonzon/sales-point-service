package com.moontech.salesPoint.infrastructure.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta para el producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 26 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponse {
  /** Propiedad para el identificador generado por el sistema. */
  private String idProduct;

  /** Imagen del producto. */
  @JsonIgnore private byte[] image;

  /** Propiedad para el nombre del producto. */
  private String name;

  /** Propiedad para el precio unitario. */
  private BigDecimal unitPrice;

  /** Propiedad para el precio venta. */
  private BigDecimal sellPrice;

  /** Proveedor del producto. */
  private SupplierResponse supplier;

  /** Tipo de producto. */
  private ProductTypeResponse productType;
}
