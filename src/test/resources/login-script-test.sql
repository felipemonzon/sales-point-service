INSERT INTO salesPoint.enterprises (id, id_enterprise, rfc, status, address, name, phone, manager, created_user,
                                    created_date, last_modified_user, last_modified_date)
VALUES (1000, 'ENT6734htdge362x1000', 'COP920421000', true, '1304 Cole Stream', 'del sol 2', '6679123299',
        'Mrs. Gene Feil', 'ADMIN', '2023-11-22 00:41:20.000000', 'felipemonzon2705', '2024-02-28 02:52:25');


INSERT INTO salesPoint.users (id, created_user, created_date, last_modified_user, last_modified_date, cellphone,
                              email_address, first_name, genre, last_name, password, username, id_user, status,
                              id_enterprise)
VALUES (1000, 'ADMIN', '2023-11-22 00:41:20', 'ADMIN', '2023-11-22 00:41:20', '6671568899', 'felipemonzon2705@gmail.com',
        'Felipe', 0, 'Monzon', '$2a$10$K9UyV7Eiwoi8Udv/9R5kROuDvz/K6ZVLJzzESW2lVe7B.FfXRg0hK', 'felipemonzon2805',
        'USU324htgd243yt51000', 'ACTIVE', 1000);


INSERT INTO salesPoint.roles (id, name, value, status, created_user, created_date, last_modified_user,
                              last_modified_date)
VALUES (1000, 'ROLE_SUPER_ADMIN', 'SUPER ADMIN', 1, 'ADMIN', '2023-11-22 00:41:20', 'ADMIN', '2023-11-22 00:41:20');

INSERT INTO salesPoint.user_roles (id_user, id_role)
VALUES (1000, 1000);
