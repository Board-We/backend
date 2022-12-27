create database boardwe;
use mysql;
# CREATE USER 'root'@'%' IDENTIFIED BY 'password';
# GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
CREATE USER 'boardwe'@'%' identified by '1234';
GRANT ALL PRIVILEGES ON boardwe.* TO 'boardwe'@'%' WITH GRANT OPTION;