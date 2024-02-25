INSERT INTO salesPoint.debts (id, id_debts, id_customer, debt_date, total_debt, status, created_user, created_date,
                              last_modified_user, last_modified_date, payment_debt)
VALUES (5, 'DEBFbLLRo3N0SLOe8100', 1, '2023-12-15 15:36:00.000000', 2, 'ACTIVE', 'felipemonzon2705',
        '2023-12-15 15:36:24.468000', 'felipemonzon2705', '2023-12-15 17:06:24.658000', 1.00);

INSERT INTO salesPoint.payments (id_payment, id_debt, payment_date, amount, id_method_payment, created_user,
                                 created_date, last_modified_user, last_modified_date)
VALUES ('PAYaBbLTbo0W2xAZWNMP', 5, '2024-01-06 19:43:37.577346', 1.00, 1, 'felipemonzon2705',
        '2024-01-06 19:43:37.602000', 'felipemonzon2705', '2024-01-06 19:43:37.602000');
