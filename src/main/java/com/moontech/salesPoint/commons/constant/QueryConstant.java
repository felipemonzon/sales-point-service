package com.moontech.salesPoint.commons.constant;

/**
 * Consultas estáticas para las consultas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 nov., 2022
 */
public class QueryConstant {
  /**
   * Consulta usuario por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>E-mail
   *   <li>teléfono
   * </ul>
   */
  public static final String FIND_USER_BY =
      "SELECT u.id, u.username, u.first_name, u.last_name, u.email, u.phone, u.id_office, u.created_by, "
          + "u.created_date, u.updated_by, u.updated_date, u.password, u.genre, u.status "
          + "FROM users u "
          + "WHERE u.first_name LIKE %:search% "
          + "OR u.last_name LIKE %:search% "
          + "OR u.email LIKE %:search% "
          + "OR u.phone LIKE %:search% ";

  /**
   * Consulta sucursal por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Teléfono
   *   <li>Coordenadas
   *   <li>Dirección
   * </ul>
   */
  public static final String FIND_OFFICES_BY =
      "SELECT b.id, b.name, b.phone, b.address, b.active, b.manager, created_by, created_date, updated_by, updated_date "
          + "FROM offices b "
          + "WHERE b.name LIKE %:search% "
          + "OR b.phone LIKE %:search% "
          + "OR b.address LIKE %:search%";

  /**
   * Consulta perfil por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Valor
   * </ul>
   */
  public static final String FIND_PROFILES_BY =
      "SELECT r.id, r.name, r.value, r.created_user, r.created_date, r.last_modified_user, r.last_modified_date "
          + "FROM roles r "
          + "WHERE r.name LIKE %:search% "
          + "OR r.value LIKE %:search%";

  /**
   * Consulta proveedores por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Teléfono
   *   <li>Dirección
   * </ul>
   */
  public static final String FIND_SUPPLIERS_BY =
      "SELECT s.id, s.name, s.phone, s.address, s.enterprise, s.rfc, s.status, s.comments, s.created_user, s.created_date, s.last_modified_user, s.last_modified_date "
          + "FROM suppliers s "
          + "WHERE s.name LIKE %:search% "
          + "OR s.phone LIKE %:search% "
          + "OR s.address LIKE %:search%";

  /**
   * Consulta método de pago por:
   *
   * <ul>
   *   <li>Descripción
   * </ul>
   */
  public static final String FIND_METHOD_PAYMENT_BY =
      "SELECT s.id, s.description, s.created_user, s.created_date, s.last_modified_user, s.last_modified_date "
          + "FROM method_payment s "
          + "WHERE s.description LIKE %:search% ";

  /**
   * Consulta puntos de venta por:
   *
   * <ul>
   *   <li>Nombre
   *   <li>Identificador
   *   <li>status
   * </ul>
   */
  public static final String FIND_POINT_SALE_BY =
      "SELECT s.id, s.id_point_sale, s.name, s.status, s.id_enterprise, s.created_user, s.created_date, s.last_modified_user, s.last_modified_date "
          + "FROM point_sales s "
          + "WHERE s.name LIKE %:search% "
          + "AND s.id_point_sale = :search"
          + "AND s.status = :search";

  /** Parámetro de búsqueda. */
  public static final String SEARCH_PARAMETER = "search";

  /** constructor privado. */
  private QueryConstant() {}
}
