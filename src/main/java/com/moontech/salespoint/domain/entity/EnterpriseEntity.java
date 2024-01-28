package com.moontech.salespoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salespoint.commons.constant.DatabaseConstant;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para empresas.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 01 jun., 2023
 */
@Getter
@Setter
@Entity
@Audited
@ToString
@AuditTable(value = "enterprises_aud")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
@Table(name = DatabaseConstant.TABLE_ENTERPRISES)
public class EnterpriseEntity extends AuditableEntity implements Serializable {
  /** Serial. */
  @Serial private static final long serialVersionUID = -8089684905670011173L;

  /** Identificador de la empresa. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** identificador alfanumérico de la empresa. */
  @Column(name = "id_enterprise", length = 20, nullable = false)
  private String idEnterprise;

  /** Nombre de la empresa. */
  @Column(name = DatabaseConstant.PROPERTY_OFFICE_NAME)
  private String name;

  /** Dirección de la empresa. */
  @Column(name = DatabaseConstant.PROPERTY_ADDRESS)
  private String address;

  /** Teléfono de la empresa. */
  @Column(name = DatabaseConstant.PROPERTY_PHONE)
  private String phone;

  /** Status de la empresa. */
  @Column(name = DatabaseConstant.PROPERTY_OFFICE_STATUS)
  private Boolean active = true;

  /** Encargado de la empresa. */
  @Column(name = DatabaseConstant.PROPERTY_OFFICE_MANAGER)
  private String manager;

  /** RFC de la empresa. */
  @Column(name = "rfc", nullable = false, length = 20)
  private String rfc;

  /** Usuarios pertenecientes a la empresa. */
  @JsonIgnore
  @OneToMany(mappedBy = DatabaseConstant.RELATION_ENTERPRISE_USERS_NAME, cascade = CascadeType.ALL)
  @ToString.Exclude
  private Set<UserEntity> users;

  /** Cajas de la sucursal. */
  @JsonIgnore
  @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
  private Set<PointSaleEntity> pointSales;
}
