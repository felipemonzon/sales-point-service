package com.moontech.salesPoint.commons.constant;

/**
 * Constantes de rutas
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2023
 */
public abstract class RoutesConstant {
  /** Ruta base para usuarios. */
  public static final String USERS_BASE_PATH = "${api.uri.domain.users}";

  /** Ruta base para sucursales. */
  public static final String OFFICE_BASE_PATH = "${api.uri.domain.offices}";

  /** Ruta base para proveedores. */
  public static final String SUPPLIER_BASE_PATH = "${api.uri.domain.supplier}";

  /** Ruta base para perfiles. */
  public static final String PROFILE_BASE_PATH = "${api.uri.domain.profiles}";

  /** Ruta base para clientes. */
  public static final String CUSTOMER_BASE_PATH = "${api.uri.domain.customers}";

  /** Ruta base para los métodos de pagos. */
  public static final String METHOD_PAYMENT_BASE_PATH = "${api.uri.domain.methodPayment}";

  /** Ruta base para los puntos de venta. */
  public static final String POINT_SALE_BASE_PATH = "${api.uri.domain.pointSale}";

  /** Ruta base para las deudas. */
  public static final String DEBT_BASE_PATH = "${api.uri.domain.debts}";

  /** Ruta para búsqueda datos. */
  public static final String SEARCH_PATH = "${api.uri.data.search}";

  /** Ruta para modificar datos. */
  public static final String DATA_MODIFIED_PATH = "${api.uri.data.modified}";

  /** Ruta para consultar deudas de clientes. */
  public static final String CUSTOMER_DEBT_PATH = "${api.uri.data.debtCustomerId}";

  /** Ruta para consultar deudas de clientes por status. */
  public static final String CUSTOMER_DEBT_STATUS_PATH = "${api.uri.data.debtCustomerStatus}";

  /** Ruta para iniciar la sesión de la caja. */
  public static final String OPEN_POINT_SALE_PATH = "${api.uri.data.openPointSale}";

  /** Ruta para cerrar sesión de la caja. */
  public static final String CLOSE_POINT_SALE_PATH = "${api.uri.data.closePointSale}";

  /** Ruta para retiros de la caja. */
  public static final String WITHDRAWAL_POINT_SALE_PATH = "${api.uri.data.withdrawalPointSale}";

  /** Ruta para actualizar los retiros de la caja. */
  public static final String UPDATE_WITHDRAWAL_POINT_SALE_PATH =
      "${api.uri.data.withdrawalPointSaleUpdate}";

  /** Ruta base para los tipos de producto. */
  public static final String PRODUCT_TYPE_BASE_PATH = "${api.uri.data.productType}";

  /** Ruta base para las deudas. */
  public static final String PRODUCT_BASE_PATH = "${api.uri.domain.products}";

  /** Ruta base para el stock del producto. */
  public static final String PRODUCT_STOCK_BASE_PATH = "${api.uri.data.productStock}";

  /** Ruta base para los pagos de las deudas. */
  public static final String DEBT_PAYMENTS_BASE_PATH = "${api.uri.domain.payments}";

  /** Ruta base para las compras. */
  public static final String SHP_BASE_PATH = "${api.uri.domain.shops}";

  /** Ruta base para las ventas. */
  public static final String SELL_BASE_PATH = "${api.uri.domain.sells}";

  /** Constructor de la class. */
  private RoutesConstant() {}
}
