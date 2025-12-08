CREATE USER product_manager_user WITH
NOSUPERUSER
CREATEDB
NOCREATEROLE
ENCRYPTED PASSWORD '123456';

CREATE DATABASE product_management_db OWNER product_manager_user;
\c product_management_db product_manager_user;


CREATE DATABASE product_management_db OWNER product_manager_user;
\c product_management_db;
SET ROLE product_manager_user;
