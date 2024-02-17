-- salesPoint.revinfo definition

CREATE TABLE IF NOT EXISTS `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `revtstmp` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.roles_seq definition

CREATE TABLE IF NOT EXISTS `enterprises_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO enterprises_seq (next_val)
VALUES(1);

-- salesPoint.enterprises definition

CREATE TABLE IF NOT EXISTS `enterprises` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_enterprise` VARCHAR(20) NOT NULL,
  `rfc` VARCHAR(20) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `manager` varchar(100) NOT NULL,
  `created_user` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `last_modified_user` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `last_modified_date` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO enterprises
(id_enterprise, rfc, status, address, name, phone, manager, created_user, created_date, last_modified_user, last_modified_date)
VALUES('ENT6734htdge362xnhd3', 'JUJI877654HSFH12', 1, 'muy lejos', 'empresa', '1234567898', 'Felipe Monzon', 'ADMIN', NOW(), 'ADMIN', NOW());

-- salesPoint.enterprises_aud definition

CREATE TABLE IF NOT EXISTS `enterprises_aud` (
  `rev_id` int NOT NULL,
  `rev_type` tinyint DEFAULT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_user` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `id_enterprise` VARCHAR(20) NOT NULL,
  `rfc` VARCHAR(20) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `manager` varchar(100) NOT NULL,
  PRIMARY KEY (`rev_id`,`id_enterprise`),
  CONSTRAINT `enterprises_aud` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.roles_seq definition

CREATE TABLE IF NOT EXISTS `roles_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO roles_seq (next_val)
VALUES(1);

-- salesPoint.roles definition

CREATE TABLE IF NOT EXISTS roles (
	id BIGINT auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	value varchar(100) NOT NULL,
	status bool NULL DEFAULT true,
	created_user varchar(250) NOT NULL,
	created_date datetime NOT NULL,
	last_modified_user varchar(255) NOT NULL,
	last_modified_date datetime NOT NULL,
	CONSTRAINT role_PK PRIMARY KEY (id),
	CONSTRAINT role_UN UNIQUE KEY (name,value)
);

INSERT INTO roles (name, value, status, created_user, created_date, last_modified_user, last_modified_date)
VALUES ('ROLE_ADMIN', 'ADMIN', 1, 'ADMIN', NOW(), 'ADMIN', NOW()),
('ROLE_CUSTOMER', 'Cliente', 1, 'ADMIN', NOW(), 'ADMIN', NOW());

-- salesPoint.users_seq definition

CREATE TABLE IF NOT EXISTS `users_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO users_seq (next_val)
VALUES(1);

-- salesPoint.users definition

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL,
  `created_user` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_user` varchar(255) NOT NULL,
  `last_modified_date` datetime NOT NULL,
  `cellphone` varchar(255) NOT NULL,
  `email_address` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `genre` tinyint DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(200) NOT NULL,
  `username` varchar(20) NOT NULL,
  `id_user` VARCHAR(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `id_enterprise` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK_sx468g52bpetvlad2j9y0lptc` (`username`),
  KEY `FKe3bv5fmb4jdep3443q00b22ha` (`id_enterprise`),
  CONSTRAINT `FKe3bv5fmb4jdep3443q00b22ha` FOREIGN KEY (`id_enterprise`) REFERENCES `enterprises` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.users_aud definition

CREATE TABLE IF NOT EXISTS `users_aud` (
  `rev_id` int NOT NULL,
  `rev_type` tinyint DEFAULT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_user` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `cellphone` varchar(255) NOT NULL,
  `email_address` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `genre` tinyint DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(200) NOT NULL,
  `username` varchar(20) NOT NULL,
  `id_user` VARCHAR(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`rev_id`,`id_user`),
  CONSTRAINT `FKd1sdju07d10p2eqnme91crm35` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO users
(id, id_user, created_user, created_date, last_modified_user, last_modified_date, cellphone, email_address, first_name, genre, last_name, password, username, status, id_enterprise)
VALUES(1, 'USU324htgd243yt567jh', 'ADMIN', NOW(), 'ADMIN', NOW(), '6671568899', 'felipemonzon2705@gmail.com', 'Felipe', 0, 'Monzon', '$2a$10$K9UyV7Eiwoi8Udv/9R5kROuDvz/K6ZVLJzzESW2lVe7B.FfXRg0hK', 'felipemonzon2705', 'ACTIVE', 1);

-- salesPoint.user_roles definition

CREATE TABLE IF NOT EXISTS `user_roles` (
  `id_user` bigint NOT NULL,
  `id_role` bigint NOT NULL,
  PRIMARY KEY (`id_user`,`id_role`),
  KEY `FK1v995xldvmr6w96c5feofx1gf` (`id_role`),
  CONSTRAINT `role_FK` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id`),
  CONSTRAINT `user_role_FK` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO user_roles(id_user, id_role)
