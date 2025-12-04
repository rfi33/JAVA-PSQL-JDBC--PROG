createuser --interactive product_management_user
Shall the new role be a superuser? (y/n) n
Shall the new role be allowed to create databases? (y/n) y
Shall the new role be allowed to create more new roles? (y/n) n
postgres@pop-os:~$ psql

postgres=# \password product_management_user
Enter new password for user "product_management_user": 123456
Enter it again: 123456

OR

CREATE USER product_management_user WITH
NOSUPERUSER
CREATDB
ENCRYPTED
NOCREATEROLE
PASSWORD '123456';

psql -U product_management_db -d product_management_db -h localhost


