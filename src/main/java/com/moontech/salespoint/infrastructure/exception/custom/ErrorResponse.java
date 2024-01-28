package com.moontech.salespoint.infrastructure.exception.custom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.moontech.salespoint.commons.constant.FormatConstant;
import java.time.ZonedDateTime;
import lombok.*;
import org.apache.commons.lang.StringUtils;

/**
 * Respuesta de error.
 *
 * @author Felipe Monzón
 * @since 12 nov., 2023
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  /** Tipo de error. */
  private String type;

  /** Código del error. */
  @Builder.Default private int code = 0;

  /** Detalles del error. */
  @Builder.Default private String message = StringUtils.EMPTY;

  /** Ubicación del error. */
  @Builder.Default private String location = StringUtils.EMPTY;

  /** Información adicional del error. */
  @Builder.Default private String moreInfo = StringUtils.EMPTY;

  /** UUID header de la petición. */
  @Builder.Default private String uuid = StringUtils.EMPTY;

  /** Fecha y hora cuando ocurre el error. */
  @Builder.Default
  @JsonSerialize(using = ZonedDateTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatConstant.ERROR_DATE_PATTERN)
  private ZonedDateTime timestamp = ZonedDateTime.now();
}
