package com.moontech.salespoint.domain.entity;

import com.moontech.salespoint.commons.constant.DatabaseConstant;
import com.moontech.salespoint.commons.enums.Status;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditOverride;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entidad para el menú.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 31 ago., 2024
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "menu")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AuditableEntity.class)
public class MenuEntity extends AuditableEntity implements Serializable {
  /** Serial. */
  @Serial private static final long serialVersionUID = 1L;

  /** Identificador del menú. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  /** Ruta del menú. */
  @Column(name = "path", nullable = false, length = 20)
  private String path;

  /** Titulo a mostrar. */
  @Column(name = "title", nullable = false, length = 20)
  private String title;

  /** Icono representativo del menú. */
  @Column(name = "icon", nullable = false, length = 20)
  private String icon;

  /** Propiedad paras el status de la venta. */
  @Enumerated(EnumType.STRING)
  @Column(name = DatabaseConstant.STATUS_PROPERTY, nullable = false, columnDefinition = "varchar")
  private Status status;
}
