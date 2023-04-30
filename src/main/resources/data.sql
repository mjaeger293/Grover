INSERT INTO `grover_user` (email, password) VALUES ('mjaeger@htl-steyr.ac.at', '1234');
INSERT INTO `grover_user` (email, password) VALUES ('admin@admin.at', 'admin');

INSERT INTO `brand` (name) VALUES ('HP');
INSERT INTO `brand` (name) VALUES ('Lenovo');
INSERT INTO `brand` (name) VALUES ('Apple');

INSERT INTO `model` (name, price, brand_id) VALUES ('Probook x360', 10, 1);
INSERT INTO `model` (name, price, brand_id) VALUES ('Elite Dragonfly', 12, 1);
INSERT INTO `model` (name, price, brand_id) VALUES ('Yoga 7', 7.5, 2);
INSERT INTO `model` (name, price, brand_id) VALUES ('MacBook Pro M2', 13.5, 3);

INSERT INTO `customer` (email, name) VALUES ('mueller123@bmd.at', 'Sebastian Müller');
INSERT INTO `customer` (email, name) VALUES ('habichler751@bmd.at', 'Raphael Habichler');

INSERT INTO `rental` (from_date, to_date, customer_id, model_id, closed, loadings) VALUES ('2023-01-26', '2023-03-02 23:59:59', 1, 3, false, 0);
INSERT INTO `rental` (from_date, to_date, customer_id, model_id, closed, loadings) VALUES ('2023-02-02', '2023-03-02 23:59:59', 2, 4, false, 0);
