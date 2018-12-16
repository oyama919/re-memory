DROP DATABASE IF EXISTS `re_memory`;
CREATE DATABASE `re_memory` DEFAULT CHARSET utf8 COLLATE utf8_bin;
GRANT ALL PRIVILEGES ON `re_memory`.* TO re_memory@localhost IDENTIFIED BY 'パスワード';