-- salesPoint.menu_seq definition

CREATE TABLE IF NOT EXISTS `menu_seq` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO menu_seq (next_val)
VALUES(1);

-- salesPoint.menu definition

CREATE TABLE IF NOT EXISTS `menu` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `path` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `created_user` varchar(255) DEFAULT NULL,
    `created_date` datetime(6) DEFAULT NULL,
    `last_modified_user` varchar(255) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO salesPoint.menu (path, title, icon, status, created_user, created_date, last_modified_user, last_modified_date)
VALUES ('/dashboard', 'Dashboard', 'fa fa-dashboard', 'ACTIVE', 'felipemonzon2705', '2023-12-05 12:57:57', 'felipemonzon2705', '2023-12-05 12:57:57');

INSERT INTO salesPoint.menu (path, title, icon, status, created_user, created_date, last_modified_user, last_modified_date)
VALUES ('/table', 'Table List', 'fa fa-table', 'ACTIVE', 'felipemonzon2705', '2023-12-05 12:57:57', 'felipemonzon2705', '2023-12-05 12:57:57');
