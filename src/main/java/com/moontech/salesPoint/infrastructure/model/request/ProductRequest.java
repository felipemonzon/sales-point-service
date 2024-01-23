package com.moontech.salesPoint.infrastructure.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.moontech.salesPoint.commons.constant.FormatConstant;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * Datos de entrada para el producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 26 dec., 2023
 */
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductRequest {
  /** Imagen del producto. */
  @NotNull private MultipartFile image;

  /** Propiedad para el nombre del producto. */
  @NotEmpty
  @Pattern(regexp = FormatConstant.ONLY_NUMBERS_AND_LETTERS_PATTERN)
  private String name;

  /** Propiedad para el precio unitario. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal unitPrice;

  /** Propiedad para el precio venta. */
  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal sellPrice;

  /** Proveedor del producto. */
  @NotEmpty private String supplierId;

  /** Tipo de producto. */
  @NotNull private Long productTypeId;
}
