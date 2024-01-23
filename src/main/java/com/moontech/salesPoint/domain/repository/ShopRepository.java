package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para las compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {}