VALUES(1, 1);

-- salesPoint.suppliers_seq definition

CREATE TABLE IF NOT EXISTS `suppliers_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO suppliers_seq (next_val)
VALUES(1);

-- salesPoint.suppliers definition

CREATE TABLE IF NOT EXISTS  `suppliers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_supplier` VARCHAR(20) NOT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_user` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `comments` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `enterprise` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `rfc` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9g4pe49qq28884yknw05f33yy` (`name`,`rfc`),
  UNIQUE KEY `UK_eegixpn11chp14nb25tl3ucv0` (`name`),
  UNIQUE KEY `UK_RFC` (`rfc`),
  UNIQUE KEY `UK_SUPPLIER` (`id_supplier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO suppliers
(id_supplier, name, enterprise, rfc, address, phone, comments, status, created_user, created_date, last_modified_user, last_modified_date)
VALUES('SUP12345678902134578', 'Costco', 'costco sa de cv', 'unrfc20202028', 'CALLE PRINCIPAL', '6671544056', 'Entrega puntal', 'ACTIVE', 'ADMIN', '2022-06-02 02:43:49', 'ADMIN', '2022-06-02 02:43:49');

-- salesPoint.suppliers_aud definition

CREATE TABLE IF NOT EXISTS `suppliers_aud` (
  `id` bigint NOT NULL,
  `id_supplier` VARCHAR(20) NOT NULL,
  `rev_id` int NOT NULL,
  `rev_type` tinyint DEFAULT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_user` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `comments` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `enterprise` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `rfc` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`rev_id`,`id_supplier`),
  CONSTRAINT `FK_supplier` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.customers_seq definition

CREATE TABLE IF NOT EXISTS `customers_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO customers_seq (next_val)
VALUES(1);

-- salesPoint.customers definition

CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_customer` VARCHAR(20) NOT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_user` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `genre` tinyint DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UK_3qyhofnbafmd6dgwbsl462cux` (`id_customer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO customers
(id_customer, created_user, created_date, last_modified_user, last_modified_date, phone, email, first_name, genre, last_name)
VALUES('CUSXXXXXXXXXXXXXXXX1', 'felipemonzon2705', '2023-04-27 20:21:22.699000000', 'felipemonzon2705', '2023-04-27 20:21:22.699000000', '1234567894', 'ejemplo@gmail.com', 'Cliente', 0, 'General');

-- salesPoint.customers_aud definition

CREATE TABLE IF NOT EXISTS `customers_aud` (
  `id` bigint NOT NULL,
  `id_customer` VARCHAR(20) NOT NULL,
  `rev_id` int NOT NULL,
  `rev_type` tinyint DEFAULT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_user` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `genre` tinyint DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`rev_id`,`id_customer`),
  CONSTRAINT `FK_customer` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.point_sales_seq definition

CREATE TABLE IF NOT EXISTS `point_sales_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO point_sales_seq (next_val)
VALUES(1);

-- salesPoint.point_sales definition

CREATE TABLE IF NOT EXISTS `point_sales` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `id_point_sale` VARCHAR(20) NOT NULL,
    `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `id_enterprise` bigint NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_point_sale` (`id_point_sale`),
    CONSTRAINT `FK_point_sales` FOREIGN KEY (`id_enterprise`) REFERENCES `enterprises` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- salesPoint.point_sales_aud definition

CREATE TABLE IF NOT EXISTS `point_sales_aud` (
    `rev_id` int NOT NULL,
    `rev_type` tinyint DEFAULT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `id` bigint NOT NULL,
    `id_point_sale` VARCHAR(20) NOT NULL,
    `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`rev_id`,`id_point_sale`),
    CONSTRAINT `FK_point_sale` FOREIGN KEY (`rev_id`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
