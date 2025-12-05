CREATE TABLE Product (
id SERIAL PRIMARY KEY ,
name VARCHAR (100),
price FLOAT ,
creation_datetime TIMESTAMP
);

CREATE TABLE Product_category (
id SERIAL PRIMARY KEY ,
name VARCHAR (50),
product_id int REFERENCES Product(id)
);