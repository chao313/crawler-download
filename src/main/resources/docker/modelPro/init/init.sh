#!/bin/bash
mysql -h host.docker.internal -u root -p123456 -e"drop database if exists 11_SoftView_64468_db; create database 11_SoftView_64468_db;use 11_SoftView_64468_db;set names utf8;source  /app/sql/db.sql;"
mysql -h host.docker.internal -u root -p123456 -e"CREATE USER '11_64468_admin'@'%' IDENTIFIED BY '11_SoftView_64468_passwd';"
mysql -h host.docker.internal -u root -p123456 -e"GRANT ALL PRIVILEGES ON 11_64468_db.* TO '11_64468_admin';"
mysql -h host.docker.internal -u root -p123456 -e"FLUSH PRIVILEGES;"
#示例
