-- salesPoint.point_sale_details_seq definition

CREATE TABLE IF NOT EXISTS `point_sale_details_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO point_sale_details_seq (next_val)
VALUES(1);

-- salesPoint.point_sale_details definition

CREATE TABLE IF NOT EXISTS `point_sale_details` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `id_point_sale` BIGINT NOT NULL,
    `money` DECIMAL(11,2) NOT NULL,
    `open_date` datetime(6) NOT NULL,
    `open_user` BIGINT NOT NULL,
    `close_date` datetime(6) DEFAULT NULL,
    `close_user` BIGINT DEFAULT NULL,
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `point_sale_FK` FOREIGN KEY (`id_point_sale`) REFERENCES `point_sales` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.point_sale_details_aud definition

CREATE TABLE IF NOT EXISTS `point_sale_details_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id` bigint NOT NULL,
    `id_point_sale` BIGINT NOT NULL,
    `money` DECIMAL(11,2) NOT NULL,
    `open_date` datetime(6) NOT NULL,
    `open_user` BIGINT NOT NULL,
    `close_date` datetime(6) DEFAULT NULL,
    `close_user` BIGINT DEFAULT NULL,
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`rev_id`,`id_point_sale`),
    CONSTRAINT `FK_detail_point_sale` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.withdrawals_point_sales_seq definition

CREATE TABLE IF NOT EXISTS `withdrawals_point_sales_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO withdrawals_point_sales_seq (next_val)
VALUES(1);

-- salesPoint.withdrawals_point_sales definition

