package com.moontech.salesPoint.domain.repository;

import com.moontech.salesPoint.domain.entity.SellDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para el detalle de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 21 nov., 2023
 */
public interface SellDetailRepository extends JpaRepository<SellDetailEntity, Long> {}
