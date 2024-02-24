INSERT INTO salesPoint.customers
(id_customer, created_user, created_date, last_modified_user, last_modified_date, phone, email, first_name, genre,
 last_name)
VALUES ('CUSXXXXXXXXXXXXXXX11', 'felipemonzon2705', '2023-04-27 20:21:22.699000000', 'felipemonzon2705',
        '2023-04-27 20:21:22.699000000', '1234567894', 'ejemplo@gmail.com', 'Cliente', 0, 'General');


INSERT INTO salesPoint.debts (id, id_debts, id_customer, debt_date, total_debt, status, created_user, created_date,
                              last_modified_user, last_modified_date, payment_debt)
VALUES (1, 'DEBFbLLRo3N0SLOe8CIT', 1, '2023-12-15 15:36:00.000000', 880.35, 'CANCELED', 'felipemonzon2705',
        '2023-12-15 15:36:24.468000', 'felipemonzon2705', '2023-12-15 17:06:24.658000', 0.00);
