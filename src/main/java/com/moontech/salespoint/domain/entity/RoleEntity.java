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

/**
 * Entidad de la tabla "roles".
 *
 * @author Felipe Monzón
 * @since May 27. 2023
 */
@Getter
@Setter
@ToString
@Table(name = DatabaseConstant.TABLE_ROLES)
@Entity(name = DatabaseConstant.TABLE_ROLES)
public class RoleEntity extends AuditableEntity implements Serializable {
  /** Serial. */
  @Serial private static final long serialVersionUID = 2013073849429702841L;

  /** Identificador del perfil. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /** Descripción del perfil. */
  @Column(name = DatabaseConstant.PROPERTY_ROLE_NAME, length = 30, nullable = false)
  private String name;

  /** Valor del perfil. */
  @Column(name = DatabaseConstant.PROPERTY_ROLE_VALUE, nullable = false, length = 40)
  private String value;

  /** Usuarios pertenecientes al rol. */
  @JsonIgnore
  @ToString.Exclude
  @ManyToMany(mappedBy = "roles")
  private Set<UserEntity> users;
}
