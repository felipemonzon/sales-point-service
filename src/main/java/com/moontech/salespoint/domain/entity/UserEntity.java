package com.moontech.salespoint.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moontech.salespoint.commons.constant.DatabaseConstant;
import com.moontech.salespoint.commons.enums.Genre;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.persistence.*;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para usuario.
 *
 * @author Felipe Monzón
 * @since May 27, 2023
 */
@Getter
@Setter
@Audited
@ToString
@AuditTable(value = "users_aud")
@Entity(name = DatabaseConstant.USERS_TABLE)
@Table(name = DatabaseConstant.USERS_TABLE)
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class UserEntity extends AuditableEntity {
  /** Identificador del usuario. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** Identificador del usuario. */
  @Column(name = "idUser", nullable = false, length = 20)
  private String idUser;

  /** Propiedad para el nombre del usuario. */
  @NaturalId
  @Column(name = DatabaseConstant.USERNAME_PROPERTY, nullable = false, length = 20, unique = true)
  private String username;

  /** Propiedad primer nombre. */
  @Column(name = DatabaseConstant.FIRST_NAME_PROPERTY, nullable = false)
  private String firstName;

  /** Propiedad segundo nombre. */
  @Column(name = DatabaseConstant.LAST_NAME_PROPERTY, nullable = false)
  private String lastName;

  /** Propiedad para el correo. */
  @Column(name = DatabaseConstant.EMAIL_PROPERTY, nullable = false)
  private String email;

  /** Propiedad para el celular. */
  @Column(name = DatabaseConstant.CELLPHONE_PROPERTY, nullable = false)
  private String cel;

  /** Propiedad para el género. */
  @Enumerated(EnumType.ORDINAL)
  private Genre genre;

  /** Propiedad para la contraseña. */
  @Column(name = DatabaseConstant.PASSWORD_PROPERTY, nullable = false, length = 200)
  private String password;

  /** Propiedad para el status del usuario. */
  @Enumerated(EnumType.STRING)
  @Column(name = DatabaseConstant.STATUS_PROPERTY, nullable = false)
  private Status status;

  /** Propiedad para el rol del usuario. */
  @NotAudited
  @ManyToMany
  @ToString.Exclude
  @EqualsAndHashCode.Include
  @JoinTable(
      name = DatabaseConstant.RELATION_USER_ROLES_NAME,
      joinColumns = {@JoinColumn(name = DatabaseConstant.PROPERTY_USER_ID)},
      inverseJoinColumns = {@JoinColumn(name = DatabaseConstant.PROPERTY_ROL_ID)})
  @Column(name = DatabaseConstant.PROPERTY_ROL_ID, nullable = false)
  @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
  private Set<RoleEntity> roles;

  /** Empresa del empleado. */
  @NotAudited
  @JsonIgnore
  @ManyToOne
  @JoinColumn(
      name = DatabaseConstant.PROPERTY_ENTERPRISE_ID,
      referencedColumnName = DatabaseConstant.PROPERTY_USER_ENTERPRISE)
  @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
  private EnterpriseEntity enterprise;
}
