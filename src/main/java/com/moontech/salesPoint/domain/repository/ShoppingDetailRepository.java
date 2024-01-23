package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.ShoppingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para el detalle de las compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface ShoppingDetailRepository extends JpaRepository<ShoppingDetailEntity, Long> {}
