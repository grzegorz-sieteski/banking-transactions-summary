INSERT INTO clients (name, surname, country) VALUES ('Tomasz', 'Kot', NULL);
INSERT INTO clients (name, surname, country) VALUES ('Natalia', 'Nowak', 'Poland');
INSERT INTO balances (client_id, total, currency, date) VALUES (1, 12110.00, 'PLN', '2020-05-01');
INSERT INTO balances (client_id, total, currency, date) VALUES (2, 6750.00, 'PLN', '2020-05-01');

INSERT INTO transactions (client_id, type, description, date, "value", currency) VALUES (1, 'income', 'salary', '2020-05-04', 7500.00, 'PLN');
INSERT INTO transactions (client_id, type, description, date, "value", currency) VALUES (1, 'outcome', 'mortgage', '2020-05-06', 1100.00, 'PLN');
INSERT INTO transactions (client_id, type, description, date, "value", currency) VALUES (1, 'income', 'interests', '2020-05-10', 1700.00, 'PLN');
INSERT INTO transactions (client_id, type, description, date, "value", currency) VALUES (1, 'outcome', 'transfer', '2020-05-11', 1200.00, 'PLN');

INSERT INTO transactions (client_id, type, description, date, "value", currency) VALUES (2, 'income', 'salary', '2020-05-04', 10500.00, 'PLN');
INSERT INTO transactions (client_id, type, description, date, "value", currency) VALUES (2, 'outcome', 'transfer', '2020-05-10', 1200.00, 'PLN');
INSERT INTO transactions (client_id, type, description, date, "value", currency) VALUES (2, 'outcome', 'transfer', '2020-05-11', 1050.50, 'PLN');
