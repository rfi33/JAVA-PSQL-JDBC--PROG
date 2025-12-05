createuser --interactive product_management_user
Shall the new role be a superuser? (y/n) n
Shall the new role be allowed to create databases? (y/n) y
Shall the new role be allowed to create more new roles? (y/n) n
postgres@pop-os:~$ psql

postgres=# \password product_manager_user
Enter new password for user "product_management_user": 123456
Enter it again: 123456

CREATE DATABASE product_management_db OWNER product_manager_user;
\q
psql -U product_manager_user -d product_management_db  -h localhost


CREATE USER product_manager_user WITH
NOSUPERUSER
CREATEDB
NOCREATEROLE
ENCRYPTED PASSWORD '123456';

CREATE DATABASE product_management_db OWNER product_manager_user;
\q
psql -U product_manager_user -d product_management_db -h localhost


