CREATE TABLE balance (
    id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    amount DOUBLE NOT NULL
);

INSERT INTO balance (id, amount) VALUES (1, 10);
INSERT INTO balance (id, amount) VALUES (2, 20);
INSERT INTO balance (id, amount) VALUES (3, 30);
INSERT INTO balance (id, amount) VALUES (4, 40);