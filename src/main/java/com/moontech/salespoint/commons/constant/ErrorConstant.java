package com.moontech.salespoint.commons.constant;

/**
 * Constantes para errores de la aplicación.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 15 dec., 2023
 */
public class ErrorConstant {
  /** Código genérico. */
  public static final Integer GENERIC_ERROR_CODE = 9009;

  /** Mensaje genérico. */
  public static final String GENERIC_ERROR_MESSAGE = "Ocurrió un error desconocido";

  /** Código para dato no encontrado. */
  public static final Integer BAD_REQUEST_CODE = 9002;

  /** Código para dato no encontrado. */
  public static final Integer RECORD_NOT_FOUND_CODE = 9003;

  /** Mensaje para dato no encontrado. */
  public static final String RECORD_NOT_FOUND_MESSAGE = "No se encontró el registro";

  /** Código para credenciales inválidas. */
  public static final int INVALID_CREDENTIAL_USER_CODE = 9000;

  /** Mensaje para usuario o contraseña incorrecto. */
  public static final String INVALID_CREDENTIAL_USER_MESSAGE = "Usuario/contraseña inválida";

  /** Código para token incorrecto. */
  public static final int INVALID_TOKEN_CODE = 9001;

  /** Mensaje para token incorrecto. */
  public static final String INVALID_TOKEN_MESSAGE = "Acceso Denegado";

  /** Código para acceso denegado. */
  public static final int ACCESS_DENIED_CODE = 9008;

  /** Mensaje para acceso denegado. */
  public static final String ACCESS_DENIED_MESSAGE = "Acceso Denegado.";

  /** Prefijo pata detalles. */
  public static final String PREFIX_DETAIL_MESSAGE = "Detail";

  /** Código de error para datos existentes. */
  public static final int DATA_EXIST_CODE = 9007;

  /** Código de error para datos no existentes */
  public static final int DATA_NOT_EXIST = 9008;

  /** Mensaje para empresa. */
  public static final String ENTERPRISE_NOT_EXIST = "La empresa no existe";

  /** Mensaje para empresa existente. */
  public static final String ENTERPRISE_EXIST = "La empresa ya existe con nombre {0}";

  /** Mensaje para usuario existente. */
  public static final String USER_EXIST = "El usuario ya esta registrado";

  /** Mensaje para usuario no encontrado. */
  public static final String USER_NOT_FOUND = "El usuario no esta registrado";

  /** Mensaje de unidad no encontrado. */
  public static final String PROFILE_NOT_FOUND_MESSAGE = "El perfil no existe";

  /** Mensaje para unidad previamente registrado. */
  public static final String PROFILE_REGISTER_MESSAGE = "El Perfil ya se Encuentra Registrado";

  /** Mensaje de proveedor no encontrado. */
  public static final String SUPPLIER_NOT_FOUND_MESSAGE = "El proveedor no existe";

  /** Mensaje de caja encontrado. */
  public static final String POINT_SALE_REGISTER_MESSAGE =
      "El Punto de venta ya se encuentra registrado";

  /** Mensaje de caja no encontrado. */
  public static final String POINT_SALE_NOT_FOUND_MESSAGE = "El punto de venta no existe";

  /** Mensaje de método de pago no encontrado. */
  public static final String METHOD_PAYMENT_NOT_FOUND_MESSAGE = "El método de pago no existe";

  /** Mensaje de método de pago encontrado. */
  public static final String METHOD_PAYMENT_REGISTER_MESSAGE =
      "El método de pago ya se encuentra registrado.";

  /** Mensaje de cliente encontrado. */
  public static final String CUSTOMER_REGISTER_MESSAGE = "El Cliente ya se encuentra registrado";

  /** Mensaje para empresa no encontrada. */
  public static final String CUSTOMER_NOT_EXIST = "El cliente no existe";

  /** Mensaje de proveedor encontrado. */
  public static final String SUPPLIER_REGISTER_MESSAGE =
      "El RFC del proveedor ya se encuentra registrado";

  /** Mensaje de deuda activa. */
  public static final String DEBT_ACTIVE_MESSAGE = "El Cliente ya tienen una deuda activa";

  /** Mensaje de deuda no registrada. */
  public static final String DEBT_INVOICE_NOT_REGISTER_MESSAGE = "Folio de deuda no encontrado";

  /** Mensaje para caja con operaciones iniciadas. */
  public static final String POINT_SALE_STARTED = "La caja ya inicio operaciones";

  /** Mensaje para caja con operaciones iniciadas. */
  public static final String POINT_SALE_CLOSED = "La caja ya cerró operaciones";

  /** Mensaje para caja con operaciones no iniciadas. */
  public static final String POINT_SALE_CANNOT_STARTED = "La caja aún no ha iniciado operaciones";

  /** Mensaje para retiro no encontrado. */
  public static final String WITHDRAWAL_NOT_EXIST = "El folio del retiro no se encuentra";

  /** Mensaje para tipo de producto encontrado. */
  public static final String PRODUCT_TYPE_EXIST = "El tipo de producto ya fue registrado";

  /** Mensaje para el tipo de producto no encontrado. */
  public static final String PRODUCT_TYPE_NOT_EXIST = "El tipo de producto no esta registrado";

  /** Mensaje para el producto encontrado. */
  public static final String PRODUCT_EXIST = "El producto ya fue registrado";

  /** Mensaje para producto no encontrado. */
  public static final String PRODUCT_NOT_EXIST = "El producto no existe";

  /** Mensaje para producto registrado en el inventario. */
  public static final String PRODUCT_STOCK_EXIST = "El producto ya fue registrado en el inventario";

  /** Mensaje para producto registrado en el inventario. */
  public static final String PRODUCT_STOCK_NOT_EXIST =
      "El producto no ha sido registrado en el inventario";

  /** Mensaje para folio requerido. */
  public static final String INVOICE_IS_REQUIRED = "El folio es requerido";

  /** Constructor privado. */
  private ErrorConstant() {}
}
