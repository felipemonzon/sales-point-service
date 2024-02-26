INSERT INTO salesPoint.enterprises
(id, id_enterprise, rfc, status, address, name, phone, manager, created_user, created_date, last_modified_user,
 last_modified_date)
VALUES (100, 'ENT6734htdge362xnhd3', 'LOJI877654HSFZ21', 1, 'muy lejos', 'empresa', '1234567898', 'Felipe Monzon',
        'ADMIN',
        NOW(), 'ADMIN', NOW());

INSERT INTO salesPoint.point_sales (id, id_point_sale, name, id_enterprise, created_user, created_date,
                                    last_modified_user, last_modified_date, status)
VALUES (52, 'POSLCwJDF7Uq2wY9HbI1', 'Caja 2', 100, 'felipemonzon2705', '2023-12-12 20:21:50.365000', 'felipemonzon2705',
        '2023-12-12 20:21:50.365000', 'ACTIVE');

INSERT INTO salesPoint.point_sales (id, id_point_sale, name, id_enterprise, created_user, created_date,
                                    last_modified_user, last_modified_date, status)
VALUES (152, 'POSLCwJDF7Uq2wY9Hb10', 'Caja 3', 100, 'felipemonzon2705', '2023-12-12 20:21:50',
        'felipemonzon2705', '2023-12-12 20:21:50', 'ACTIVE');

INSERT INTO salesPoint.users (id, created_user, created_date, last_modified_user, last_modified_date, cellphone,
                              email_address, first_name, genre, last_name, password, username, id_user, status,
                              id_enterprise)
VALUES (100, 'ADMIN', '2023-11-22 00:41:20', 'ADMIN', '2023-11-22 00:41:20', '6671568899', 'felipemonzon2705@gmail.com',
        'Felipe', 0, 'Monzon', '$2a$10$K9UyV7Eiwoi8Udv/9R5kROuDvz/K6ZVLJzzESW2lVe7B.FfXRg0hK', 'felipemonzon27',
        'USU324htgd243yt56100', 'ACTIVE', 1);

INSERT INTO salesPoint.point_sale_details (id, id_point_sale, money, open_date, open_user, close_date, close_user,
                                           status, created_user, created_date, last_modified_user, last_modified_date)
VALUES (152, 152, 38.54, '2023-12-21 22:40:39', 100, '2023-12-23 08:47:27', null, 'OPENED',
        'felipemonzon2705', '2023-12-21 22:40:39', 'felipemonzon2705', '2023-12-23 08:47:27');

INSERT INTO salesPoint.withdrawals_point_sales (id_withdrawals, id_point_sale, withdrawals_date, withdrawals,
                                                description, created_user, created_date, last_modified_user,
                                                last_modified_date)
VALUES ('WIDGoKXQTY1Gp8N4Bxl7', 152, '2023-12-21 22:45:22', 821.43, 'Pago comida', 'felipemonzon2705',
        '2023-12-21 22:45:22', 'felipemonzon2705', '2023-12-21 22:45:22');