CREATE TABLE IF NOT EXISTS `withdrawals_point_sales` (
    `id_withdrawals` VARCHAR(100) COLLATE utf8mb4_general_ci NOT NULL,
    `id_point_sale` BIGINT NOT NULL,
    `withdrawals_date` datetime(6) DEFAULT NULL,
    `withdrawals` DECIMAL(11,2)  NOT NULL,
    `description` VARCHAR(200) COLLATE utf8mb4_general_ci NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (id_withdrawals, id_point_sale),
    UNIQUE (`id_withdrawals`),
    CONSTRAINT `FK_point_sale_withdrawal` FOREIGN KEY (`id_point_sale`) REFERENCES `point_sales` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.product_type_seq definition

CREATE TABLE IF NOT EXISTS `product_type_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO product_type_seq (next_val)
VALUES(1);

-- salesPoint.product_type definition

CREATE TABLE IF NOT EXISTS `product_type` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_product` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.products_seq definition

CREATE TABLE IF NOT EXISTS `products_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO products_seq (next_val)
VALUES(1);

-- salesPoint.products definition

CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_product` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `image` mediumblob NOT NULL,
  `price_unit` decimal(11,2) NOT NULL,
  `price_sell` decimal(11,2) NOT NULL,
  `created_user` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_user` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `supplier` bigint NOT NULL,
  `product_type` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_product_name` (`id_product`),
  KEY `products_FK` (`supplier`),
  CONSTRAINT `products_FK` FOREIGN KEY (`supplier`) REFERENCES `suppliers` (`id`),
  CONSTRAINT `product_type_FK` FOREIGN KEY (`product_type`) REFERENCES `product_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.products_aud definition

CREATE TABLE IF NOT EXISTS `products_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id` bigint NOT NULL,
    `id_product` VARCHAR(20) NOT NULL,
    `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `image` MEDIUMBLOB NOT NULL,
    `price_unit` DECIMAL(11,2)  NOT NULL,
    `price_sell` DECIMAL(11,2)  NOT NULL,
    `supplier` bigint NOT NULL,
    PRIMARY KEY (`rev_id`,`id_product`),
    CONSTRAINT `FK_product_aud` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.stocktaking_seq definition

CREATE TABLE IF NOT EXISTS `stocktaking_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO stocktaking_seq (next_val)
VALUES(1);

-- salesPoint.stocktaking definition

CREATE TABLE IF NOT EXISTS `stocktaking` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `id_product` BIGINT NOT NULL,
    `stock_max` INT  NOT NULL,
    `stock_min` INT  NOT NULL,
    `stock` INT  NOT NULL,
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `stocktaking_product_FK` FOREIGN KEY (`id_product`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.stocktaking_aud definition

CREATE TABLE IF NOT EXISTS `stocktaking_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id` bigint NOT NULL,
    `id_product` BIGINT NOT NULL,
    `stock_max` INT  NOT NULL,
    `stock_min` INT  NOT NULL,
    `stock` INT  NOT NULL,
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`rev_id`,`id_product`),
    CONSTRAINT `FK_stocktaking_aud` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.debts_seq definition

CREATE TABLE IF NOT EXISTS `debts_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO debts_seq (next_val)
VALUES(1);

-- salesPoint.debts definition

CREATE TABLE IF NOT EXISTS `debts` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `id_debts` VARCHAR(20) COLLATE utf8mb4_general_ci NOT NULL,
    `id_customer` BIGINT NOT NULL,
    `debt_date` datetime(6) DEFAULT NULL,
    `payment_debt` decimal(11,2)  NOT NULL,
    `total_debt` decimal(11,2)  NOT NULL,
    `status` varchar(50) NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_debts`, `id_customer`),
    UNIQUE (`id`),
    CONSTRAINT `FK_customer_debts` FOREIGN KEY (`id_customer`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.debts_aud definition

CREATE TABLE IF NOT EXISTS `debts_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id` bigint NOT NULL,
    `id_debts` VARCHAR(20) COLLATE utf8mb4_general_ci NOT NULL,
    `id_customer` BIGINT NOT NULL,
    `debt_date` datetime(6) DEFAULT NULL,
    `payment_debt` decimal(11,2)  NOT NULL,
    `total_debt` decimal(11,2)   NOT NULL,
    `status` varchar(50) NOT NULL,
    PRIMARY KEY (`rev_id`,`id`),
    CONSTRAINT `FK_debts_aud` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.method_payment_seq definition

CREATE TABLE IF NOT EXISTS `method_payment_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO method_payment_seq (next_val)
VALUES(1);

-- salesPoint.method_payment definition

CREATE TABLE IF NOT EXISTS `method_payment` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(50) COLLATE utf8mb4_general_ci NOT NULL,
    `created_user` VARCHAR(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` VARCHAR(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT IGNORE INTO salesPoint.method_payment (description, created_user, created_date, last_modified_user, last_modified_date)
VALUES ('Tarjeta debito', 'felipemonzon2705', '2023-12-05 12:57:57', 'felipemonzon2705', '2023-12-05 12:57:57');

INSERT IGNORE INTO salesPoint.method_payment (description, created_user, created_date, last_modified_user, last_modified_date)
VALUES ('Efectivo', 'felipemonzon2705', '2023-12-05 12:57:57', 'felipemonzon2705', '2023-12-05 12:57:57');

INSERT IGNORE INTO salesPoint.method_payment (description, created_user, created_date, last_modified_user, last_modified_date)
VALUES ('Tarjeta credito', 'felipemonzon2705', '2023-12-05 12:57:57', 'felipemonzon2705', '2023-12-05 12:57:57');


-- salesPoint.payments_seq definition

CREATE TABLE IF NOT EXISTS `payments_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO payments_seq (next_val)
VALUES(1);

-- salesPoint.payments definition

CREATE TABLE IF NOT EXISTS `payments` (
    `id_payment` VARCHAR(20) COLLATE utf8mb4_general_ci NOT NULL,
    `id_debt` bigint  NOT NULL,
    `payment_date` datetime(6) NOT NULL,
    `amount` DECIMAL(11,2)  NOT NULL,
    `id_method_payment` bigint  NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_payment`, `id_debt`),
    UNIQUE (`id_payment`),
    CONSTRAINT `FK_payment_debts` FOREIGN KEY (`id_debt`) REFERENCES `debts` (`id`),
    CONSTRAINT `FK_method_payment_debts` FOREIGN KEY (`id_method_payment`) REFERENCES `method_payment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.payments_aud definition

CREATE TABLE IF NOT EXISTS `payments_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id_method_payment` bigint NOT NULL,
    `id_payment` VARCHAR(20) COLLATE utf8mb4_general_ci NOT NULL,
    `id_debt` bigint  NOT NULL,
    `payment_date` datetime(6) DEFAULT NULL,
    `amount` DECIMAL(11,2)  NOT NULL,
    PRIMARY KEY (`rev_id`,`id_payment`),
    CONSTRAINT `FK_payment_aud` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.sells_seq definition

CREATE TABLE IF NOT EXISTS `sells_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO sells_seq (next_val)
VALUES(1);

-- salesPoint.sells definition

CREATE TABLE IF NOT EXISTS `sells` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `id_sell` VARCHAR(100) COLLATE utf8mb4_general_ci NOT NULL,
    `id_point_sale` bigint  NOT NULL,
    `sell_date` datetime(6) DEFAULT NULL,
    `amount` DECIMAL(11,2)  NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `id_method_payment` BIGINT NOT NULL,
    `invoice` VARCHAR(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `id_customer` BIGINT NOT NULL,
    `created_user` VARCHAR(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` VARCHAR(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`id_sell`),
    CONSTRAINT `sell_point_sale_FK` FOREIGN KEY (`id_point_sale`) REFERENCES `point_sales` (`id`),
    CONSTRAINT `method_payment_FK` FOREIGN KEY (`id_method_payment`) REFERENCES `method_payment` (`id`),
    CONSTRAINT `customer_FK` FOREIGN KEY (`id_customer`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.sells_aud definition

CREATE TABLE IF NOT EXISTS `sells_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id` bigint NOT NULL,
    `id_sell` VARCHAR(100) COLLATE utf8mb4_general_ci NOT NULL,
    `id_point_sale` bigint  NOT NULL,
    `sell_date` datetime(6) DEFAULT NULL,
    `amount` DECIMAL(11,2)  NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `invoice` VARCHAR(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `id_customer` BIGINT NOT NULL,
    PRIMARY KEY (`rev_id`,`id`),
    CONSTRAINT `FK_sells_aud` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.detail_sells_seq definition

CREATE TABLE IF NOT EXISTS `sells_details_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO sells_details_seq (next_val)
VALUES(1);

-- salesPoint.detail_sells definition

CREATE TABLE IF NOT EXISTS `sells_details` (
    `id_sell` BIGINT NOT NULL,
    `id_product` BIGINT  NOT NULL,
    `piece` INT NOT NULL,
    `subtotal` DECIMAL(11,2) NOT NULL,
    `created_user` VARCHAR(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` VARCHAR(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_sell`,`id_product`),
    CONSTRAINT `sell_product_FK` FOREIGN KEY (`id_product`) REFERENCES `products` (`id`),
    CONSTRAINT `sell_detail_FK` FOREIGN KEY (`id_sell`) REFERENCES `sells` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.shopping_seq definition

CREATE TABLE IF NOT EXISTS `shopping_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO shopping_seq (next_val)
VALUES(1);

-- salesPoint.shopping definition

CREATE TABLE IF NOT EXISTS `shopping` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `id_shopping` VARCHAR(20) COLLATE utf8mb4_general_ci NOT NULL,
    `id_point_sale` bigint  NOT NULL,
    `shopping_date` datetime(6) DEFAULT NULL,
    `amount` DECIMAL(11,2)  NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `id_method_payment` BIGINT NOT NULL,
    `invoice` VARCHAR(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `id_supplier` BIGINT NOT NULL,
    `created_user` VARCHAR(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` VARCHAR(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`id_shopping`),
    CONSTRAINT `shopping_point_sale_FK` FOREIGN KEY (`id_point_sale`) REFERENCES `point_sales` (`id`),
    CONSTRAINT `shopping_method_payment_FK` FOREIGN KEY (`id_method_payment`) REFERENCES `method_payment` (`id`),
    CONSTRAINT `supplier_FK` FOREIGN KEY (`id_supplier`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- salesPoint.shopping_aud definition

CREATE TABLE IF NOT EXISTS `shopping_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id` bigint NOT NULL,
    `id_shopping` VARCHAR(100) COLLATE utf8mb4_general_ci NOT NULL,
    `id_point_sale` bigint  NOT NULL,
    `shopping_date` datetime(6) DEFAULT NULL,
    `amount` DECIMAL(11,2)  NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `invoice` VARCHAR(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `id_supplier` BIGINT NOT NULL,
    PRIMARY KEY (`rev_id`,`id`),
    CONSTRAINT `FK_shopping_aud` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.detail_shopping_seq definition

CREATE TABLE IF NOT EXISTS `shopping_details_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO shopping_details_seq (next_val)
VALUES(1);

-- salesPoint.detail_shopping definition

CREATE TABLE IF NOT EXISTS `shopping_details` (
    `id_shopping` BIGINT NOT NULL,
    `id_product` BIGINT  NOT NULL,
    `piece` INT NOT NULL,
    `subtotal` DECIMAL(11,2) NOT NULL,
    `created_user` VARCHAR(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` VARCHAR(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_shopping`,`id_product`),
    CONSTRAINT `id_shop_product_FK` FOREIGN KEY (`id_product`) REFERENCES `products` (`id`),
    CONSTRAINT `FK_shopping_detail` FOREIGN KEY (`id_shopping`) REFERENCES `shopping` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
