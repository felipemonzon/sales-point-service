package com.moontech.salespoint.commons.utilities;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import lombok.extern.slf4j.Slf4j;

/**
 * Utilería para imágenes.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 20 dec., 2023
 */
@Slf4j
public class ImageUtil {
  /**
   * Comprime una imagen en arreglo de bytes.
   *
   * @param data arreglo de bytes
   * @return arreglo de bytes
   */
  public static byte[] compressImage(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setLevel(Deflater.BEST_COMPRESSION);
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024];
    while (!deflater.finished()) {
      int size = deflater.deflate(tmp);
      outputStream.write(tmp, 0, size);
    }
    try {
      outputStream.close();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return outputStream.toByteArray();
  }

  public static byte[] decompressImage(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(tmp);
        outputStream.write(tmp, 0, count);
      }
      outputStream.close();
    } catch (Exception exception) {
      log.error(exception.getMessage(), exception);
    }
    return outputStream.toByteArray();
  }

  private ImageUtil() {}
}
