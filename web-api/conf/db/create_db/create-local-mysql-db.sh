#!/bin/sh

mysql -uroot < create-mysql-db.sql

# -prootなど　パスワード必要な場合は各自設定
# 例) mysql -uroot -proot < create-mysql-db.sql
