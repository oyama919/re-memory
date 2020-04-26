#!/bin/sh
mysql -u root -p -h 127.0.0.1 < create-mysql-db.sql
cd web-api
sbt flywayMigrate
