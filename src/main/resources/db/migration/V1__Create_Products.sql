CREATE TABLE products
(id BIGINT NOT NULL AUTO_INCREMENT,
 name TEXT NOT NULL,
 category TEXT NOT NULL,
 brand TEXT NOT NULL,
 description TEXT NOT NULL,
 color TEXT,
 date_added TEXT NOT NULL,
 in_stock BOOLEAN NOT NULL,
PRIMARY KEY (id));