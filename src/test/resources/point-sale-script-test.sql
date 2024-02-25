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
